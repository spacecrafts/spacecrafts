package se.jbee.game.scs.gfx.art;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Artwork;
import se.jbee.game.uni.gfx.Styles;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.Rnd;

public class Background implements Artwork {

	private int _w;
	private int _h;
	private long _seed;
	private int[] precomputed;

	@Override
	public void paint(Graphics2D gfx, Styles styles, int x0, int y0, int w, int h, int... typeAndSeed) {
		switch (typeAndSeed[0]) {
		default:
		case Gfx.BG_BLACK: gfx.setColor(Color.black); gfx.fillRect(x0,y0,w,h); break;
		case Gfx.BG_SPACE: paintSpace(gfx, x0, y0, w, h, Entity.longValue(typeAndSeed[1], typeAndSeed[2])); break;
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
			gfx.setColor(new Color(200 + r, 200 + g, 200 + b, a));
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
				gfx.setColor(new Color(255, 255, 255, a));
				int n = a > 68 ? 2 : 1;
				if (a % 2 == 0) {
					gfx.drawLine(x-n, y-n, x+n, y+n);
					gfx.drawLine(x+n, y-n, x-n, y+n);
				} else {
					gfx.drawLine(x-n, y, x+n, y);
					gfx.drawLine(x, y-2, x, y+n);
				}
				if (a >= 68) {
					gfx.setColor(new Color(238, 238, 255, 120));
					if (a % 2 == 0) {
						gfx.drawLine(x-n, y, x+n, y);
						gfx.drawLine(x, y-2, x, y+n);
					} else {
						gfx.drawLine(x-n, y-n, x+n, y+n);
						gfx.drawLine(x+n, y-n, x-n, y+n);
					}
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