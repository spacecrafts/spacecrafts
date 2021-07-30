package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;

import se.jbee.game.any.gfx.Drawable;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;

public final class Button implements Gfx, Drawable {

	private final int x;
	private final int y;
	private final int d;
	private final Hue color;

	public Button(int x, int y, int d, Hue color) {
		super();
		this.x = x;
		this.y = y;
		this.d = d;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		gfx.setColor(resources.color(color));
		gfx.fillOval(x, y, d, d);
	}		

}
