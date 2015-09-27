package se.jbee.game.uni.gfx.obj;

import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;

public class Text implements Gfx, Obj {

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		int x = obj[2];
		int y = obj[3];
		int font = obj[4];
		int size = obj[5];
		int color = obj[6];
		gfx.setColor(styles.color(color));
		if (font == FONT_DOTS) {
			DotFont5x4.draw(gfx, x, y, size, data.get(1));
		} else {
			gfx.setFont(styles.font(font, size));
			String str = "";
			int xt = x;
			for (int j = 1; j < data.size(); j++) {
				if (j > 1) {
					xt += gfx.getFontMetrics().stringWidth(str);
					xt += gfx.getFontMetrics().stringWidth(" ");
				}
				int[] text = data.get(j);
				str = new String(text, 0, text.length);
				gfx.drawString(str, xt, y);
			}
		}		
	}

}
