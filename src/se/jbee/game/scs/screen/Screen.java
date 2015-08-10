package se.jbee.game.scs.screen;

import se.jbee.game.scs.process.IOMapping;
import se.jbee.game.state.State;


public interface Screen {

	void show(State game, IOMapping mappings);
}
