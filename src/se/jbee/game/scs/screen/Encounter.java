package se.jbee.game.scs.screen;

import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_ENCOUNTER)
public class Encounter implements Screen, GameComponent, Gfx {

	private static final int[] ENCOUNTER = codePoints("ENCOUNTER...");
	
	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));
		
		int dotDia = dotDiameter(screen);
		int x0 = (screen.width-(dotDia*ENCOUNTER.length*5-1))/2;
		int y0 = (screen.height-(dotDia*5)) /2;
		stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		stage.inFront(ENCOUNTER);
		
		stage.disableInputs();		
	}

}
