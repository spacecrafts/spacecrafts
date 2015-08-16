package se.jbee.game.scs.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.common.gfx.Backdrop;
import se.jbee.game.common.gfx.Palette;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.process.Stage;
import se.jbee.game.common.process.Stage.KeyMapping;
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
	private final Backdrop starClip  = Star.CLIP;
	private final Backdrop planet = Planet.CIRCLE;
	private final Backdrop planetClip = Planet.CLIP;
	
	private int frameDone = -1;

	@Override
	public void render(Stage stage, Dimension screen, Palette palette, Graphics2D gfx) {
		int frame = stage.frame();
		if (frame != frameDone) {
			frameDone = frame;
			long drawStart = System.currentTimeMillis();

			antialias(gfx);
			textAntialias(gfx);
			
			render(palette, gfx, stage.objects.get());
			render(palette, gfx, stage.accents());
			
			gfx.setFont(palette.font(FONT_REGULAR, 12));
			gfx.setColor(Color.WHITE);
			gfx.drawString(""+(System.currentTimeMillis() - drawStart), 20, 20);
		
			//TODO not here but in a post processing to setting the stage, like a stage decoration (so that addition area mappings are only done once.
			int x = 20;
			for (KeyMapping m : new ArrayList<>(stage.onKeyPress)) {
				gfx.setColor(Color.BLACK);
				gfx.fillRoundRect(x, screen.height-60, 40, 40, 15,15);
				gfx.setColor(Color.LIGHT_GRAY);
				gfx.drawRoundRect(x, screen.height-60, 40, 40, 15,15);
				gfx.setFont(palette.font(FONT_REGULAR, 24));
				gfx.drawString(KeyEvent.getKeyText(m.keyCode), x+12, screen.height-30);
				x+= 45;
			}
			
			// TODO also as an decoration, rendered like extra display on top of the main monitor of a spacecraft (that the player is commanding)
			// this may look different depending on the chosen race 
			gfx.setColor(palette.color(COLOR_TEXT_NORMAL));
			gfx.drawRoundRect(screen.width/3+screen.width/32, -screen.height/16-20, screen.width/3-screen.width/16, screen.width/16+20, 20, 20);
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
				planet.paint(palette, gfx, obj[1], obj[2], obj[3], obj[3], obj[4]);	break;
			case OBJ_PLANET_CLIP:
				planetClip.paint(palette, gfx, obj[1], obj[2], obj[3], obj[3], obj[4]); break;
			case OBJ_STAR_CLIP:
				starClip.paint(palette, gfx, obj[1], obj[2], obj[3], obj[3], obj[4]); break;
			case OBJ_BORDER:
				gfx.setColor(new Color(0x8899FF));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			case OBJ_FOCUS_BOX:
				gfx.setColor(palette.color(COLOR_TEXT_HIGHLIGHT));
				gfx.drawRect(obj[1], obj[2], obj[3], obj[4]);
				break;
			case OBJ_SLOT:
				gfx.setColor(palette.color(COLOR_SLOT_BORDER));
				gfx.drawOval(obj[1], obj[2], obj[3], obj[3]);
				break;
			case OBJ_RESOURCE:
				gfx.setColor(palette.color(COLOR_TEXT_NORMAL));
				gfx.drawOval(obj[1], obj[2], obj[3], obj[3]);
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
