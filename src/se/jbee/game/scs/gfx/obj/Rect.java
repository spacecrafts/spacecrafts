package se.jbee.game.scs.gfx.obj;

import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;

public class Rect implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		gfx.setColor(styles.color(COLOR_TEXT_HIGHLIGHT)); gfx.drawRect(obj[2], obj[3], obj[4], obj[5]);		
	}

}