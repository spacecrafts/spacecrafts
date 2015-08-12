package se.jbee.game.scs.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;

import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.gfx.SimplexNoise;
import se.jbee.game.common.process.Scene;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Object {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectange that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f
	
	private static final BufferedImage SPACE = SimplexNoise.image(500, 500, 20, 60, 45, 0.3f, 0.99f, 0xFFFFFF0F);
	
	private static final BufferedImage ROUGH_STAR = SimplexNoise.image(200, 2000, 500, 80, 666, 0.15f, 0f, 0xFFFFFFFF);
	private static final BufferedImage FINE_STAR = SimplexNoise.image(200, 2000, 50, 60, 700, 0.15f, 0f, 0xFFFFFFFF);
	
	private static final BufferedImage ROUGH_PLANET = SimplexNoise.image(500, 500, 500, 60, 7000, 0.15f, 0f, 0xFFFFFFFF);
	private static final BufferedImage FINE_PLANET = SimplexNoise.image(500, 500, 100, 40, 6000, 0.15f, 0f, 0xFFFFFFFF);

	@Override
	public void render(Scene scene, Dimension screen, Graphics2D gfx) {
		long drawStart = System.currentTimeMillis();
		
		antialias(gfx);
		textAntialias(gfx);
		render(gfx, scene.objects.get());
		render(gfx, scene.areaObjects.get());
		
		gfx.setFont(new Font(Font.MONOSPACED, 0 , 12));
		gfx.setColor(Color.WHITE);
		gfx.drawString(""+(System.currentTimeMillis() - drawStart), 20, 20);
	}

	private static void render(Graphics2D gfx, List<int[]> objects) {
		for (int i = 0; i < objects.size(); i++) {
			int[] obj = objects.get(i);
			switch (obj[0]) {
			case BACKGROUND:
				if (obj[5] == 0) {
					gfx.setPaint(new GradientPaint(0, 0, new Color(0xA15FE3), 0, obj[4], new Color(0xE1AFF8)));
					gfx.fillRect(obj[1], obj[2], obj[3], obj[4]);
				} else {
					space(gfx, obj[1], obj[2], obj[3], obj[4]);
				}
//				gfx.setColor(Color.WHITE);
//				gfx.setFont(new Font(Font.MONOSPACED, 0, 100));
//				gfx.drawString("STARCRAFTS", 300, (screen.height-100)/2);
				break;
			case TEXT:
				gfx.setColor(new Color(0x8899FF));
				gfx.setFont(Fonts.light(obj[3]));
				String str = "";
				int xt = obj[1];
				for (int j = 0; j < obj[4]; j++) {
					if (j > 0) {
						xt += gfx.getFontMetrics().stringWidth(str);
						xt += gfx.getFontMetrics().stringWidth(" ");
					}
					int[] text = objects.get(++i);
					str = new String(text, 0, text.length);
					gfx.drawString(str, xt, obj[2]);
				}
				break;
			case PLANET:
				planet(gfx, obj[1], obj[2], obj[3], new Color(obj[4]));
				break;
			case STAR_ARC:
				stararc(gfx, obj[1], obj[2], obj[3], new Color(obj[4]));
				break;
			case BORDER:
				gfx.setColor(new Color(0x8899FF));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			case FOCUS_BOX:
				gfx.setColor(new Color(0x8899FF));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			}
		}
	}
	
	private static void space(Graphics2D gfx, int x0, int y0, int w, int h) {
		gfx.setColor(Color.black);
		gfx.fillRect(x0, y0, w, h);
		gfx.setPaint(new TexturePaint(SPACE, new Rectangle(x0, y0, 500, 500)));
		gfx.fillRect(x0, y0, w, h);
	}
	
	private static void stararc(Graphics2D gfx, int x0, int y0, int radius, Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		gfx.setColor(c);
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
		
		// texture
		gfx.setPaint(new TexturePaint(FINE_STAR, new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, radius, radius, 150, 60);			

		// 3d effect (as darkening)
		Paint paint = new RadialGradientPaint(x0+radius/2,
				y0+radius/2, radius,
                new float[] { 0f, 0.8f, 1f },
                new Color[] { new Color(150,150,0, 40), new Color(150,150,0, 150), new Color(150,150,0, 50) });
		gfx.setPaint(paint);
		gfx.fillArc(x0+2, y0, radius, radius, 150, 60);
		// + 2 on x to make a glowing edge

		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, radius/4, new float[] { 0f, 1f }, new Color[] { new Color(0,0,0, 50), new Color(255,255,100, 50)  }, CycleMethod.REFLECT);
		gfx.setPaint(paint);
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
		
		gfx.setPaint(new TexturePaint(ROUGH_STAR, new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
	}

	public static void planet(Graphics2D gfx, int x0, int y0, int radius, Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getGreen();
		
		// 3d effect
		Paint paint = new RadialGradientPaint(x0+radius/2,
				y0+radius/2, radius,
                new float[] { 0f, 0.8f, 0.9f, 1f },
                new Color[] { new Color(r,g,b), new Color(r/8,g/8,b/8), new Color(r/128,g/128,b/128), Color.black });
		drawCircle(gfx, x0-1, y0, radius+1, paint);
		
		// texture
		drawCircle(gfx, x0, y0, radius, new TexturePaint(ROUGH_PLANET, new Rectangle(x0, y0, 500, 500)));
		drawCircle(gfx, x0, y0, radius, new TexturePaint(FINE_PLANET, new Rectangle(x0-(radius/50), y0, 500, 500)));
		
        // star-light
        Color lc = new Color(min(255,r+50),min(255,g+50),max(0,b-50), 100);
		paint = new RadialGradientPaint(new Point(x0 + radius+(radius/3),
                y0+(radius / 2)), radius,
                new float[] { 0f, 1f },
                // it turned out that usual light appears somewhat blueish for most planet colors therefore this color change
                // but this can also be used to give different stars different light color.
                new Color[] { lc, new Color(0.0f, 0.0f, 0.0f, 0.4f) });
				// using the original color c as 1st param almost appears as some kind of shield
        drawCircle(gfx, x0-1, y0, radius+1, paint);
	}
	
	public static void textAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	public static void noTextAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}
	
	public static void antialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	public static void noAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	private static void drawCircle(Graphics2D gfx, int x0, int y0, int radius, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillOval(x0, y0, radius , radius);
		gfx.setPaint(old);
	}
}
