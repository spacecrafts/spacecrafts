package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;

import se.jbee.game.scs.process.Scene;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.Change.Op;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

public class Screen1 implements Screen, GameComponent {

	@Override
	public void show(State game, Dimension screen, Scene scene) {
		Entity g1 = game.entity(game.all(GAME)[0]);
		
		scene.place(background(0));
		scene.bindLeftClick(new Ellipse2D.Float(300, 300, 300, 300), new Change(g1.id(), SCREEN, Op.PUT, 1));

	}

}
