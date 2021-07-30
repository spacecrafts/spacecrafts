package se.jbee.game.any.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;

import se.jbee.game.any.state.Rnd;

public final class RGBA {

	private final Color c;
	
	public RGBA(int[] rgba) {
		this.c = rgba.length == 3 
				? new Color(rgba[0], rgba[1], rgba[2])
				: new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
	}
	
	public RGBA(int rgb) {
		this.c = new Color(rgb);
	}
	
	private RGBA(Color c) {
		super();
		this.c = c;
	}

	public RGBA shift(Rnd rnd, int delta) {
		int r = max(0, min(255, c.getRed()+rnd.nextInt(-delta, delta)));
		int g = max(0, min(255, c.getGreen()+rnd.nextInt(-delta, delta)));
		int b = max(0, min(255, c.getBlue()+rnd.nextInt(-delta, delta)));
		int a = max(0, min(255, c.getAlpha()+rnd.nextInt(-delta, delta)));
		return new RGBA(new Color(r,g,b,a));
	}
	
	public int toRGBA() {
		return c.getRGB();
	}
	
	@Override
	public String toString() {
		return c.toString();
	}
}
