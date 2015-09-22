package se.jbee.game.scs.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Renderer;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.gfx.Stage.KeyMapping;
import se.jbee.game.uni.gfx.Styles;

public class OnScreenKeyboad implements Renderer, Gfx {


	@Override
	public void render(Stage stage, Dimension screen, Styles styles, Graphics2D gfx) {

		//TODO not here but in a post processing to setting the stage, like a stage decoration (so that addition area mappings are only done once.
		int x = 20;
		for (KeyMapping m : new ArrayList<>(stage.onKeyPress)) {
			int w = 40;
			String text = KeyEvent.getKeyText(m.keyCode);
			int tw = gfx.getFontMetrics().stringWidth(text);
			while (tw > w-20) { w+=20; }
			gfx.setColor(Color.BLACK);
			gfx.fillRoundRect(x, screen.height-60, w, 40, 15,15);
			gfx.setColor(Color.LIGHT_GRAY);
			gfx.drawRoundRect(x, screen.height-60, w, 40, 15,15);
			gfx.setFont(styles.font(FONT_REGULAR, 24));
			gfx.drawString(text, x+12, screen.height-30);
			x+= w+5;
		}

		// TODO also as an decoration, rendered like extra display on top of the main monitor of a spacecraft (that the player is commanding)
		// this may look different depending on the chosen race
		gfx.setColor(styles.color(COLOR_TEXT_NORMAL));
		gfx.drawRoundRect(screen.width/3+screen.width/32, -screen.height/16-20, screen.width/3-screen.width/16, screen.width/16+20, 20, 20);

	}


}
