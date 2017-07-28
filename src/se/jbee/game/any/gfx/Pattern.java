package se.jbee.game.any.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import se.jbee.game.any.state.Rnd;

@Deprecated // was just a test for an idea so far
public class Pattern {

	final int[][] cells;
	final int[][] splits;
	
	public Pattern(int[][] cells, int[][] splits) {
		super();
		this.cells = cells;
		this.splits = splits;
	}

	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		int w = 300;
		int h = 300;
		write(draw(w, h));
		long e = System.currentTimeMillis();
		System.out.println((e-s)+" millis");
	}

	private static final class Pixels {
		final int w;
		final int h;
		final int[] pixels;
		
		Pixels(int w, int h) {
			this.w = w;
			this.h = h;
			this.pixels = new int[w*h];
		}
		
		void set(int x, int y, int argb) {
			pixels[(y*w)+x] = argb;
		}
		
		int get(int x, int y) {
			return pixels[(y*w)+x];
		}
		
		boolean isSet(int x, int y) {
			return get(x, y) != 0;
		}
		
		public BufferedImage toImage() {
	        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	        for (int i = 0; i < img.getWidth(); i++) {
	            for (int j = 0; j < img.getHeight(); j++) {
	            	img.setRGB(i, j, get(i, j));
	            }
	        }
	        return img;
	    }
	}
	
	static BufferedImage draw(int w, int h) {
		Rnd rnd = new Rnd(System.currentTimeMillis());
		Pixels pixels = new Pixels(w, h);
		LinkedList<Point> next = new LinkedList<Point>();
		add(pixels, next,  w/2,h/2);
		while (!next.isEmpty() && next.size() < 100) {
			Point c = next.pollFirst();
			int x = c.x;
			int y = c.y;
			int[] dxs = { 1, 2, 1 };
			int[] dys = { -1, 1, 2 };
			for (int i = 0; i < 1; i++) {
				int dx = dxs[i];
				int dy = dys[i];
				set(pixels, x+dx,y+dy, 0xFFFF0000);
			}
			for (int i = 0; i < 1; i++) {
				int dx = dxs[i];
				int dy = dys[i];
				add(pixels, next, x+dx, y+dy);
			}
		}
		return pixels.toImage();
	}
	
	private static void set(Pixels pixels, int x, int y, int rgba) {
		if (x > 0 && y > 0 && x < pixels.w && y < pixels.h) {
			pixels.set(x, y, rgba);
		}
	}
	
	private static void add(Pixels pixels, LinkedList<Point> l, int x, int y) {
		if (x > 0 && y > 0 && x < pixels.w && y < pixels.h) {
			l.add(new Point(x, y));
		}
	}
	
	static void split(int x, int y, int cell, Pattern pattern, Pixels pixels) {
		
	}
	
	public static void write(BufferedImage img) {
		try {
		    File outputfile = new File("saved.png");
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
