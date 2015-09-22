package se.jbee.game.scs.gfx.art;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Artwork;
import se.jbee.game.uni.gfx.Styles;

public class Planet implements Gfx, Artwork {

	public static final Planet CLIP = new Planet(true);
	public static final Planet CIRCLE = new Planet(false);
	
	private final boolean clip;
	
	private Planet(boolean clip) {
		super();
		this.clip = clip;
	}

	@Override
	public void paint(Graphics2D gfx, Styles styles, int x0, int y0, int w, int h, int... rand) {
		if (clip) {
			planetClip(styles, gfx, x0, y0, w, rand);
		} else {
			planetCircle(styles, gfx, x0, y0, w, rand);
		}
	}

	private void planetClip(Styles styles, Graphics2D gfx, int x0, int y0, int d, int... rand) {
		Color c = new Color(rand[0]);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getGreen();
		
		// 3d base bulb
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.84f, 0.9f },
                new Color[] { c, new Color(r/128,g/128,b/128), Color.black });
		drawClip(gfx, x0-1, y0, d+1, paint);
		
		// texture
		drawClip(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_200x2000_SMALL), new Rectangle(0, y0, 200, 2000)));
		
		// 3d effect (as darkening)
		paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.8f, 1f },
                new Color[] { new Color(150,150,150, 40), new Color(150,150,150, 150), new Color(150,150,150, 50) });
		gfx.setPaint(paint);
		gfx.fillArc(x0+2, y0, d, d, 150, 60);
		
		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, d/4, new float[] { 0f, 1f }, new Color[] { new Color(r/4,g/4,b/4, 100), new Color(r,g,b,100)  }, CycleMethod.REFLECT);
		drawClip(gfx, x0, y0, d, paint);
		
		drawClip(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_200x2000_LARGE), new Rectangle(0, y0, 200, 2000)));
	}	
	
	private void planetCircle(Styles styles, Graphics2D gfx, int x0, int y0, int d, int... rand) {
		Color c = new Color(rand[0]);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getGreen();
		
		// 3d effect
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.8f, 0.9f, 1f },
                new Color[] { new Color(r,g,b), new Color(r/8,g/8,b/8), new Color(r/128,g/128,b/128), Color.black });
		drawCircle(gfx, x0-1, y0, d+1, paint);
		
		// texture
		drawCircle(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_600x600_LARGE), new Rectangle(x0, y0, 600, 600)));
		drawCircle(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_600x600_SMALL), new Rectangle(x0-(d/50), y0, 600, 600)));
		
        // star-light
        Color lc = new Color(min(255,r+50),min(255,g+50),max(0,b-50), 100);
		paint = new RadialGradientPaint(new Point(x0 + d+(d/3),
                y0+(d / 2)), d,
                new float[] { 0f, 1f },
                // it turned out that usual light appears somewhat blueish for most planet colors therefore this color change
                // but this can also be used to give different stars different light color.
                new Color[] { lc, new Color(0.0f, 0.0f, 0.0f, 0.4f) });
				// using the original color c as 1st param almost appears as some kind of shield
        drawCircle(gfx, x0-1, y0, d+1, paint);
	}
	
	private static void drawCircle(Graphics2D gfx, int x0, int y0, int d, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillOval(x0, y0, d , d);
		gfx.setPaint(old);
	}
	
	private static void drawClip(Graphics2D gfx, int x0, int y0, int d, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillArc(x0, y0, d, d, -30, 60);
		gfx.setPaint(old);
	}
	
}
