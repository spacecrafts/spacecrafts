package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;

public class Ring implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		int xc = obj[2];
		int yc = obj[3];
		int d = obj[4];
		int thickness = obj[5];
		int fg = obj[6];
		int bg = obj[7];
		gfx.setColor(styles.color(fg));
		int r = d/2;
		gfx.fillOval(xc-r, yc-r, 2*r, 2*r);
		r -= thickness;
		gfx.setColor(styles.color(bg));
		gfx.fillOval(xc-r, yc-r, 2*r, 2*r);
	}		

}
