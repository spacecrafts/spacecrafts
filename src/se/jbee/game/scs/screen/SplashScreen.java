package se.jbee.game.scs.screen;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.common.state.Change.put;
import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.star;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Rectangle;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
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
		Entity g1 = game.single(GAME);
		
		stage.enter(background(0,0, screen.width, screen.height, BG_SPACE));
		stage.enter(star(120, 120, 16, 0, 0));
		
		int diameter = 20;
		String title = "SPACECRAFTS";
		while (title.length()*5*diameter > screen.width) {
			diameter--;
		}
		stage.enter(text((screen.width-(title.length()*5*diameter)+diameter)/2, screen.height/4-(5*diameter), FONT_DOTS, diameter, COLOR_TEXT_SPECIAL, 1));
		stage.enter(codePoints(title));
		
		int w = Math.max(200, screen.width/8);
		diameter = w/(4*5);
		w = 4*5*diameter-diameter;
		int h = diameter*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/4+screen.height/8;

		// open
		Rectangle open = new Rectangle(x0,y0,w,h);
		stage.enter(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		stage.enter(OPEN);
		stage.in(open, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), OPEN);
		Change[] openChangeset = { new Change(g1.id(), SCREEN, Op.PUT, SCREEN_SETUP_GAME) };
		stage.onLeftClickIn(open, openChangeset);
		stage.onKey('o', openChangeset);
		
		// load
		y0 += 8*diameter;
		Rectangle load = new Rectangle(x0,y0,w,h);
		stage.enter(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		stage.enter(LOAD);
		stage.in(load, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), LOAD);
		Change[] loadChangeset = { put(g1.id(), SCREEN, SCREEN_LOAD_GAME) };
		stage.onLeftClickIn(load, loadChangeset);
		stage.onKey('l', loadChangeset);
		
		// save
		y0 += 8*diameter;
		Rectangle save = new Rectangle(x0,y0,w,h);
		stage.enter(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		stage.enter(SAVE);
		stage.in(save, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), SAVE);
		Change[] saveChangeset = { put(g1.id(), SCREEN, SCREEN_SAVE_GAME) };
		stage.onLeftClickIn(save, saveChangeset);
		stage.onKey('s', saveChangeset);
		
		// back
		if (g1.has(RETURN_SCREEN) && g1.num(RETURN_SCREEN) != SCREEN_MAIN) {
			y0 += 8*diameter;
			Rectangle back = new Rectangle(x0,y0,w,h);
			stage.enter(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1)); //TODO other color
			stage.enter(BACK);
			stage.in(back, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), BACK);
			Change backChange = put(g1.id(), SCREEN, g1.num(RETURN_SCREEN));
			stage.onLeftClickIn(back, backChange);
			stage.onKey(' ', backChange);			
		}
		
		// exit
		y0 += 8*diameter;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		stage.enter(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		stage.enter(EXIT);
		stage.in(exit, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), EXIT);
		Change exitChange = put(g1.id(), ACTION, ACTION_EXIT);
		stage.onLeftClickIn(exit, exitChange);
		stage.onKey(VK_ESCAPE, exitChange);
	}

}
