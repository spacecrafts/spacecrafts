package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;

import se.jbee.game.any.gfx.Drawable;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;

public final class Rect implements Gfx, Drawable {

	private final int x,y,w,h;
	
	public Rect(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		gfx.setColor(resources.color(Hue.TEXT_HIGHLIGHT)); gfx.drawRect(x, y, w, h);		
	}

}
