package se.jbee.game.scs.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Dimension;
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
import java.util.List;

import se.jbee.game.common.gfx.Palette;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.process.Scene;
import se.jbee.game.scs.gfx.bg.Space;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Gfx {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectange that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f
	
	private final Space bgSpace=new Space();
	
	private int frameDone = -1;

	@Override
	public void render(Scene scene, Dimension screen, Palette palette, Graphics2D gfx) {
		int frame = scene.frame();
		if (frame != frameDone) {
			frameDone = frame;
			long drawStart = System.currentTimeMillis();

			antialias(gfx);
			textAntialias(gfx);
			
			render(palette, gfx, scene.objects.get());
			render(palette, gfx, scene.accents());
			
			gfx.setFont(palette.font(FONT_REGULAR, 12));
			gfx.setColor(Color.WHITE);
			gfx.drawString(""+(System.currentTimeMillis() - drawStart), 20, 20);
		}
		
	}

	private void render(Palette palette, Graphics2D gfx, List<int[]> objects) {
		for (int i = 0; i < objects.size(); i++) {
			int[] obj = objects.get(i);
			switch (obj[0]) {
			case OBJ_BACKGROUND:
				int bgtype = obj[5];
				if (bgtype == 0) {
					gfx.setPaint(new GradientPaint(0, 0, new Color(0xA15FE3), 0, obj[4], new Color(0xE1AFF8)));
					gfx.fillRect(obj[1], obj[2], obj[3], obj[4]);
				} else if (bgtype == 2) {
					gfx.setColor(Color.black);
					gfx.fillRect(obj[1], obj[2], obj[3], obj[4]);
				} else {
					bgSpace.space(gfx, obj[1], obj[2], obj[3], obj[4]);
				}
				break;
			case OBJ_TEXT:
				gfx.setColor(palette.color(obj[5]));
				if (obj[3] == FONT_DOTS) {
					SCSFont.draw(gfx, obj[1], obj[2], obj[4], objects.get(++i));
				} else {
					gfx.setFont(palette.font(obj[3], obj[4]));
					String str = "";
					int xt = obj[1];
					for (int j = 0; j < obj[6]; j++) {
						if (j > 0) {
							xt += gfx.getFontMetrics().stringWidth(str);
							xt += gfx.getFontMetrics().stringWidth(" ");
						}
						int[] text = objects.get(++i);
						str = new String(text, 0, text.length);
						gfx.drawString(str, xt, obj[2]);
					}
				}
				break;
			case OBJ_PLANET:
				planet(palette, gfx, obj[1], obj[2], obj[3], new Color(obj[4]));
				break;
			case OBJ_STAR_ARC:
				stararc(palette, gfx, obj[1], obj[2], obj[3], new Color(obj[4]));
				break;
			case OBJ_BORDER:
				gfx.setColor(new Color(0x8899FF));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			case OBJ_FOCUS_BOX:
				gfx.setColor(palette.color(COLOR_TEXT_NORMAL));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			}
		}
	}
	
	private static void stararc(Palette palette, Graphics2D gfx, int x0, int y0, int radius, Color c) {
		gfx.setColor(c);
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
		
		// texture
		gfx.setPaint(new TexturePaint(palette.background(BACK_STAR_NARROW), new Rectangle(x0, y0, 200, 2000)));
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
		
		gfx.setPaint(new TexturePaint(palette.background(BACK_STAR_WIDE), new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
	}

	public static void planet(Palette palette, Graphics2D gfx, int x0, int y0, int radius, Color c) {
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
		drawCircle(gfx, x0, y0, radius, new TexturePaint(palette.background(BACK_PLANET_WIDE), new Rectangle(x0, y0, 500, 500)));
		drawCircle(gfx, x0, y0, radius, new TexturePaint(palette.background(BACK_PLANET_NARROW), new Rectangle(x0-(radius/50), y0, 500, 500)));
		
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
