package se.jbee.game.any.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import se.jbee.game.any.gfx.texture.Colouring;

/**
 * A utility to manage graphic related resources like colors, fonts and texts.
 */
public final class Resources {

	public final Texts texts;
	private final Color[] colors;
	private final Font[] fonts;
	private final Font[][] derivedFonts;
	private final Noise[] noises;
	private final BufferedImage[] images;

	private final Resource<Font>[] lazyFonts;
	private final Resource<Noise>[] lazyNoises;
	private final Resource<BufferedImage>[] lazyImages;

	@SuppressWarnings("unchecked")
	public Resources(Class<? extends Enum<?>> colors, Class<? extends Enum<?>> fonts, int noises, int images) {
		super();
		this.texts = new Texts();
		this.colors = new Color[colors.getEnumConstants().length];
		int fontCount = fonts.getEnumConstants().length;
		this.fonts = new Font[fontCount];
		this.derivedFonts = new Font[fontCount][64];
		this.noises = new Noise[noises];
		this.images = new BufferedImage[images];
		this.lazyFonts = new Resource[fontCount];
		this.lazyNoises = new Resource[noises];
		this.lazyImages = new Resource[images];
	}

	public void addFont(Enum<?> type, String file) {
		lazyFonts[type.ordinal()] = (Style) -> { return loadFont(file); };
	}

	public void addNoise(int type, int size, int depth, int seed) {
		lazyNoises[type] = (Style) -> { return new Noise(size,depth/100f, seed); };
	}

	public void addTexture(int type, Resource<BufferedImage> image) {
		lazyImages[type] = image;
	}

	public void addColor(Enum<?> type, int argb) {
		colors[type.ordinal()] = new Color(argb, true);
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
						long s = System.currentTimeMillis();
						instances[i] = resource.yield(Resources.this);
						System.out.println(System.currentTimeMillis()-s+" ms ["+instances.getClass().getSimpleName()+"]");
					}
				}
			}
		});
		loader.setDaemon(true);
		loader.start();
	}

	public Color color(Enum<?> c) {
		int type = c.ordinal();
		return type < 0 || type >= colors.length ? colors[0] : colors[type];
	}

	public Font font(Enum<?> style, int size) {
		int type = style.ordinal();
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

	private static Font derive(Font base, int size) {
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
