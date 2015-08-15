package se.jbee.game.scs.gfx.bg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import se.jbee.game.common.gfx.Backdrop;
import se.jbee.game.common.gfx.Palette;
import se.jbee.game.scs.gfx.Gfx;

public class Star implements Gfx, Backdrop {

	@Override
	public void paint(Palette palette, Graphics2D gfx, int x0, int y0, int w, int h, int... rand) {
		Color c = new Color(rand[0]);
		int radius = w;
		gfx.setColor(c);
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
		
		// texture
		gfx.setPaint(new TexturePaint(palette.background(PAINT_STAR_NARROW), new Rectangle(x0, y0, 200, 2000)));
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
		
		gfx.setPaint(new TexturePaint(palette.background(PAINT_STAR_WIDE), new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, radius, radius, 150, 60);
	}
}
