package se.jbee.game.scs.screen;

import static java.util.Collections.singletonList;
import static se.jbee.game.scs.gfx.Objects.border;

import java.awt.geom.Rectangle2D;

import se.jbee.game.scs.process.IOMapping;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.Change.Op;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

public class Screen2 implements Screen, GameComponent {

	@Override
	public void show(State game, IOMapping mappings) {
		Entity g1 = game.entity(game.all(GAME)[0]);
		mappings.objects.set(singletonList(border(300, 300, 300, 300)));
		mappings.onLeftClick.add(new IOMapping.AreaMapping(new Rectangle2D.Float(300, 300, 300, 300), new Change(g1.id(), SCREEN, Op.PUT, 0)));
	}

}
