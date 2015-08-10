package se.jbee.game.scs.screen;

import static java.util.Collections.singletonList;
import static se.jbee.game.scs.gfx.Objects.planet;

import java.awt.geom.Ellipse2D;

import se.jbee.game.scs.process.IOMapping;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.Change.Op;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

public class Screen1 implements Screen, GameComponent {

	@Override
	public void show(State game, IOMapping mappings) {
		Entity g1 = game.entity(game.all(GAME)[0]);
		mappings.objects.set(singletonList(planet(300, 300, 300, 0xFF5014, 0)));
		mappings.onLeftClick.add(new IOMapping.AreaMapping(new Ellipse2D.Float(300, 300, 300, 300), new Change(g1.id(), SCREEN, Op.PUT, 1)));
		
	}

}
