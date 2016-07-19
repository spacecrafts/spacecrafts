package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import se.jbee.game.any.gfx.ObjClass;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Ring implements Gfx, ObjClass {

	@Override
	public void draw(Graphics2D gfx, Resources resources, int[] obj) {
		int xc = obj[1];
		int yc = obj[2];
		int d = obj[3];
		int thickness = obj[4];
		int fg = obj[5];
		gfx.setColor(resources.color(fg));
		int r = d/2;
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(thickness));
		gfx.drawOval(xc-r, yc-r, 2*r, 2*r);
		gfx.setStroke(s);
	}

}
