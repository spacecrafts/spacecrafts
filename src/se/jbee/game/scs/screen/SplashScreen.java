package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.state.GameComponent;

public class SplashScreen implements Screen, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		Entity g1 = game.single(GAME);
		
		scene.place(background(0,0, screen.width, screen.height, 0));
		scene.bindLeftClick(new Ellipse2D.Float(300, 300, 300, 300), new Change(g1.id(), SCREEN, Op.PUT, 1), new Change(g1.id(), RETURN_SCREEN, Op.PUT, 0));
	}

}
