package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.scs.gfx.GfxObjs.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_ERROR)
public class ErrorJournal implements Screen, GameComponent, Gfx {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		//TODO show error journal
		
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		stage.atFront(text(textKey('G', 'j', 'T'), x0+20, y0+h/2, FONT_DOTS, dotDiameter(screen), COLOR_WEAPON));
		
		stage.onKey(VK_ESCAPE, set(State.ROOT, ACTIONS, ACTION_EXIT));
	}

}
