package se.jbee.game.scs.gfx.obj;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

public class Knob implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Resources styles, List<int[]> data) {
		int[] obj = data.get(0);
		gfx.setColor(styles.color(obj[5]));
		int x = obj[2];
		int y = obj[3];
		int d = obj[4];
		gfx.fillOval(x, y, d, d);
		if (data.size() < 2)
			return;
		gfx.setColor(styles.color(obj[6]));
		int w = 0;
		int size = d/2;
		int[] txt = data.get(1);
		String text = new String(txt, 0, txt.length);
		do {
			gfx.setFont(styles.font(FONT_REGULAR, size));
			FontMetrics fm = gfx.getFontMetrics();
			w = fm.stringWidth(text);
			size -= 2;
		} while (w > (d-d/8));
		FontMetrics fm = gfx.getFontMetrics();
		y += (d-fm.getHeight())/2 + fm.getHeight() - fm.getDescent();
		x += (d-w)/2;
		gfx.drawString(text, x, y);
	}		

}
