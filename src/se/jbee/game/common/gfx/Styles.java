package se.jbee.game.common.gfx;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * A utility to manage graphic related resources like colors and fonts.
 */
public final class Styles {

	private final Color[] colors;
	private final Font[][] fonts;
	private final Noise[] noises;

	private final Supplier<Font>[] lazyFonts;
	private final Supplier<Noise>[] lazyNoises;

	public Styles(int colors, int fonts, int noises) {
		super();
		this.colors = new Color[colors];
		this.fonts = new Font[fonts][64];
		this.noises = new Noise[noises];
		this.lazyFonts = (Supplier<Font>[]) new Supplier<?>[fonts];
		this.lazyNoises = (Supplier<Noise>[]) new Supplier<?>[noises];
	}

	public void addFont(int type, String file) {
		lazyFonts[type] = () -> { return loadFont(file); };
	}

	public void addNoise(int type, int size, int depth, int seed) {
		lazyNoises[type] = () -> { return new Noise(size,depth/100f, seed); };
	}

	public void addColor(int type, int rgba) {
		colors[type] = new Color(rgba, true);
	}

	public void ready() {
		Thread loader = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < lazyFonts.length; i++) {
					Supplier<Font> supplier = lazyFonts[i];
					if (supplier != null) {
						fonts[i][0] = supplier.get();
					}
				}
				for (int i = 0; i < lazyNoises.length; i++) {
					Supplier<Noise> supplier = lazyNoises[i];
					if (supplier != null) {
						noises[i] = supplier.get();
					}
				}				
			}
		});
		loader.setDaemon(true);
		loader.start();		
	}

	public Color color(int type) {
		return type < 0 || type >= colors.length ? colors[0] : colors[type];
	}

	public Font font(int type, int size) {
		if (type < 0 || type >= fonts.length)
			type = 0;
		Font[] sizes = fonts[type];
		if (size >= sizes.length)
			return sizes[0].deriveFont(0, size);
		Font f = sizes[size];
		if (f == null) {
			f = sizes[0].deriveFont(0, size);
			sizes[size] = f;
		}
		return f;		
	}
	
	public Noise noise(int type) {
		if (type < 0 || type >= fonts.length)
			type = 0;
		Noise n = noises[type];
		if (n == null) {
			n = lazyNoises[type].get();
			noises[type] = n;
		}
		return n;
	}

	public static Font loadFont(String file) {
		try (InputStream is = new FileInputStream(new File(file))) {
			return Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
