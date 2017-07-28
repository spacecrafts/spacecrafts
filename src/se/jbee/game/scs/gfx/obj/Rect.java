package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public final class Rect implements Gfx, GfxObj {

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
		gfx.setColor(resources.color(COLOR_TEXT_HIGHLIGHT)); gfx.drawRect(x, y, w, h);		
	}

}
