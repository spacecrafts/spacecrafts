package se.jbee.game.scs.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;

import se.jbee.game.scs.gfx.noise.SimplexNoise;
import se.jbee.game.scs.process.Scene;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Object {

	private static final BufferedImage LARGE_PLANET = SimplexNoise.image(500, 500, 500, 60, 7000);
	private static final BufferedImage SMALL_PLANET = SimplexNoise.image(500, 500, 100, 40, 6000);

	@Override
	public void render(Scene scene, Dimension screen, Graphics2D gfx) {
		long drawStart = System.currentTimeMillis();
		
		antialias(gfx);
		textAntialias(gfx);
		render(screen, gfx, scene.objects.get());
		render(screen, gfx, scene.areaObjects.get());
		
		gfx.setFont(new Font(Font.MONOSPACED, 0 , 12));
		gfx.setColor(Color.WHITE);
		gfx.drawString(""+(System.currentTimeMillis() - drawStart), 20, 20);
	}

	private static void render(Dimension screen, Graphics2D gfx, List<int[]> objects) {
		for (int[] f : objects) {
			switch (f[0]) {
			case BACKGROUND:
				if (f[1] == 0) {
					gfx.setPaint(new GradientPaint(0, 0, new Color(0xA15FE3), 0, screen.height, new Color(0xE1AFF8)));
				} else {
					gfx.setColor(Color.black);
				}
				gfx.fillRect(0, 0, screen.width, screen.height);
//				gfx.setColor(Color.WHITE);
//				gfx.setFont(new Font(Font.MONOSPACED, 0, 100));
//				gfx.drawString("STARCRAFTS", 300, (screen.height-100)/2);
				break;
			case PLANET:
				planet(gfx, f[1], f[2], f[3], new Color(f[4]));
				break;
			case BORDER:
				gfx.setColor(Color.DARK_GRAY);
				gfx.drawRect(f[1], f[2], f[3], f[4]);
				break;
			case FOCUS_BOX:
				gfx.setColor(Color.CYAN);
				gfx.drawRect(f[1], f[2], f[3], f[4]);
				break;
			}
		}
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
		drawCircle(gfx, x0, y0, radius, new TexturePaint(LARGE_PLANET, new Rectangle(x0, y0, 500, 500)));
		drawCircle(gfx, x0, y0, radius, new TexturePaint(SMALL_PLANET, new Rectangle(x0-(radius/50), y0, 500, 500)));
		
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
