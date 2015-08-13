package se.jbee.game.scs.gfx;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public final class Fonts {

	private static final Font ROBOTO_LIGHT = loadFont("font/Roboto-Light.ttf");
	private static final Font ROBOTO = loadFont("font/Roboto-Regular.ttf");
	
	private final static Font[] ROBOTO_LIGHT_X = new Font[64];
	private final static Font[] ROBOTO_X = new Font[64];
	
	public static Font fromType(int type, int size) {
		switch (type) {
		default:
		case Gfx.FONT_REGULAR : return regular(size);
		case Gfx.FONT_LIGHT   : return light(size);
		}
	}
	
	public static Font regular(int size) {
		return derive(size, ROBOTO, ROBOTO_X);
	}
	
	public static Font light(int size) {
		return derive(size, ROBOTO_LIGHT, ROBOTO_LIGHT_X);
	}
	
	private static Font derive(int size, Font base, Font[] cache) {
		if (size >= cache.length) {
			return base.deriveFont(0, size);
		}
		Font f = cache[size];
		if (f == null) {
			f = base.deriveFont(0, size);
			cache[size] = f;
		}
		return f;
	}
	
	private static Font loadFont(String file) {
		try (InputStream is = new FileInputStream(new File(file))) {
			return Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
