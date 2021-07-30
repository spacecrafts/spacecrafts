package se.jbee.game.scs.screen;

import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.append;
import static se.jbee.game.any.state.Change.replace;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.any.state.Change.take;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Draw.background;
import static se.jbee.game.scs.gfx.Draw.button;
import static se.jbee.game.scs.gfx.Draw.label;
import static se.jbee.game.scs.gfx.Draw.text;
import static se.jbee.game.scs.gfx.Hue.BLACK;
import static se.jbee.game.scs.gfx.Hue.TEXT_HIGHLIGHT;
import static se.jbee.game.scs.gfx.Hue.TEXT_NORMAL;
import static se.jbee.game.scs.gfx.Hue.TEXT_SPECIAL;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

/**
 * Used in turn zero to setup overall game properties.
 */
@ScreenNo(GameScreen.SCREEN_SETUP_GAME)
public class SetupGame implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {

		stage.atFront(background(0, 0, screen.width, screen.height, BG_BLACK, 0L));

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
		Entity gamE = game.root();
		int gid = gamE.id();
		int[] name = gamE.list(NAME);
		if (gamE.num(TURN) == 0) {
			if (name.length < 12) {
				for (char c = 'a'; c <= 'z'; c++) {
					stage.onKey(c, append(State.ROOT, NAME, c));
				}
				stage.onKey(' ', append(State.ROOT, NAME, ' '));
			}
			if (name.length > 0) {
				stage.onKey('\b', take(State.ROOT, NAME, name.length-1));
			}
		}
		int fontSize = screen.height/16;
		stage.atFront(label(x0, y0-fontSize-50, FontStyle.LIGHT, fontSize, TEXT_SPECIAL, name));
	}

	private void nextButton(State game, Dimension screen, Stage stage, int x0, int y0) {
		Entity gamE = game.root();
		if (gamE.list(NAME).length < 1)
			return;

		int dotDia = dotDiameter(screen);
		x0 = x0+400-(dotDia*19);
		stage.atFront(text(textKey('G', 's', 'n'), x0, y0, FontStyle.DOTS, dotDia, TEXT_NORMAL));
		Rectangle nextArea = new Rectangle(x0,y0,dotDia*19,dotDia*5);
		stage.in(nextArea, text(textKey('G', 's', 'n'), x0, y0, FontStyle.DOTS, dotDia, TEXT_HIGHLIGHT));
		stage.onLeftClickIn(nextArea,
				set(State.ROOT, ACTIONS, ACTION_SETUP));
		stage.onKey('\n', 
				set(State.ROOT, ACTIONS, ACTION_SETUP));
	}

	private void upDownSlider(Stage stage, State game, int x0, int y0, String text, int setupIndex, String...names) {
		Entity gamE = game.root();
		int[] setup = gamE.list(SETUP);
		stage.atFront(label(x0, y0, FontStyle.THIN, 48, TEXT_NORMAL, codePoints(text)));
		stage.atFront(button(x0+200, y0, 50, TEXT_NORMAL, BLACK));
		//FIXME stage.atFront(codePoints("<"));
		stage.atFront(button(x0+350, y0, 50, TEXT_NORMAL, BLACK));
		//FIXME  stage.atFront(codePoints(">"));
		int val = setup[setupIndex];
		int size = names.length == 0 ? 36 : 24;
		int[] txt = names.length == 0
			? codePoints(String.valueOf(val)) 
			: codePoints(names[val]);
		stage.atFront(label(x0+250, y0, FontStyle.LIGHT, size, TEXT_HIGHLIGHT, Align.EYE,x0+350,y0+50, txt));
		if (val > 1) {
			stage.onLeftClickIn(new Rectangle(x0+200, y0, 50,50), replace(State.ROOT, SETUP, setupIndex, val-1));
		}
		if (names.length == 0 || val < names.length-1) {
			stage.onLeftClickIn(new Rectangle(x0+350, y0, 50,50), replace(State.ROOT, SETUP, setupIndex, val+1));
		}
	}

}
