package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.knob;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import static se.jbee.game.uni.state.Change.append;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Change.replace;
import static se.jbee.game.uni.state.Change.take;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * Used in turn zero to setup overall game properties.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_GAME)
public class SetupGame implements Screen, GameComponent, Gfx, GameScreen {

	private static final int[] NEXT = codePoints("NEXT");

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {

		stage.inFront(background(0, 0, screen.width, screen.height, BG_BLACK));

		//TODO create a game-name generator: typically a 2 part name sounding somewhat latin-english (stella odis, luna expos, ... edit)
		
		// # players
		// # AI
		// # stars in a cluster
		// # clusters in galaxy
		// (things about events, independent parties, etc.)

		// new entities should only be created by the game
		// especially as entities cannot be removed the player should first be created at the end of the zero'th turn.


		int x0 = screen.width/2-200;
		int y0 = screen.height/3;
		
		nameField(game, screen, stage, x0, y0);
		
		upDownSlider(stage, game, x0, y0, "Players", SETUP_NUMBER_OF_PLAYERS);
		y0 += 60;
		upDownSlider(stage, game, x0, y0, "AIs", SETUP_NUMBER_OF_AIS);
		y0 += 60;
		upDownSlider(stage, game, x0, y0, "Galaxy", SETUP_GALAXY_SIZE, "none", "small", "medium", "large");
		y0 += 100;
		nextButton(game, screen, stage, x0, y0);
	}

	private void nameField(State game, Dimension screen, Stage stage, int x0, int y0) {
		Entity gamE = game.single(GAME);
		int gid = gamE.id();
		int[] name = gamE.list(NAME);
		if (name.length < 12) {
			for (char c = 'a'; c <= 'z'; c++) {
				stage.onKey(c, append(gid, NAME, c));
			}
		}
		if (name.length > 0) {
			stage.onKey('\b', take(gid, NAME, name.length-1));
		}
		int fontSize = screen.height/16;
		stage.inFront(text(x0, y0-fontSize, FONT_LIGHT, fontSize, COLOR_TEXT_HIGHLIGHT, 1));
		stage.inFront(name);
	}

	private void nextButton(State game, Dimension screen, Stage stage, int x0, int y0) {
		Entity gamE = game.single(GAME);
		if (gamE.list(NAME).length < 1)
			return;
		stage.onKey('\n', put(gamE.id(), ACTION, ACTION_SETUP));

		int dotDia = dotDiameter(screen);
		x0 = x0+400-(dotDia*19);
		stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
		stage.inFront(NEXT);
		Rectangle nextArea = new Rectangle(x0,y0,dotDia*19,dotDia*5);
		stage.in(nextArea, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1),NEXT);
		stage.onLeftClickIn(nextArea,
				put(gamE.id(), ACTION, ACTION_SETUP));
	}

	private void upDownSlider(Stage stage, State game, int x0, int y0, String text, int setupIndex, String...names) {
		Entity gamE = game.single(GAME);
		int[] setup = gamE.list(SETUP);
		stage.inFront(text(x0, y0+40, FONT_THIN, 48, COLOR_TEXT_NORMAL, 1));
		stage.inFront(codePoints(text));
		stage.inFront(knob(x0+200, y0, 50, COLOR_TEXT_NORMAL, COLOR_BLACK, 1));
		stage.inFront(codePoints("<"));
		stage.inFront(knob(x0+350, y0, 50, COLOR_TEXT_NORMAL, COLOR_BLACK, 1));
		stage.inFront(codePoints(">"));
		int val = setup[setupIndex];
		if (names.length == 0) {
			stage.inFront(text(x0+290, y0+40, FONT_LIGHT, 48, COLOR_TEXT_HIGHLIGHT, 1));
			stage.inFront(codePoints(String.valueOf(val)));
		} else {
			stage.inFront(text(x0+270, y0+40, FONT_LIGHT, 24, COLOR_TEXT_HIGHLIGHT, 1));
			stage.inFront(codePoints(names[val]));
		}
		if (val > 1) {
			stage.onLeftClickIn(new Rectangle(x0+200, y0, 50,50), replace(gamE.id(), SETUP, setupIndex, val-1));
		}
		if (names.length == 0 || val < names.length-1) {
			stage.onLeftClickIn(new Rectangle(x0+350, y0, 50,50), replace(gamE.id(), SETUP, setupIndex, val+1));
		}
	}

}
