package se.jbee.game.any.gfx.obj;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import se.jbee.game.any.gfx.ObjClass;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Text implements Gfx, ObjClass {

	@Override
	public void draw(Graphics2D gfx, Resources resources, int[] obj) {
		int x1 = obj[1];
		int y1 = obj[2];
		int x2 = obj[3];
		int y2 = obj[4];
		int font = obj[5];
		int size = obj[6];
		int color = obj[7];
		int align = obj[8];
		int type = obj[9];
		String text = "???";
		switch(type) {
		case 0: text=resources.texts.lookup(obj[10]); break;
		case 1: text=new String(obj, 10, obj.length-10); break;
		}
		gfx.setColor(resources.color(color));
		if (font == FONT_DOTS) {
			// in case of dot font the size is the diameter of the dots
			//TODO align calc
			DotFont5x4.draw(gfx, x1, y1, size, text);
			return;
		}
		FontMetrics fm = null;
		do {
			gfx.setFont(resources.font(font, size));
			fm = gfx.getFontMetrics();
			size-=2;
		} while (x2 > 0 && fm.stringWidth(text) > (x2-x1));
		int x = x1;
		switch(align) {
		case ALIGN_NE:
		case ALIGN_E :
		case ALIGN_SE: x=x2-fm.stringWidth(text); break;
		case ALIGN_N :
		case ALIGN_EYE:
		case ALIGN_S : x=x1+(x2-x1-fm.stringWidth(text)) / 2; break;
		}
		int ascent = fm.getAscent();
		int y = y1+ascent;
		switch(align) {
		case ALIGN_E :
		case ALIGN_EYE:
		case ALIGN_W : y=y1+ascent+(y2-y1-ascent) / 2; break;
		case ALIGN_SW:
		case ALIGN_S:
		case ALIGN_SE: y=y2;
		}
		gfx.drawString(text, x, y);
	}

}
