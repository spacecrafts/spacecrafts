package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ENTER;
import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.fixtext;
import static se.jbee.game.scs.gfx.GfxObjs.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.state.GameComponent;

/**
 * Is used in turn zero to setup a human players properties.
 *
 * Already in turn zero the active human player switches between the player
 * created. Each of them use their turn to chose their race abilities.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_PLAYER)
public class SetupPlayer implements Screen, Gfx, GameComponent {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		// expects the screen entity to point to the player to setup

		Entity gamE = game.single(GAME);
		Entity player = Turn.currentPlayer(game);

		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK));
		stage.atFront(fixtext(100, 100, FONT_THIN, 48, COLOR_TEXT_NORMAL, codePoints("Player "+String.valueOf(player.num(NO)))));

		// next
		int dotDia = dotDiameter(screen);
		int y0 = screen.height/2;
		int x0 = (screen.width-(dotDia*19))/2;
		stage.atFront(text(textKey('G', 's', 'n'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		Rectangle nextArea = new Rectangle(x0,y0,dotDia*19,dotDia*5);
		stage.in(nextArea, text(textKey('G', 's', 'n'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		Change[] nextCs = {
				set(player.id(), TURN, 0),
				set(gamE.id(), ACTION, ACTION_NEXT_TASK),
				set(gamE.id(), SCREEN, GameScreen.SCREEN_BLANK)
		};
		stage.onLeftClickIn(nextArea, nextCs);
		stage.onGlobalKey(VK_ENTER, nextCs);
	}

}
