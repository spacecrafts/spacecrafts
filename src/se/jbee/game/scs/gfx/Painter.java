package se.jbee.game.scs.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 * a utility class to draw basic shapes. 
 */
public class Painter {

	public static void planet(Graphics2D g2d, int x0, int y0, int radius, Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getGreen();
				
		// this paint creates a usual gradient that has a gentle horizontal scratch effect
		Paint pg = new GradientPaint(
				0, 0, c,
				0, 300, new Color(r*3/4,g*3/4,b*3/4),
				true);
		drawCircle(g2d, x0, y0, radius, pg);
		
		// dark edges for 3d effect
		pg = new RadialGradientPaint(x0+radius/2,
				y0+radius/2, radius/2,
                new float[] { 0f, 1f },
                new Color[] { new Color(r,g,b, 250), new Color(r/3,g/3,b/3, 250) });
		drawCircle(g2d, x0, y0, radius, pg);
		
        // star-light
        pg = new RadialGradientPaint(new Point2D.Double(x0 + radius,
                y0+(radius / 2.0)), radius/1.3f,
                new float[] { 0f, 1f },
                // it turned out that usual light appears somewhat blueish for most planet colors therefore this color change
                // but this can also be used to give different stars different light color.
                new Color[] { new Color(min(255,r+50),min(255,g+50),max(0,b-50), 100), new Color(0.0f, 0.0f, 0.0f, 0.6f) });
        drawCircle(g2d, x0, y0, radius, pg);
	}
	
	private static void drawCircle(Graphics2D g2d, int x0, int y0, int radius, Paint paint) {
		Paint old = g2d.getPaint();
		g2d.setPaint(paint);
		g2d.fillOval(x0, y0, radius , radius);
		g2d.setPaint(old);
	}
}
