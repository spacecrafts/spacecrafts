package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.toRadians;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Point;
import se.jbee.game.any.gfx.Styles;
import se.jbee.game.scs.gfx.Gfx;

public class Techwheel implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj=data.get(0);
		int xc = obj[2];
		int yc = obj[3];
		int d = obj[4];
		int color = obj[5];
		gfx.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.setColor(styles.color(color));

		int ring = d/12;

		int h1 = ring/2+ring/5+ring/24;
		int h6 = ring*6/2+ring*6/5+ring*6/120;
		gfx.drawLine(xc, yc-ring, xc, yc-(6*ring)+1);
		gfx.drawLine(xc, yc+ring, xc, yc+(6*ring)-1);
		gfx.drawLine(xc-ring, yc, xc-(6*ring)+1, yc);
		gfx.drawLine(xc+ring, yc, xc+(6*ring)-1, yc);
		gfx.drawLine(xc+h1, yc+h1, xc+h6, yc+h6);
		gfx.drawLine(xc-h1, yc-h1, xc-h6, yc-h6);
		gfx.drawLine(xc+h1, yc-h1, xc+h6, yc-h6);
		gfx.drawLine(xc-h1, yc+h1, xc-h6, yc+h6);

		int dn = ring+ring;
		int x0 = xc-ring;
		int y0 = yc-ring;
		gfx.drawOval(x0, y0, dn, dn);
		for (int i = 0; i < 5; i++) {
			x0 -=ring;
			y0 -=ring;
			dn += ring+ring;
			gfx.drawOval(x0, y0, dn, dn);
		}
	}

	/**
	 * Position relative to the first that is "centered" as far as possible.
	 */
	static int [] REL_POS = { 0, 1, -1, 2, -2 };

	public static Point centerOfWheelPosition(int xc, int yc, int d, int sec, int lev, int no) {
		int w_lev = d/12;
		double dist = w_lev*lev+w_lev/2;
		double angle = sec*45d + 45d/2d + (45d/lev*REL_POS[no]);
		if (lev % 2 == 0) {
			angle -= 45d/(2*lev);
		}
		int dx = (int) (Math.sin(toRadians(angle)) * dist);
		int dy = (int) (Math.cos(toRadians(angle)) * dist);
		return new Point(xc+dx, yc+dy);
	}

}
