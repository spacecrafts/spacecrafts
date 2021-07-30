package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import se.jbee.game.any.gfx.Drawable;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.texture.Colouring;
import se.jbee.game.scs.Spacecrafts;
import se.jbee.game.scs.gfx.Gfx;

public final class Planet implements Gfx, Drawable {

	private final int x0;
	private final int y0;
	private final int w;
	private final int type;
	private final int rgba;
	private final boolean partial;

	public Planet(int x0, int y0, int dia, int type, int rgba, boolean partial) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.w = dia;
		this.type = type;
		this.rgba = rgba;
		this.partial = partial;
	}

	@Override
	public void draw(Graphics2D gfx, Resources resources) {
		if (partial) {
			partialPlanet(resources, gfx, x0, y0, w, rgba);
		} else {
			planet(resources, gfx, x0, y0, w, rgba);
		}
	}

	private static void partialPlanet(Resources styles, Graphics2D gfx, int x0, int y0, int d, int rand) {
		Color c = new Color(rand);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getGreen();

		// 3d base bulb
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.84f, 0.9f },
                new Color[] { c, new Color(r/128,g/128,b/128), Color.black });
		drawArc(gfx, x0-1, y0, d+1, paint);

		// texture
		drawArc(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_200x2000_SMALL), new Rectangle(0, y0, 200, 2000)));

		// 3d effect (as darkening)
		paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.8f, 1f },
                new Color[] { new Color(150,150,150, 40), new Color(150,150,150, 150), new Color(150,150,150, 50) });
		gfx.setPaint(paint);
		gfx.fillArc(x0+2, y0, d, d, 150, 60);

		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, d/4, new float[] { 0f, 1f }, new Color[] { new Color(r/4,g/4,b/4, 100), new Color(r,g,b,100)  }, CycleMethod.REFLECT);
		drawArc(gfx, x0, y0, d, paint);

		drawArc(gfx, x0, y0, d, new TexturePaint(styles.texture(TEXTURE_PLANET_200x2000_LARGE), new Rectangle(0, y0, 200, 2000)));
	}

	private static void planet(Resources styles, Graphics2D gfx, int x0, int y0, int dia, int rgba) {
		Color c = new Color(rgba);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		// 3d effect
		Paint paint = new RadialGradientPaint(x0+dia/2,
				y0+dia/2, max(1,dia/2),
                new float[] { 0f, 0.9f, 0.95f, 1f },
                new Color[] { new Color(r,g,b), new Color(r/4,g/4,b/4), new Color(r/8,g/8,b/8), Color.black });
		drawCircle(gfx, x0-1, y0, dia+1, paint);

		// texture
		if (false) {
		drawCircle(gfx, x0, y0, dia, new TexturePaint(styles.texture(TEXTURE_PLANET_600x600_LARGE), new Rectangle(x0, y0, 600, 300)));
		drawCircle(gfx, x0, y0, dia, new TexturePaint(styles.texture(TEXTURE_PLANET_600x600_SMALL), new Rectangle(x0/2, y0, 1600, 600)));
		}
        // star-light
        Color lc = new Color(min(255,r+50),min(255,g+50),max(0,b-50), 150);
		paint = new RadialGradientPaint(new Point(x0 + dia+(dia/4),
                y0+(dia / 2)), max(1,dia),
                new float[] { 0f, 1f },
                // it turned out that usual light appears somewhat blueish for most planet colors therefore this color change
                // but this can also be used to give different stars different light color.
                new Color[] { lc, new Color(0.0f, 0.0f, 0.0f, 0.6f) });
				// using the original color c as 1st param almost appears as some kind of shield
        drawCircle(gfx, x0-1, y0, dia+1, paint);
        
        gfx.setComposite(AlphaComposite.SrcOver);
        paint = new TexturePaint(styles.texture(TEXTURE_TEST), new Rectangle(x0, y0, 900, 300));
        drawCircle(gfx, x0, y0, dia, paint);
        gfx.setComposite(AlphaComposite.Src);
	}

	private static void drawCircle(Graphics2D gfx, int x0, int y0, int d, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillOval(x0, y0, d , d);
		gfx.setPaint(old);
	}

	private static void drawArc(Graphics2D gfx, int x0, int y0, int d, Paint paint) {
		Paint old = gfx.getPaint();
		gfx.setPaint(paint);
		gfx.fillArc(x0, y0, d, d, -30, 60);
		gfx.setPaint(old);
	}

}
