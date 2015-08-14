package se.jbee.game.scs.gfx;

import java.awt.Graphics2D;

/**
 * Defines the SPACECRAFTS "font". That is a 5 rows 4 columns dot font.
 */
public final class SCSFont {

	private static final int
		A = 0b0110_1001_1111_1001_1001,
		B = 0b1110_1001_1111_1001_1110,
		C = 0b0111_1000_1000_1000_0111,
		D = 0b1110_1001_1001_1001_1110,
		E = 0b1111_1000_1110_1000_1111,
		F = 0b1111_1000_1110_1000_1000,
		G = 0b0110_1001_1011_1001_0110,
		H = 0b1001_1001_1111_1001_1001,
		I = 0b0110_0110_0110_0110_0110,
		J = 0b1111_0001_0001_1001_0110,
		K = 0b1001_1010_1100_1010_1001,
		L = 0b1000_1000_1000_1000_1111,
		M = 0b1001_1111_1001_1001_1001,
		N = 0b1001_1101_1010_1010_1001,
		O = 0b0110_1001_1001_1001_0110,
		P = 0b1110_1001_1110_1000_1000,
		Q = 0b0110_1001_1001_1001_0111,
		R = 0b1110_1001_1110_1001_1001,
		S = 0b0111_1000_0110_0001_1110,
		T = 0b1111_0110_0110_0110_0110,
		U = 0b1001_1001_1001_1001_0110,
		V = 0b1001_1001_1001_0110_0110,
		W = 0b1001_1001_1001_1111_1111,
		X = 0b1001_1111_0110_1111_1001,
		Y = 0b1001_1001_0110_0110_0110,
		Z = 0b1111_0010_0100_1000_1111;
		
	private static final int[] AZ = {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z};
	
	public static void draw(Graphics2D gfx, int x0, int y0, int diameter, int[] text) {
		int x = x0;
		int y = y0;
		for (int c : text) {
			int letter = AZ[c-'A'];
			int n = 0;
			for (int bit = 19; bit >= 0; bit--) {
				if (((1 << bit) & letter) > 0) {
					gfx.fillOval(x, y, diameter, diameter);
				}
				if (n == 3) {
					n = 0;
					y += diameter;
					x = x0;
				} else {
					x += diameter;
					n++;
				}
			}
			x0 += 5*diameter;
			y = y0;
			x = x0;
		}
	}
}
