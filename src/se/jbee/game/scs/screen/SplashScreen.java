package se.jbee.game.scs.screen;

import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;
import java.awt.Rectangle;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Colors;
import se.jbee.game.scs.gfx.Fonts;
import se.jbee.game.scs.state.GameComponent;

public class SplashScreen implements Screen, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		Entity g1 = game.single(GAME);
		
		scene.place(background(0,0, screen.width, screen.height, 1));
		int diameter = 20;
		String title = "SPACECRAFTS";
		while (title.length()*5*diameter > screen.width) {
			diameter--;
		}
		scene.place(text((screen.width-(title.length()*5*diameter)+diameter)/2, screen.height/4-(5*diameter), Fonts.TYPE_CAPS, diameter, Colors.HIGHLIGHT_TEXT, 1));
		scene.place(codePoints(title));
		
		int w = screen.width/6;
		diameter = w/(4*5);
		w = 4*5*diameter-diameter;
		int h = diameter*5;
		int x0 = (screen.width-w)/2;
		int y0 = screen.height/2;
		
		// save
		Rectangle save = new Rectangle(x0,y0,w,h);
		scene.place(text(x0, y0, Fonts.TYPE_CAPS, diameter, Colors.NORMAL_TEXT, 1));
		scene.place(codePoints("SAVE"));
		scene.bind(save, text(x0, y0, Fonts.TYPE_CAPS, diameter, Colors.HIGHLIGHT_TEXT, 1), codePoints("SAVE"));
		Change[] saveChangeset = { new Change(g1.id(), SCREEN, Op.PUT, 1), new Change(g1.id(), RETURN_SCREEN, Op.PUT, 0) };
		scene.bindLeftClick(save, saveChangeset);
		scene.bindKey('s', saveChangeset);
		
		// exit
		y0 += 10*diameter;
		Rectangle exit = new Rectangle(x0,y0,w,h);
		scene.place(text(x0, y0, Fonts.TYPE_CAPS, diameter, Colors.NORMAL_TEXT, 1));
		scene.place(codePoints("EXIT"));
		scene.bind(exit, text(x0, y0, Fonts.TYPE_CAPS, diameter, Colors.HIGHLIGHT_TEXT, 1), codePoints("EXIT"));
		Change exitChange = new Change(g1.id(), ACTION, Op.PUT, 0);
		scene.bindLeftClick(exit, exitChange);
		scene.bindKey((char)27, exitChange);
	}

}
