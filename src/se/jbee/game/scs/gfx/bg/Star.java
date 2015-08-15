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

	public static final Star CLIP = new Star(true);
	public static final Star CIRCLE = new Star(false);
	
	private final boolean clip;
	
	private Star(boolean clip) {
		super();
		this.clip = clip;
	}

	@Override
	public void paint(Palette palette, Graphics2D gfx, int x0, int y0, int w, int h, int... rand) {
		if (clip) {
			starClip(palette, gfx, x0, y0, w, rand);
		} else {
			//TODO
		}
	}

	private void starClip(Palette palette, Graphics2D gfx, int x0, int y0, int d, int... rand) {
		Color c = new Color(rand[0]);
		gfx.setColor(c);
		gfx.fillArc(x0, y0, d, d, 150, 60);
		
		// texture
		gfx.setPaint(new TexturePaint(palette.background(PAINT_STAR_NARROW), new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, d, d, 150, 60);			

		// 3d effect (as darkening)
		Paint paint = new RadialGradientPaint(x0+d/2,
				y0+d/2, d,
                new float[] { 0f, 0.8f, 1f },
                new Color[] { new Color(150,150,0, 40), new Color(150,150,0, 150), new Color(150,150,0, 50) });
		gfx.setPaint(paint);
		gfx.fillArc(x0+2, y0, d, d, 150, 60);
		// + 2 on x to make a glowing edge

		// darken upper and lower area
		paint = new LinearGradientPaint(x0, 0, x0, d/4, new float[] { 0f, 1f }, new Color[] { new Color(0,0,0, 50), new Color(255,255,100, 50)  }, CycleMethod.REFLECT);
		gfx.setPaint(paint);
		gfx.fillArc(x0, y0, d, d, 150, 60);
		
		gfx.setPaint(new TexturePaint(palette.background(PAINT_STAR_WIDE), new Rectangle(x0, y0, 200, 2000)));
		gfx.fillArc(x0, y0, d, d, 150, 60);
	}
}
