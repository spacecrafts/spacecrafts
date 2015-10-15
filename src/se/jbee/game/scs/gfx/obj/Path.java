package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;

public final class Path implements Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		int type = obj[2];
		int color = obj[3];
		int stroke = obj[4];
		gfx.setColor(styles.color(color));
		Stroke s = gfx.getStroke();
		gfx.setStroke(new BasicStroke(stroke));
		// edgy
		for (int i = 8; i < obj.length; i+=2) {
			gfx.drawLine(obj[i-3], obj[i-2], obj[i-1], obj[i]);
		}
		gfx.setStroke(s);
	}

}
