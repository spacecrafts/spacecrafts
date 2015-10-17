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
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.util.List;

import se.jbee.game.any.gfx.Obj;
import se.jbee.game.any.gfx.Styles;
import se.jbee.game.scs.gfx.Gfx;

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
		int rgba = obj[5];
		Color color = new Color(rgba);
		if (clip) {
			BufferedImage stex = styles.texture(TEXTURE_STAR_200x2000_SMALL);
			TexturePaint ss = new TexturePaint(stex, new Rectangle(x0, 0, stex.getWidth(), stex.getHeight()));
			BufferedImage ltex =  color.getGreen() > color.getBlue() && color.getBlue() < color.getRed()
					? styles.texture(TEXTURE_STAR_200x2000_LARGE_RED)
					: styles.texture(TEXTURE_STAR_200x2000_LARGE_BLUE);
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
				new Color[] { new Color(r,g,b,255), new Color(r,g,b, a), new Color(r,g,b/2, a*3/5), new Color(r,g,b/2, a*3/8), new Color(r,g,b, 0) });
		gfx.setPaint(paint);
		gfx.fillOval(x0+k, y0+k, rad, rad);
	}

	private void starClip(Graphics2D gfx, int x0, int y0, int d, int rgba, TexturePaint ls, TexturePaint ss) {
		Color c = new Color(rgba);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		Arc2D.Float arc = new Arc2D.Float(x0, y0, d, d, 150, 60, Arc2D.CHORD);
		Arc2D.Float arc2 = new Arc2D.Float(x0-1, y0, d, d, 150, 60, Arc2D.CHORD);

		gfx.setColor(c);
		gfx.fill(arc2);

		// 3d effect (as darkening)
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
				new float[] { 0f, 0.8f, 1f },
				new Color[] { new Color(r,g,b, 40), new Color(r,g,b, 150), new Color(r,g,b, 50) });
		gfx.setPaint(paint);
		gfx.fill(arc);
		// + 2 on x to make a glowing edge

		// texture
		gfx.setPaint(ss);
		gfx.fill(arc);

		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, d/4,
				new float[] { 0f, 1f },
				new Color[] { new Color(0,0,0, 50), new Color(r,g,b, 50)  }, CycleMethod.REFLECT);
		gfx.setPaint(paint);
		gfx.fill(arc);

		gfx.setPaint(ls);
		gfx.fill(arc2);
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
