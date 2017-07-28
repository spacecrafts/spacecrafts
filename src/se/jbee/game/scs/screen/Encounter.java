package se.jbee.game.scs.screen;

import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.state.GameComponent;

/**
 * Screen shown until the control is transfered to an actual encounter interface
 * where battles are fought.
 */
@ScreenNo(GameScreen.SCREEN_ENCOUNTER)
public class Encounter implements Screen, GameComponent, Gfx {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK, 0L));
		stage.atFront(text(textKey('G', 'i', 'e'), 0, 0, FontStyle.DOTS, dotDiameter(screen), Hue.TEXT_HIGHLIGHT, Align.EYE, screen.width, screen.height));
		stage.disableInputs();		
	}

}
