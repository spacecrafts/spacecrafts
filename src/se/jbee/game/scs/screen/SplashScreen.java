package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;
import static se.jbee.game.uni.state.Change.put;
import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Rectangle;

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Change;
import se.jbee.game.uni.state.Change.Op;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

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
		stage.inFront(text((screen.width-(title.length()*5*dotDia)+dotDia)/2, screen.height/4-(5*dotDia), FONT_DOTS, dotDia, COLOR_TEXT_SPECIAL, 1));
		stage.inFront(codePoints(title));

		dotDia = dotDiameter(screen);
		int w = 4*5*dotDia-dotDia;
		int h = dotDia*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/4+screen.height/8;
		int gID = gamE.id();

		// open
		Rectangle open = new Rectangle(x0,y0,w,h);
		stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
		stage.inFront(OPEN);
		stage.in(open, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1), OPEN);
		Change[] openCs = { new Change(gID, SCREEN, Op.PUT, SCREEN_SETUP_GAME) };
		stage.onLeftClickIn(open, openCs);
		stage.onKey('o', openCs);

		// load
		y0 += 8*dotDia;
		Rectangle load = new Rectangle(x0,y0,w,h);
		stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
		stage.inFront(LOAD);
		stage.in(load, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1), LOAD);
		Change[] loadCs = { put(gID, SCREEN, SCREEN_LOAD_GAME) };
		stage.onLeftClickIn(load, loadCs);
		stage.onKey('l', loadCs);

		// save
		if (gamE.num(TURN) > 0) {
			y0 += 8*dotDia;
			Rectangle save = new Rectangle(x0,y0,w,h);
			stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
			stage.inFront(SAVE);
			stage.in(save, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1), SAVE);
			Change[] saveCs = { put(gID, SCREEN, SCREEN_SAVING_GAME) };
			stage.onLeftClickIn(save, saveCs);
			stage.onKey('s', saveCs);
		}

		// back
		if (gamE.has(RETURN_SCREEN) && gamE.num(RETURN_SCREEN) != SCREEN_MAIN) {
			y0 += 8*dotDia;
			Rectangle back = new Rectangle(x0,y0,w,h);
			stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1)); //TODO other color
			stage.inFront(BACK);
			stage.in(back, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1), BACK);
			Change backChange = put(gID, SCREEN, gamE.num(RETURN_SCREEN));
			stage.onLeftClickIn(back, backChange);
			stage.onKey(' ', backChange);
		}

		// exit
		y0 += 8*dotDia;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		stage.inFront(text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL, 1));
		stage.inFront(EXIT);
		stage.in(exit, text(x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT, 1), EXIT);
		Change exitCs = put(gID, ACTION, ACTION_EXIT);
		stage.onLeftClickIn(exit, exitCs);
		stage.onKey(VK_ESCAPE, exitCs);
	}

}
