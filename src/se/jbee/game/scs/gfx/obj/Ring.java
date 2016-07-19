package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Ring implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Resources styles, List<int[]> data) {
		int[] obj = data.get(0);
		int xc = obj[2];
		int yc = obj[3];
		int d = obj[4];
		int thickness = obj[5];
		int fg = obj[6];
		gfx.setColor(styles.color(fg));
		int r = d/2;
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(thickness));
		gfx.drawOval(xc-r, yc-r, 2*r, 2*r);
		gfx.setStroke(s);
	}

}
