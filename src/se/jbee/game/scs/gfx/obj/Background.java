package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Styles;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.scs.gfx.Gfx;

public class Background implements Gfx, Obj {

	private int _w;
	private int _h;
	private long _seed;
	private int[] precomputed;

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		int x0 = obj[2];
		int y0 = obj[3];
		int w = obj[4];
		int h = obj[5];
		int type = obj[6];
		int seed1 = obj[7];
		int seed2 = obj[8];
		switch (type) {
		default:
		case Gfx.BG_BLACK: gfx.setColor(Color.black); gfx.fillRect(x0,y0,w,h); break;
		case Gfx.BG_SPACE: paintSpace(gfx, x0, y0, w, h, Entity.longValue(seed1, seed2)); break;
		}

	}

	private void paintSpace(Graphics2D gfx, int x0, int y0, int w, int h, long seed) {
		gfx.setColor(Color.black);
		gfx.fillRect(x0, y0, w, h);
		if (w != _w || h != _h || _seed != seed) {
			precomputed = makeSpace(w, h, seed);
			_w = w;
			_h = h;
			_seed = seed;
		}
		int j = 0;
		int[] sp = precomputed;
		for (int i = 0; i < w/2; i++) {
			int r = sp[j++];
			int g = sp[j++];
			int b = sp[j++];
			int a = sp[j++];
			if (r > b) {
				gfx.setColor(new Color(200 + r, 200 + g, 50 + b+a, a));
			} else {
				gfx.setColor(new Color(100 + r+a, 200 + g, 217 + b, a));
			}
			int x = sp[j++];
			int y = sp[j++];
			gfx.drawLine(x-1, y, x+1, y);
			gfx.drawLine(x, y-1, x, y+1);
			if (a > 60) {
				gfx.drawLine(x-1, y-1, x+1, y+1);
				gfx.drawLine(x+1, y-1, x-1, y+1);
			}
			if (a >= 64) {
				if (a < 66) {
					a /= 2;
				}
				gfx.setColor(new Color(255, 255, 180+a, a));
				int n = a > 68 ? 2 : 1;
				if (a % 2 == 0) {
					gfx.drawLine(x-n, y-n, x+n, y+n);
					gfx.drawLine(x+n, y-n, x-n, y+n);
				} else {
					gfx.drawLine(x-n, y, x+n, y);
					gfx.drawLine(x, y-2, x, y+n);
				}
			}
		}
	}

	private static int[] makeSpace(int w, int h, long seed) {
		Rnd rnd = new Rnd(seed);
		int[] space = new int[w/2*6];
		int j = 0;
		for (int i = 0; i < w/2; i++) {
			int r = rnd.nextInt(38);
			int g = rnd.nextInt(38);
			int b = rnd.nextInt(38);
			space[j++] = max(r, g); // r
			space[j++] = g; // g
			space[j++] = min(g, b);  // b
			space[j++] = rnd.nextInt(70); // a
			space[j++] = rnd.nextInt(w); // x
			space[j++] = rnd.nextInt(h); // y
		}
		return space;
	}
}
