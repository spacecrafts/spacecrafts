package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.scs.gfx.Gfx;

public final class Background implements Gfx, GfxObj {

	private final int x0;
	private final int y0;
	private final int w;
	private final int h;
	private final int type;
	private final long seed;
	
	private int cachedWidth;
	private int cachedHeight;
	private long cachedSeed;
	private int[] cachedStars;

	public Background(int x0, int y0, int w, int h, int type, long seed) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.w = w;
		this.h = h;
		this.type = type;
		this.seed = seed;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {

		switch (type) {
		default:
		case Gfx.BG_BLACK: gfx.setColor(Color.black); gfx.fillRect(x0,y0,w,h); break;
		case Gfx.BG_SPACE: paintSpace(gfx, x0, y0, w, h, seed); break;
		}

	}

	private void paintSpace(Graphics2D gfx, int x0, int y0, int w, int h, long seed) {
		GradientPaint gp = new GradientPaint(0, 0, new Color(18, 0, 10), 0, h, new Color(10, 0, 25));
		gfx.setColor(Color.black);
		gfx.setPaint(gp);
		gfx.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		gfx.fillRect(x0, y0, w, h);
		if (w != cachedWidth || h != cachedHeight || cachedSeed != seed) {
			cachedStars = computeStars(w, h, seed);
			cachedWidth = w;
			cachedHeight = h;
			cachedSeed = seed;
		}
		int j = 0;
		int[] stars = cachedStars;
		drawGlow(gfx, stars);
		for (int i = 0; i < w/2; i++) {
			int r = stars[j++];
			int g = stars[j++];
			int b = stars[j++];
			int a = stars[j++];
			if (r > b) {
				gfx.setColor(new Color(200 + r, 200 + g, 50 + b+a, a));
			} else {
				gfx.setColor(new Color(100 + r+a, 200 + g, 217 + b, a));
			}
			int x = stars[j++];
			int y = stars[j++];
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
	
	private static void drawGlow(Graphics2D gfx, int[] stars) {
		if (true)
			return;
		// below is buggy...
		int i = 0; 
		while (i < stars.length) {
			if (stars[i+3] > 65) {
				int x = stars[i+4];
				int y = stars[i+5];
				int a = stars[i+3];
				int j = a;
				while (j > 0) {
					Color c = new Color(0, 0, 255, a);
					gfx.setColor(c);
					gfx.drawLine(x, y, x, y);
					j--;
					x++;
					y++;
				}
			}
			i+=6;
		}
	}

	private static int[] computeStars(int w, int h, long seed) {
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
