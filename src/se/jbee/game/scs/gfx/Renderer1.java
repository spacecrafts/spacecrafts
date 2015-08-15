package se.jbee.game.scs.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import se.jbee.game.common.gfx.Backdrop;
import se.jbee.game.common.gfx.Palette;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.process.Stage;
import se.jbee.game.scs.gfx.bg.Planet;
import se.jbee.game.scs.gfx.bg.Space;
import se.jbee.game.scs.gfx.bg.Star;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Gfx {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectange that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f
	
	private final Backdrop space = new Space();
	private final Backdrop star  = new Star();
	private final Backdrop planet = new Planet();
	
	private int frameDone = -1;

	@Override
	public void render(Stage scene, Dimension screen, Palette palette, Graphics2D gfx) {
		int frame = scene.frame();
		if (frame != frameDone) {
			frameDone = frame;
			long drawStart = System.currentTimeMillis();

			antialias(gfx);
			textAntialias(gfx);
			
			render(palette, gfx, scene.objects.get());
			render(palette, gfx, scene.accents());
			
			gfx.setFont(palette.font(FONT_REGULAR, 12));
			gfx.setColor(Color.WHITE);
			gfx.drawString(""+(System.currentTimeMillis() - drawStart), 20, 20);
		}
		
	}

	private void render(Palette palette, Graphics2D gfx, List<int[]> objects) {
		for (int i = 0; i < objects.size(); i++) {
			int[] obj = objects.get(i);
			switch (obj[0]) {
			case OBJ_BACKGROUND: background(palette, gfx, obj); break;
			case OBJ_TEXT:
				gfx.setColor(palette.color(obj[5]));
				if (obj[3] == FONT_DOTS) {
					SCSFont.draw(gfx, obj[1], obj[2], obj[4], objects.get(++i));
				} else {
					gfx.setFont(palette.font(obj[3], obj[4]));
					String str = "";
					int xt = obj[1];
					for (int j = 0; j < obj[6]; j++) {
						if (j > 0) {
							xt += gfx.getFontMetrics().stringWidth(str);
							xt += gfx.getFontMetrics().stringWidth(" ");
						}
						int[] text = objects.get(++i);
						str = new String(text, 0, text.length);
						gfx.drawString(str, xt, obj[2]);
					}
				}
				break;
			case OBJ_PLANET:
				planet.paint(palette, gfx, obj[1], obj[2], obj[3], obj[3], obj[4]);
				break;
			case OBJ_STAR_ARC:
				star.paint(palette, gfx, obj[1], obj[2], obj[3], obj[3], obj[4]);
				break;
			case OBJ_BORDER:
				gfx.setColor(new Color(0x8899FF));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			case OBJ_FOCUS_BOX:
				gfx.setColor(palette.color(COLOR_TEXT_HIGHLIGHT));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			}
		}
	}

	private void background(Palette palette, Graphics2D gfx, int[] obj) {
		switch (obj[5]) {
		default:
		case BG_BLACK:
			gfx.setColor(Color.black);
			gfx.fillRect(obj[1], obj[2], obj[3], obj[4]);
			break;
		case BG_SPACE:
			space.paint(palette, gfx, obj[1], obj[2], obj[3], obj[4]);
			break;
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
