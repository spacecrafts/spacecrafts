package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.State;

@ScreenNo(GameScreen.SCREEN_ERROR)
public class ErrorJournal implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		//TODO show error journal
		
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		stage.inFront(text(x0+20, y0+h/2, FONT_DOTS, dotDiameter(screen), COLOR_WEAPON, 1));
		stage.inFront(codePoints("ERROR"));
		
		stage.onKey(VK_ESCAPE, put(game.single(GAME).id(), ACTION, ACTION_EXIT));
	}

}
