package se.jbee.game.any.gfx.obj;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public final class Text implements Gfx, GfxObj {

	private final int x1;
	private final int y1;
	private final int x2;
	private final int y2;
	private final int font;
	private int size;
	private int color;
	private final int align;
	private final int key;
	private final String text;
	
	private boolean sized = false;

	public Text(int x1, int y1, int x2, int y2, int font, int size, int color,
			int align, int key, String text) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.font = font;
		this.size = size;
		this.color = color;
		this.align = align;
		this.key = key;
		this.text = text;
	}
	
	public GfxObj withColor(int color) {
		return new Text(x1, y1, x2, y2, font, size, color, align, key, text);
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		String text = "???";
		if (key != 0) {
			text=resources.texts.lookup(key);
		} else {
			text=this.text;
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
			sized = x2 < 0 || fm.stringWidth(text) <= (x2-x1);
			if (!sized) { size-=2; }
		} while (!sized);
		int x = x1;
		switch(align) {
		case ALIGN_NE:
		case ALIGN_E :
		case ALIGN_SE: x=x2-fm.stringWidth(text); break;
		case ALIGN_N :
		case ALIGN_EYE:
		case ALIGN_S : x=x1+(x2-x1-fm.stringWidth(text)) / 2; break;
		case ALIGN_CENTER: x=x1-(fm.stringWidth(text)/2); break;
		}
		int ascent = fm.getAscent();
		int y = y1+ascent;
		switch(align) {
		case ALIGN_E :
		case ALIGN_EYE:
		case ALIGN_W : y=y1+ascent+(y2-y1-ascent) / 2; break;
		case ALIGN_SW:
		case ALIGN_S:
		case ALIGN_SE: y=y2; break;
		case ALIGN_CENTER: y=y1+ascent; break;
		}
		gfx.drawString(text, x, y);
	}

}
