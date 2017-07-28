package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public final class Ring implements Gfx, GfxObj {

	private final int xc;
	private final int yc;
	private final int d;
	private final int thickness;
	private final int fg;
	
	public Ring(int xc, int yc, int d, int thickness, int fg) {
		super();
		this.xc = xc;
		this.yc = yc;
		this.d = d;
		this.thickness = thickness;
		this.fg = fg;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		gfx.setColor(resources.color(fg));
		int r = d/2;
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(thickness));
		gfx.drawOval(xc-r, yc-r, 2*r, 2*r);
		gfx.setStroke(s);
	}

}
