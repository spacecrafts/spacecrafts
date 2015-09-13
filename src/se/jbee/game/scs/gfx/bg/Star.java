package se.jbee.game.scs.gfx.bg;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.uni.gfx.Backdrop;
import se.jbee.game.uni.gfx.Styles;

public class Star implements Gfx, Backdrop {

	public static final Star CLIP = new Star(true);
	public static final Star CIRCLE = new Star(false);
	
	private final boolean clip;
	
	private Star(boolean clip) {
		super();
		this.clip = clip;
	}

	@Override
	public void paint(Styles styles, Graphics2D gfx, int x0, int y0, int w, int h, int... rand) {
		if (clip) {
			BufferedImage stex = styles.texture(TEXTURE_STAR_200x2000_SMALL);
			TexturePaint ss = new TexturePaint(stex, new Rectangle(x0, 0, stex.getWidth(), stex.getHeight()));
			BufferedImage ltex = styles.texture(TEXTURE_STAR_200x2000_LARGE);
			TexturePaint ls = new TexturePaint(ltex, new Rectangle(x0, 0, ltex.getWidth(), ltex.getHeight()));
			int rgba = rand[0];
			rgba = 0x000000;
			starClip(styles, gfx, x0, y0, w, rgba, ls, ss);
		} else {
			starCircle(styles, gfx, x0, y0, w, rand);
		}
	}

	private void starCircle(Styles styles, Graphics2D gfx, int x0, int y0, int dia, int... rand) {
		// brown: 200, 125, 100
		// orange: 250, 180, 50 (less green makes more red, blue is constant 50)
		// purple: 200, 50, 250
		// teal: 200, 250, 250 (more red makes it more white, gb are fix)
		// colors...
		int t = rand[0];
		int r = 255;
		int g = 150+t/4;
		int b = 85-t/3;
		if ((t % 2) == 0 && t > 150) {
			r = 255-t/4;
			g = 255;
			b = 255;
		}
		// size correction (larger => brighter)
		r = min(255, r*14/dia);
		g = min(255, g*14/dia);
		b = min(255, b*14/dia);

		int rad = dia/2;
		int xl = x0-rad;
		int yl = y0-rad;
		int dl = dia*2;
		Paint paint = new RadialGradientPaint(xl+dl/2,
				yl+dl/2, dl,
                new float[] { 0f, 0.35f, 1f },
                new Color[] { new Color(r,g,b, 40), new Color(r/3, g/3, b/3, 25), new Color(0,0,0) });
		gfx.setPaint(paint);
		gfx.fillOval(xl, yl, dl, dl);		

		if (t > 200) {

			paint = new RadialGradientPaint(xl+dl/2,
					yl+dl/2, dl,
					new float[] { 0f, 0.5f, 1f },
					new Color[] { new Color(r,g,b, 60), new Color(r,g,b, 15), new Color(r,g,b, 0) });
			//                new Color[] { new Color(0x40FFee00, true), new Color(0x25FF8800, true), new Color(0,0,0) });
			gfx.setPaint(paint);
			Stroke s = gfx.getStroke();
			int len = 0;
			gfx.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			gfx.drawLine(x0+rad, y0-len, x0+rad, y0+dia+len);
			gfx.drawLine(x0-len, y0+rad, x0+dia+len, y0+rad);
			gfx.setStroke(s);

			gfx.setColor(new Color(255,255,255,50));
			gfx.drawLine(x0+rad, y0+rad/2, x0+rad, y0+dia-rad/2);
			gfx.drawLine(x0+rad/2, y0+rad, x0+dia-rad/2, y0+rad);
		}
		
		paint = new RadialGradientPaint(x0+rad,
				y0+rad, rad,
                new float[] { 0f, 0.20f, 0.5f, 1f },
                new Color[] { new Color(r,g,b, 255), new Color(r,g,b, 100), new Color(r,g,b, 0), new Color(0,0,0) });                
//                new Color[] { new Color(0xaaFFFFaa, true), new Color(0x10FFFFaa, true), new Color(0,0,0,50) });
		gfx.setPaint(paint);
		gfx.fillOval(x0+rad/2, y0+rad/2, rad, rad);
		
	}
	
	private void starClip(Styles styles, Graphics2D gfx, int x0, int y0, int d, int rgba, TexturePaint ls, TexturePaint ss) {
		Color c = new Color(rgba);
		gfx.setColor(c);
		gfx.fillArc(x0, y0, d, d, 150, 60);
		
		// texture
		gfx.setPaint(ss);
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
		
		gfx.setPaint(ls);
		gfx.fillArc(x0, y0, d, d, 150, 60);
	}
	
	public static BufferedImage image(int w, int h, float[] noise) {
		BufferedImage image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[w * h];
		for (int k = 0; k < pixels.length; k++) {
			float rgb = noise[k];
			rgb = min(1f, max(0f, rgb));
			float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
			Color c = new Color(1f, rgb, rgb/2f, a); 
			pixels[k++] = c.getRGB();
		}
		image.getRaster().setDataElements(0,  0,  w, h, pixels);
		return image;
	}
}
