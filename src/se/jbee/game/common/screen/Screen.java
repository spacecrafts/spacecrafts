package se.jbee.game.common.screen;

import se.jbee.game.common.gfx.Dimension;
import se.jbee.game.common.gfx.Stage;
import se.jbee.game.common.state.State;


public interface Screen {

	void show(State user, State game, Dimension screen, Stage stage);
}
