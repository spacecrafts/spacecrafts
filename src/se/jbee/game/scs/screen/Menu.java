package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.gfx.Texts.textKey;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.text;
import static se.jbee.game.scs.screen.Viewport.dotDiameter;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Change.Op;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_MENU)
public class Menu implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		Entity gamE = game.root();

		stage.atFront(background(0,0, screen.width, screen.height, BG_SPACE, 42L));

		int dotDia = 20;
		String title = "SPACECRAFTS";
		while (title.length()*5*dotDia > screen.width) {
			dotDia--;
		}
		stage.atFront(text(textKey('G', 'm', 'T'), (screen.width-(title.length()*5*dotDia)+dotDia)/2, screen.height/4-(5*dotDia), FONT_DOTS, dotDia, COLOR_TEXT_SPECIAL));

		dotDia = dotDiameter(screen);
		int w = 4*5*dotDia-dotDia;
		int h = dotDia*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/4+screen.height/8;
		int gID = gamE.id();

		// open
		Rectangle open = new Rectangle(x0,y0,w,h);
		stage.atFront(text(textKey('G', 'm', 'O'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.in(open, text(textKey('G', 'm', 'O'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		Change[] openCs = { new Change(gID, SCREEN, Op.SET, SCREEN_SETUP_GAME) };
		stage.onLeftClickIn(open, openCs);
		stage.onKey('o', openCs);

		// load
		y0 += 8*dotDia;
		Rectangle load = new Rectangle(x0,y0,w,h);
		stage.atFront(text(textKey('G', 'm', 'L'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.in(load, text(textKey('G', 'm', 'L'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		Change[] loadCs = { set(gID, SCREEN, SCREEN_LOAD_GAME) };
		stage.onLeftClickIn(load, loadCs);
		stage.onKey('l', loadCs);

		// save
		if (gamE.num(TURN) > 0) {
			y0 += 8*dotDia;
			Rectangle save = new Rectangle(x0,y0,w,h);
			stage.atFront(text(textKey('G', 'm', 'S'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
			stage.in(save, text(textKey('G', 'm', 'S'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
			Change[] saveCs = { set(gID, SCREEN, SCREEN_SAVING_GAME) };
			stage.onLeftClickIn(save, saveCs);
			stage.onKey('s', saveCs);
		}

		// back (link)
		if (gamE.has(RETURN_SCREEN) && gamE.num(RETURN_SCREEN) != SCREEN_MENU) {
			y0 += 8*dotDia;
			Rectangle back = new Rectangle(x0,y0,w,h);
			stage.atFront(text(textKey('G', 'm', 'B'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL)); //TODO other color
			stage.in(back, text(textKey('G', 'm', 'B'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
			Change backChange = set(gID, SCREEN, gamE.num(RETURN_SCREEN));
			stage.onLeftClickIn(back, backChange);
			stage.onKey(' ', backChange);
		}

		// exit
		y0 += 8*dotDia;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		stage.atFront(text(textKey('G', 'm', 'E'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_NORMAL));
		stage.in(exit, text(textKey('G', 'm', 'E'), x0, y0, FONT_DOTS, dotDia, COLOR_TEXT_HIGHLIGHT));
		Change exitCs = set(gID, ACTIONS, ACTION_STOP_AI, ACTION_AUTOSAVE, ACTION_EXIT);
		stage.onLeftClickIn(exit, exitCs);
		stage.onKey(VK_ESCAPE, exitCs);
	}

}
