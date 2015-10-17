package se.jbee.game.any.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * A utility to manage graphic related resources like colors and fonts.
 */
public final class Styles {

	private final Color[] colors;
	private final Font[] fonts;
	private final Font[][] derivedFonts;
	private final Noise[] noises;
	private final BufferedImage[] images;

	private final Resource<Font>[] lazyFonts;
	private final Resource<Noise>[] lazyNoises;
	private final Resource<BufferedImage>[] lazyImages;

	@SuppressWarnings("unchecked")
	public Styles(int colors, int fonts, int noises, int images) {
		super();
		this.colors = new Color[colors];
		this.fonts = new Font[fonts];
		this.derivedFonts = new Font[fonts][64];
		this.noises = new Noise[noises];
		this.images = new BufferedImage[images];
		this.lazyFonts = new Resource[fonts];
		this.lazyNoises = new Resource[noises];
		this.lazyImages = new Resource[images];
	}

	public void addFont(int type, String file) {
		lazyFonts[type] = (Style) -> { return loadFont(file); };
	}

	public void addNoise(int type, int size, int depth, int seed) {
		lazyNoises[type] = (Style) -> { return new Noise(size,depth/100f, seed); };
	}

	public void addTexture(int type, Resource<BufferedImage> image) {
		lazyImages[type] = image;
	}

	public void addColor(int type, int argb) {
		colors[type] = new Color(argb, true);
	}

	public void ready() {
		Thread loader = new Thread(new Runnable() {

			@Override
			public void run() {
				yield(lazyFonts, fonts);
				yield(lazyNoises, noises);
				yield(lazyImages, images);
			}

			private <T> void yield(Resource<T>[] resources, T[] instances) {
				for (int i = 0; i < resources.length; i++) {
					Resource<T> resource = resources[i];
					if (resource != null) {
						instances[i] = resource.yield(Styles.this);
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
		if (type < 0 || type >= derivedFonts.length)
			type = 0;
		Font[] sizes = derivedFonts[type];
		if (size >= sizes.length)
			return derive(fonts[type], size);
		Font f = sizes[size];
		if (f == null) {
			Font base = fonts[type];
			f = derive(base, size);
			if (base != null) {
				sizes[size] = f;
			}
		}
		return f;
	}

	private Font derive(Font base, int size) {
		return base == null ? Font.getFont(Font.MONOSPACED) : base.deriveFont(0, size);
	}

	public Noise noise(int type) {
		return yield(type, lazyNoises, noises);
	}

	public BufferedImage texture(int type) {
		return yield(type, lazyImages, images);
	}

	private <T> T yield(int type, Resource<T>[] rs, T[] is) {
		if (type < 0 || type >= images.length)
			type = 0;
		T i = is[type];
		if (i == null) {
			i = rs[type].yield(this);
			is[type] = i;
		}
		return i;
	}

	public static BufferedImage texture(int w, int h, Noise noise, Colouring coloring) {
		BufferedImage img = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = new int[w * h];
		int k = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// the noiseAt results in -1.0 to 1.0; the below does shift it to 0.0 to 1.0
				pixels[k++] = coloring.rgba(min(1f, max(0f, 0.5f*(1f+(float)noise.noiseAt(x, y)))));
			}
		}
		img.getRaster().setDataElements(0,  0,  w, h, pixels);
		return img;
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
