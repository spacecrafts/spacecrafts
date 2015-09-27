package se.jbee.game.scs.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.List;

import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Obj;
import se.jbee.game.uni.gfx.Renderer;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.gfx.Styles;

/**
 * The first {@link Renderer} I do.
 */
public class Renderer1 implements Renderer, Gfx {

	// make horizontal scratch: 300, 1000, 1000, 80, 42, 0.2f (has been caused by using a rectangle that had another shape)
	// wood-like: 100, 2000, 200, 80, 42, 0.2f

	private Obj[] objects = new Obj[64];
	
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
	
	public void use(int type, Obj obj) {
		if (type > objects.length) {
			objects = Arrays.copyOf(objects, objects.length*2);
		}
		objects[type] = obj;
	}

	private void render(Graphics2D gfx, Styles styles, List<int[]> objects) {
		int i = 0;
		while (i < objects.size()) {
			int[] obj = objects.get(i);
			List<int[]> data = objects.subList(i, i+1+obj[1]);
			this.objects[obj[0]].draw(gfx, styles, data);
			i+= data.size();
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
