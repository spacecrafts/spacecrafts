package se.jbee.game.any.gfx.obj;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Text implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Resources styles, List<int[]> data) {
		int[] obj = data.get(0);
		int x1 = obj[2];
		int y1 = obj[3];
		int font = obj[4];
		int size = obj[5];
		int color = obj[6];
		int align = obj[7];
		int x2 = obj[8];
		int y2 = obj[9];
		gfx.setColor(styles.color(color));
		if (font == FONT_DOTS) {
			// in case of dot font the size is the diameter of the dots
			//TODO align calc
			DotFont5x4.draw(gfx, x1, y1, size, data.get(1));
			return;
		}
		FontMetrics fm = null;
		String text = text(data);
		do {
			gfx.setFont(styles.font(font, size));
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

	private String text(List<int[]> data) {
		String str = "";
		for (int j = 1; j < data.size(); j++) {
			if (j > 1) {
				str+=" ";
			}
			int[] text = data.get(j);
			str += new String(text, 0, text.length);
		}
		return str;
	}

}
