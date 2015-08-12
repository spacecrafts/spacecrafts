package se.jbee.game.scs.screen;

import static se.jbee.game.scs.gfx.Objects.background;
import static se.jbee.game.scs.gfx.Objects.focusBox;
import static se.jbee.game.scs.gfx.Objects.planet;
import static se.jbee.game.scs.gfx.Objects.starArc;
import static se.jbee.game.scs.gfx.Objects.text;

import java.awt.Dimension;
import java.awt.Rectangle;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.state.GameComponent;

public class SolarSystem implements Screen, GameComponent {

	@Override
	public void show(State user, State game, Dimension screen, Scene scene) {
		Entity g1 = game.single(GAME);
		Rectangle area = new Rectangle(690, 390, 220, 220);

		int w = screen.width;
		int h = screen.height;
		scene.place(background(0,0,w, h, 1));
		scene.place(starArc(w-h/8, -h/2, h*2, 0xFF8800, 0));

		scene.place(planet(700, 400, 200, 0xFF5014, 0));
		scene.place(planet(100, 300, 400, 0x44FF99, 0));
		scene.place(text(690, 380, 24, 1));
		scene.place(Entity.codePoints("Mars"));
		scene.place(text(90, 280, 24, 1));
		scene.place(Entity.codePoints("Uranus"));
		
		scene.bindLeftClick(area, new Change(g1.id(), SCREEN, Op.PUT, 0));
		scene.bind(area, focusBox(690, 390, 220, 220));
		
		area = new Rectangle(90, 290, 420, 420);
		scene.bindLeftClick(area, new Change(g1.id(), SCREEN, Op.PUT, 0));
		scene.bind(area, focusBox(90, 290, 420, 420));
		
	}

}
