package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Resources;

public final class Path implements GfxObj {

	private final int type;
	private final int color;
	private final int stroke;
	private final int points[];

	public Path(int type, int color, int stroke, int[] points) {
		super();
		this.type = type;
		this.color = color;
		this.stroke = stroke;
		this.points = points;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		gfx.setColor(resources.color(color));
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(stroke));
		// edgy
		for (int i = 4; i < points.length; i+=2) {
			gfx.drawLine(points[i-3], points[i-2], points[i-1], points[i]);
		}
		gfx.setStroke(s);
	}

}
