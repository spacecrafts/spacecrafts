package se.jbee.game.scs.gfx;

import static java.util.Arrays.copyOf;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.Renderer;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.Stage;

public class GfxRenderer implements Renderer, Gfx {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectangle that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f

	@Override
	public void render(Stage stage, Dimension screen, Resources styles, Graphics2D gfx) {
		long drawStart = System.currentTimeMillis();

		antialias(gfx);
		textAntialias(gfx);

		render(gfx, styles, stage.objects.get());
		render(gfx, styles, stage.highlights());

		gfx.setFont(styles.font(FONT_REGULAR, 12));
		gfx.setColor(Color.WHITE);
		gfx.drawString(String.format("  %03d  %dM", System.currentTimeMillis() - drawStart, Runtime.getRuntime().freeMemory()/(1024*1024)), 20, 20);
	}

	private static void render(Graphics2D gfx, Resources styles, List<GfxObj> objects) {
		for (GfxObj obj : objects) {
			obj.draw(gfx, styles);
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
