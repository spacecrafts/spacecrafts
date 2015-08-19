package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Change.put;
import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.View.dotDiameter;

import java.awt.Rectangle;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Objects;
import se.jbee.game.scs.state.GameComponent;

/**
 * Is used in turn zero to setup a human players properties.
 * 
 * Already in turn zero the active human player switches between the player
 * created. Each of them use their turn to chose their race abilities.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_PLAYER)
public class SetupPlayer implements Screen, Gfx, GameComponent {

	private static final int[] NEXT = codePoints("NEXT");
	
	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		// expects the screen entity to point to the player to setup
		
		Entity gamE = game.single(GAME);
		Entity player = game.entity(gamE.num(SCREEN_ENTITY));
		
		stage.enter(background(0, 0, screen.width, screen.height, BG_BLACK));
		
		stage.enter(Objects.text(100, 100, FONT_THIN, 48, COLOR_TEXT_NORMAL, 2));
		stage.enter(codePoints("Player"));
		stage.enter(codePoints(String.valueOf(player.num(NO))));
		
		// next
		int dotDia = dotDiameter(screen);
		int y0 = screen.height/2;
		int x0 = (screen.width-(dotDia*19))/2;
		stage.enter(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
		stage.enter(NEXT);
		Rectangle nextArea = new Rectangle(x0,y0,dotDia*19,dotDia*5);
		stage.in(nextArea, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1),NEXT);
		stage.onLeftClickIn(nextArea, 
				put(player.id(), TURN, 0),
				put(gamE.id(), ACTION, ACTION_STEP));
	}

}
