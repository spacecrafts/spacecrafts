package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;

import se.jbee.game.any.gfx.ObjClass;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Button implements Gfx, ObjClass {

	@Override
	public void draw(Graphics2D gfx, Resources resources, int[] obj) {
		int x = obj[1];
		int y = obj[2];
		int d = obj[3];
		gfx.setColor(resources.color(obj[4]));
		gfx.fillOval(x, y, d, d);
	}		

}
