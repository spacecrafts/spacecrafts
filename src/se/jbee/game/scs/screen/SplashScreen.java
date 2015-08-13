package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;
import java.awt.Rectangle;

import se.jbee.game.common.process.Scene;
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
			BACK = codePoints("BACK"),
			EXIT = codePoints("EXIT"),
			SAVE = codePoints("SAVE"),
			LOAD = codePoints("LOAD");

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		Entity g1 = game.single(GAME);
		
		scene.place(background(0,0, screen.width, screen.height, 1));
		int diameter = 20;
		String title = "SPACECRAFTS";
		while (title.length()*5*diameter > screen.width) {
			diameter--;
		}
		scene.place(text((screen.width-(title.length()*5*diameter)+diameter)/2, screen.height/3-(5*diameter), FONT_DOTS, diameter, 3, 1));
		scene.place(codePoints(title));
		
		int w = Math.max(200, screen.width/8);
		diameter = w/(4*5);
		w = 4*5*diameter-diameter;
		int h = diameter*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/3+screen.height/8;

		// load
		Rectangle load = new Rectangle(x0,y0,w,h);
		scene.place(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		scene.place(LOAD);
		scene.bind(load, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), LOAD);
		Change[] loadChangeset = { new Change(g1.id(), SCREEN, Op.PUT, SCREEN_SOLAR_SYSTEM), new Change(g1.id(), RETURN_SCREEN, Op.PUT, SCREEN_MAIN) };
		scene.bindLeftClick(load, loadChangeset);
		scene.bindKey('l', loadChangeset);
		
		// save
		y0 += 10*diameter;
		Rectangle save = new Rectangle(x0,y0,w,h);
		scene.place(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		scene.place(SAVE);
		scene.bind(save, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), SAVE);
		Change[] saveChangeset = { new Change(g1.id(), SCREEN, Op.PUT, SCREEN_SAVE_GAME), new Change(g1.id(), RETURN_SCREEN, Op.PUT, SCREEN_MAIN) };
		scene.bindLeftClick(save, saveChangeset);
		scene.bindKey('s', saveChangeset);
		
		// exit
		y0 += 10*diameter;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		scene.place(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1));
		scene.place(EXIT);
		scene.bind(exit, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), EXIT);
		Change exitChange = new Change(g1.id(), ACTION, Op.PUT, ACTION_EXIT);
		scene.bindLeftClick(exit, exitChange);
		scene.bindKey((char)27, exitChange);
		
		// back
		if (g1.has(RETURN_SCREEN) && g1.num(RETURN_SCREEN) != SCREEN_MAIN) {
			y0 += 10*diameter;
			Rectangle back = new Rectangle(x0,y0,w,h);
			scene.place(text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_NORMAL, 1)); //TODO other color
			scene.place(BACK);
			scene.bind(back, text(x0, y0, FONT_DOTS, diameter, COLOR_TEXT_HIGHLIGHT, 1), BACK);
			Change backChange = new Change(g1.id(), SCREEN, Op.PUT, g1.num(RETURN_SCREEN));
			scene.bindLeftClick(back, backChange);
			scene.bindKey(' ', backChange);			
		}
	}

}
