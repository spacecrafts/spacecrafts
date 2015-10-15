package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Styles;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.Rnd;

public class Star implements Gfx, Obj {

	public static final Star CLIP = new Star(true);
	public static final Star CIRCLE = new Star(false);

	private final boolean clip;

	private Star(boolean clip) {
		super();
		this.clip = clip;
	}

	@Override
	public void draw(Graphics2D gfx, Styles styles, List<int[]> data) {
		int[] obj = data.get(0);
		int x0 = obj[2];
		int y0 = obj[3];
		int w = obj[4];
		int[] rnd = data.get(1);
		int rgba = color(Entity.longValue(rnd[0], rnd[1])).getRGB();
		if (clip) {
			BufferedImage stex = styles.texture(TEXTURE_STAR_200x2000_SMALL);
			TexturePaint ss = new TexturePaint(stex, new Rectangle(x0, 0, stex.getWidth(), stex.getHeight()));
			BufferedImage ltex = styles.texture(TEXTURE_STAR_200x2000_LARGE);
			TexturePaint ls = new TexturePaint(ltex, new Rectangle(x0, 0, ltex.getWidth(), ltex.getHeight()));
			starClip(gfx, x0, y0, w, rgba, ls, ss);
		} else {
			starCircle(gfx, x0, y0, w, rgba);
		}
	}

	private void starCircle(Graphics2D gfx, int x0, int y0, int dia, int rgba) {
		int r = new Color(rgba).getRed();
		int g = new Color(rgba).getGreen();
		int b = new Color(rgba).getBlue();
		int a = new Color(rgba, true).getAlpha();

		int k = dia/4;
		int rad = 2*k;

		Paint paint = new RadialGradientPaint(x0+rad, y0+rad, rad,
				new float[] { 0f, 0.2f, 0.6f, 0.8f, 1f },
				new Color[] { new Color(r,g,255,255), new Color(r,g,b, a), new Color(r,g,b/2, a*3/5), new Color(r,g,b/2, a*3/8), new Color(r,g,b, 0) });
		gfx.setPaint(paint);
		gfx.fillOval(x0+k, y0+k, rad, rad);
	}

	public static Color color(long seed) {
		// brown: 200, 125, 100
		// orange: 250, 180, 50 (less green makes more red, blue is constant 50)
		// purple: 200, 50, 250
		// teal: 200, 250, 250 (more red makes it more white, gb are fix)

		Rnd rnd = new Rnd(seed);
		int dist = rnd.nextInt(255);
		int r = rnd.nextInt(200, 255);
		int g = min(255, rnd.nextInt(120, 150)+dist/4);
		int b = max(0, min(255, rnd.nextInt(60, 100)-dist/3));
		int a = rnd.nextInt(220, 255);
		if ((dist % 2 == 1)) {
			if (dist < 50) { // red
				g = g/2;
				b = b/2;
			}
			if (dist > 200) { // purple
				g = g/2;
				b = r*3/5;
			}
		}
		if ((dist % 2) == 0 && dist > 150) { // blue
			r = max(0, r-dist/2);
			g = min(255, g + dist/2);
			b = min(255, b + dist);
		}
		return new Color(r, g, b, a);
	}

	private void starClip(Graphics2D gfx, int x0, int y0, int d, int rgba, TexturePaint ls, TexturePaint ss) {
		Color c = new Color(rgba);
		gfx.setColor(c);
		gfx.fillArc(x0, y0, d, d, 150, 60);

		// texture
		gfx.setPaint(ss);
		gfx.fillArc(x0, y0, d, d, 150, 60);

		// 3d effect (as darkening)
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
				new float[] { 0f, 0.8f, 1f },
				new Color[] { new Color(150,150,0, 40), new Color(150,150,0, 150), new Color(150,150,0, 50) });
		gfx.setPaint(paint);
		gfx.fillArc(x0+2, y0, d, d, 150, 60);
		// + 2 on x to make a glowing edge

		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, d/4, new float[] { 0f, 1f }, new Color[] { new Color(0,0,0, 50), new Color(255,255,100, 50)  }, CycleMethod.REFLECT);
		gfx.setPaint(paint);
		gfx.fillArc(x0, y0, d, d, 150, 60);

		gfx.setPaint(ls);
		gfx.fillArc(x0, y0, d, d, 150, 60);
	}

	public static BufferedImage image(int w, int h, float[] noise) {
		BufferedImage image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[w * h];
		for (int k = 0; k < pixels.length; k++) {
			float rgb = noise[k];
			rgb = min(1f, max(0f, rgb));
			float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
			Color c = new Color(1f, rgb, rgb/2f, a);
			pixels[k++] = c.getRGB();
		}
		image.getRaster().setDataElements(0,  0,  w, h, pixels);
		return image;
	}
}
