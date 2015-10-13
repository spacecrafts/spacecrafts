package se.jbee.game.scs.gfx.obj;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;

public class Line implements Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		gfx.setColor(styles.color(obj[7]));
		Stroke stroke = gfx.getStroke();
		gfx.setStroke(new BasicStroke(obj[6]));
		gfx.drawLine(obj[2], obj[3], obj[4], obj[5]);
		gfx.setStroke(stroke);
	}

}
