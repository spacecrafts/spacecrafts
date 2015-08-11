package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.planet;
import static se.jbee.game.scs.gfx.Objects.starArc;

import java.awt.Dimension;
import java.awt.Rectangle;

import se.jbee.game.scs.process.Scene;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.Change.Op;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

public class Screen2 implements Screen, GameComponent {

	@Override
	public void show(State game, Dimension screen, Scene scene) {
		Entity g1 = game.entity(game.all(GAME)[0]);
		Rectangle area = new Rectangle(690, 390, 220, 220);

		scene.place(background(1));
		scene.place(planet(700, 400, 200, 0xFF5014, 0));
		scene.place(planet(100, 300, 400, 0x44FF99, 0));
		scene.place(starArc(screen.width-screen.height/8, -screen.height/2, screen.height*2, 0xFF8800, 0));
		
		scene.bindLeftClick(area, new Change(g1.id(), SCREEN, Op.PUT, 0));
		scene.bind(area, focusBox(690, 390, 220, 220));
	}

}
