package se.jbee.game.scs.gfx.bg;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import se.jbee.game.common.gfx.Backdrop;
import se.jbee.game.common.gfx.Palette;
import se.jbee.game.scs.gfx.Gfx;

public class Planet implements Gfx, Backdrop {

	@Override
	public void paint(Palette palette, Graphics2D gfx, int x0, int y0, int w, int h, int... rand) {
		int radius = w;
		Color c = new Color(rand[0]);
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
		drawCircle(gfx, x0, y0, radius, new TexturePaint(palette.background(PAINT_PLANET_WIDE), new Rectangle(x0, y0, 500, 500)));
		drawCircle(gfx, x0, y0, radius, new TexturePaint(palette.background(PAINT_PLANET_NARROW), new Rectangle(x0-(radius/50), y0, 500, 500)));
		
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
	
	private static void drawCircle(Graphics2D gfx, int x0, int y0, int radius, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillOval(x0, y0, radius , radius);
		gfx.setPaint(old);
	}

}
