package se.jbee.game.common.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * A utility to manage graphic related resources like colors and fonts.
 */
public final class Palette {

	private final Color[] colors;
	private final Font[][] fonts;
	private final BufferedImage[] bgs;
	private final Supplier<BufferedImage>[] layzBgs;
	
	public Palette(Color[] colors, Font[][] fonts, final Supplier<BufferedImage>[] lazyBgs) {
		super();
		this.colors = colors;
		this.fonts = fonts;
		this.bgs = new BufferedImage[lazyBgs.length];;
		this.layzBgs = lazyBgs;
		Thread loader = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < lazyBgs.length; i++) {
					bgs[i] = lazyBgs[i].get();
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
	
	public BufferedImage background(int type) {
		BufferedImage bg = bgs[type];
		if (bg == null) {
			bg = layzBgs[type].get();
			bgs[type] = bg;
		}
		return bg;
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
