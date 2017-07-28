package se.jbee.game.any.gfx.obj;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import se.jbee.game.any.gfx.Drawable;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;

public final class Text implements Gfx, Drawable {

	private final int x1;
	private final int y1;
	private final int x2;
	private final int y2;
	private final FontStyle font;
	private int size;
	private Hue color;
	private final Align align;
	private final int key;
	private final String text;
	
	private boolean sized = false;

	public Text(int x1, int y1, int x2, int y2, FontStyle font, int size, Hue color,
			Align align, int key, String text) {
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
	
	public Drawable withColor(Hue color) {
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
		if (font == FontStyle.DOTS) {
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
		case NE:
		case E :
		case SE: x=x2-fm.stringWidth(text); break;
		case EYE:
		case N :
		case S : x=x1+(x2-x1-fm.stringWidth(text)) / 2; break;
		case HCENTER: x=x1-(fm.stringWidth(text)/2); break;
		default:
		}
		int ascent = fm.getAscent();
		int y = y1+ascent;
		switch(align) {
		case EYE:
		case E :
		case W : y=y1+ascent+(y2-y1-ascent) / 2; break;
		case SW:
		case S:
		case SE: y=y2; break;
		case HCENTER: y=y1+ascent; break;
		default:
		}
		gfx.drawString(text, x, y);
	}

}
