package se.jbee.game.scs.gfx;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import se.jbee.game.scs.gfx.art.Background;
import se.jbee.game.scs.gfx.art.DotFont5x4;
import se.jbee.game.scs.gfx.art.Planet;
import se.jbee.game.scs.gfx.art.Star;
import se.jbee.game.uni.gfx.Artwork;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Renderer;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.gfx.Styles;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Gfx {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectangle that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f

	private final Artwork bg = new Background();
	private final Artwork starClip  = Star.CLIP;
	private final Artwork star = Star.CIRCLE;
	private final Artwork planet = Planet.CIRCLE;
	private final Artwork planetClip = Planet.CLIP;

	@Override
	public void render(Stage stage, Dimension screen, Styles styles, Graphics2D gfx) {
		long drawStart = System.currentTimeMillis();

		antialias(gfx);
		textAntialias(gfx);

		render(gfx, styles, stage.objects.get());
		render(gfx, styles, stage.accents());

		gfx.setFont(styles.font(FONT_REGULAR, 12));
		gfx.setColor(Color.WHITE);
		gfx.drawString(String.format("%03d  %dM", System.currentTimeMillis() - drawStart, Runtime.getRuntime().freeMemory()/(1024*1024)), 20, 20);
	}

	private void render(Graphics2D gfx, Styles styles, List<int[]> objects) {
		for (int i = 0; i < objects.size(); i++) {
			int[] obj = objects.get(i);
			switch (obj[0]) {
			case OBJ_TEXT         : text(gfx, styles, obj[1], obj[2], obj[3], obj[4], obj[5], objects.subList(i+1, i+1+obj[6])); i += obj[6]; break;
			case OBJ_BACKGROUND   : bg.paint(gfx, styles, obj[1], obj[2], obj[3], obj[4], obj[5]); break;
			case OBJ_PLANET       :	planet.paint(gfx, styles, obj[1], obj[2], obj[3], obj[3], obj[4]);	break;
			case OBJ_PLANET_CLIP  : planetClip.paint(gfx, styles, obj[1], obj[2], obj[3], obj[3], obj[4]); break;
			case OBJ_STAR_CLIP    :	starClip.paint(gfx, styles, obj[1], obj[2], obj[3], obj[3], obj[4]); break;
			case OBJ_STAR         : star.paint(gfx, styles, obj[1], obj[2], obj[3], obj[3], obj[4]); break;
			case OBJ_BORDER       :	gfx.setColor(new Color(0x8899FF)); gfx.drawRect(obj[1], obj[2], obj[3], obj[4]); break;
			case OBJ_FOCUS_BOX    : gfx.setColor(styles.color(COLOR_TEXT_HIGHLIGHT)); gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);	break;
			case OBJ_ICON         : gfx.setColor(styles.color(obj[5]));	Icon.draw(gfx, obj[1], obj[2], obj[3], obj[4]); break;
			case OBJ_BUTTON_LESS  : gfx.setColor(styles.color(COLOR_TEXT_NORMAL)); gfx.fillOval(obj[1], obj[2], obj[3], obj[3]); break;
			case OBJ_BUTTON_MORE  : gfx.setColor(styles.color(COLOR_TEXT_NORMAL)); gfx.fillOval(obj[1], obj[2], obj[3], obj[3]); break;
			case OBJ_PIEINFO      : pieinfo(gfx, styles, obj);
			}
		}
	}

	private void pieinfo(Graphics2D gfx, Styles styles, int[] obj) {
		gfx.setColor(styles.color(obj[4]));
		int x = obj[1];
		int y = obj[2];
		int d = obj[3];
		gfx.fillOval(x, y, d, d);
		gfx.setColor(styles.color(obj[5]));
		int w = 0;
		int size = d/2;
		String text = new String(obj, 6, obj.length-6);
		do {
			gfx.setFont(styles.font(FONT_REGULAR, size));
			FontMetrics fm = gfx.getFontMetrics();
			w = fm.stringWidth(text);
			size -= 2;
		} while (w > (d-d/8));
		FontMetrics fm = gfx.getFontMetrics();
		y += (d-fm.getHeight())/2 + fm.getHeight() - fm.getDescent();
		x += (d-w)/2;
		gfx.drawString(text, x, y);
	}

	private static void text(Graphics2D gfx, Styles styles, int x, int y, int font, int size, int color, List<int[]> words ) {
		gfx.setColor(styles.color(color));
		if (font == FONT_DOTS) {
			DotFont5x4.draw(gfx, x, y, size, words.get(0));
		} else {
			gfx.setFont(styles.font(font, size));
			String str = "";
			int xt = x;
			for (int j = 0; j < words.size(); j++) {
				if (j > 0) {
					xt += gfx.getFontMetrics().stringWidth(str);
					xt += gfx.getFontMetrics().stringWidth(" ");
				}
				int[] text = words.get(j);
				str = new String(text, 0, text.length);
				gfx.drawString(str, xt, y);
			}
		}
	}

	public static void textAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	public static void noTextAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	public static void antialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	public static void noAntialias(Graphics2D gfx) {
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}

}
