package se.jbee.game.common.gfx;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * A utility to manage graphic related resources like colors and fonts.
 */
public final class Palette {

	private final Color[] colors;
	private final Font[][] fonts;
	
	public Palette(Color[] colors, Font[][] fonts) {
		super();
		this.colors = colors;
		this.fonts = fonts;
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
	
	public static Font loadFont(String file) {
		try (InputStream is = new FileInputStream(new File(file))) {
			return Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
