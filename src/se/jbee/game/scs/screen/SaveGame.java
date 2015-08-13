package se.jbee.game.scs.screen;

import static java.util.Arrays.copyOf;
import static se.jbee.game.common.state.Entity.codePoints;
import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.border;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

public class SaveGame implements Screen, GameComponent, Gfx {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		int w = screen.width/2;
		int h = screen.height/2;
		int x0 = screen.width/4;
		int y0 = screen.height/4;
		
		Entity game1 = game.single(GAME);
		if (game1.has(RETURN_SCREEN)) {
			scene.place(background(x0-1, y0-1, w+3, h+3, 2));
		} else {
			scene.place(background(0, 0, screen.width, screen.height, 2));
		}
		
		scene.place(border(x0, y0, w, h));
		scene.place(border(x0+20, y0+20, w*2/3-40, h*2/3-40));
	
		scene.place(text(x0+(w*2/3), y0+h, FONT_REGULAR, h/3, COLOR_TEXT_NORMAL, 1));
		scene.place(codePoints(String.valueOf(game1.num(TURN))));
		
		int inputSize = h/7;
		int[] savegame = game1.list(SAVEGAME);
		scene.place(text(x0+20, y0+h-20, FONT_LIGHT, inputSize, COLOR_TEXT_NORMAL, 1));
		scene.place(codePoints(">"));
		scene.place(text(x0+20+50, y0+h-20, FONT_LIGHT, inputSize, COLOR_TEXT_HIGHLIGHT, 1));
		scene.place(savegame);
		
		int gid = game1.id();
		if (savegame.length < 12) {
			for (char c = 'a'; c <= 'z'; c++) {
				scene.bindKey(c, new Change(gid, SAVEGAME, Op.APPEND, c));
			}
		}
		if (savegame.length > 0) {
			scene.bindKey('\b', new Change(gid, SAVEGAME, Op.PUT, copyOf(savegame, savegame.length-1) ));
			scene.bindKey('\n', new Change(gid, SCREEN, Op.PUT, 2));
		}
		scene.bindKey((char)27, new Change(gid, SCREEN, Op.PUT, game1.num(RETURN_SCREEN)), new Change(gid, RETURN_SCREEN, Op.ERASE));
	}

}
