package se.jbee.game.scs.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * a utility class to draw basic shapes. 
 */
public class Painter implements Object {

	public static void paint(Graphics2D gfx, Dimension screen, List<int[]> objects) {
		for (int[] f : objects) {
			switch (f[0]) {
			case BACKGROUND:
				if (f[1] == 0) {
					gfx.setColor(new Color(0xB171F9));
				} else {
					gfx.setColor(Color.black);
				}
				gfx.fillRect(0, 0, screen.width, screen.height);
				break;
			case PLANET:
				Painter.planet(gfx, f[1], f[2], f[3], new Color(f[4]));
				break;
			case BORDER:
				gfx.setColor(Color.BLUE);
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
				
		// this paint creates a usual gradient that has a gentle horizontal scratch effect
		Paint pg = new GradientPaint(
				0, 0, c,
				0, 300, new Color(r*3/4,g*3/4,b*3/4),
				true);
		drawCircle(gfx, x0, y0, radius, pg);
		
		// dark edges for 3d effect
		pg = new RadialGradientPaint(x0+radius/2,
				y0+radius/2, radius/2,
                new float[] { 0f, 1f },
                new Color[] { new Color(r,g,b, 250), new Color(r/3,g/3,b/3, 250) });
		drawCircle(gfx, x0, y0, radius, pg);
		
        // star-light
        pg = new RadialGradientPaint(new Point2D.Double(x0 + radius,
                y0+(radius / 2.0)), radius/1.3f,
                new float[] { 0f, 1f },
                // it turned out that usual light appears somewhat blueish for most planet colors therefore this color change
                // but this can also be used to give different stars different light color.
                new Color[] { new Color(min(255,r+50),min(255,g+50),max(0,b-50), 100), new Color(0.0f, 0.0f, 0.0f, 0.6f) });
        drawCircle(gfx, x0, y0, radius, pg);
	}
	
	private static void drawCircle(Graphics2D gfx, int x0, int y0, int radius, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillOval(x0, y0, radius , radius);
		gfx.setPaint(old);
	}
}
