package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.state.Change.put;
import static se.jbee.game.any.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.any.state.Change.Op;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_MAIN)
public class SplashScreen implements Screen, GameComponent, Gfx, GameScreen {

	private static final int[]
			OPEN = codePoints("OPEN"),
			BACK = codePoints("BACK"),
			EXIT = codePoints("EXIT"),
			SAVE = codePoints("SAVE"),
			LOAD = codePoints("LOAD");

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);

		stage.inFront(background(0,0, screen.width, screen.height, BG_SPACE, 42, 42));

		int dotDia = 20;
		String title = "SPACECRAFTS";
		while (title.length()*5*dotDia > screen.width) {
			dotDia--;
		}
		stage.inFront(text(1, (screen.width-(title.length()*5*dotDia)+dotDia)/2, screen.height/4-(5*dotDia), FONT_DOTS, dotDia, COLOR_TEXT_SPECIAL));
		stage.inFront(codePoints(title));

		dotDia = dotDiameter(screen);
		int w = 4*5*dotDia-dotDia;
		int h = dotDia*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/4+screen.height/8;
		int gID = gamE.id();

		// open
		Rectangle open = new Rectangle(x0,y0,w,h);
		stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.inFront(OPEN);
		stage.in(open, text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT), OPEN);
		Change[] openCs = { new Change(gID, SCREEN, Op.PUT, SCREEN_SETUP_GAME) };
		stage.onLeftClickIn(open, openCs);
		stage.onKey('o', openCs);

		// load
		y0 += 8*dotDia;
		Rectangle load = new Rectangle(x0,y0,w,h);
		stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.inFront(LOAD);
		stage.in(load, text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT), LOAD);
		Change[] loadCs = { put(gID, SCREEN, SCREEN_LOAD_GAME) };
		stage.onLeftClickIn(load, loadCs);
		stage.onKey('l', loadCs);

		// save
		if (gamE.num(TURN) > 0) {
			y0 += 8*dotDia;
			Rectangle save = new Rectangle(x0,y0,w,h);
			stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
			stage.inFront(SAVE);
			stage.in(save, text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT), SAVE);
			Change[] saveCs = { put(gID, SCREEN, SCREEN_SAVING_GAME) };
			stage.onLeftClickIn(save, saveCs);
			stage.onKey('s', saveCs);
		}

		// back
		if (gamE.has(RETURN_SCREEN) && gamE.num(RETURN_SCREEN) != SCREEN_MAIN) {
			y0 += 8*dotDia;
			Rectangle back = new Rectangle(x0,y0,w,h);
			stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL)); //TODO other color
			stage.inFront(BACK);
			stage.in(back, text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT), BACK);
			Change backChange = put(gID, SCREEN, gamE.num(RETURN_SCREEN));
			stage.onLeftClickIn(back, backChange);
			stage.onKey(' ', backChange);
		}

		// exit
		y0 += 8*dotDia;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		stage.inFront(text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.inFront(EXIT);
		stage.in(exit, text(1, x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT), EXIT);
		Change exitCs = put(gID, ACTION, ACTION_EXIT);
		stage.onLeftClickIn(exit, exitCs);
		stage.onKey(VK_ESCAPE, exitCs);
	}

}
