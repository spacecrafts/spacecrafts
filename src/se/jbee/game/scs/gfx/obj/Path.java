package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import se.jbee.game.any.gfx.ObjClass;
import se.jbee.game.any.gfx.Resources;

public final class Path implements ObjClass {

	@Override
	public void draw(Graphics2D gfx, Resources resources, int[] obj) {
		int type = obj[1];
		int color = obj[2];
		int stroke = obj[3];
		gfx.setColor(resources.color(color));
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(stroke));
		// edgy
		for (int i = 7; i < obj.length; i+=2) {
			gfx.drawLine(obj[i-3], obj[i-2], obj[i-1], obj[i]);
		}
		gfx.setStroke(s);
	}

}
