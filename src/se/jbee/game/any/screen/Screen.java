package se.jbee.game.any.screen;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.state.State;


public interface Screen {

	void show(State user, State game, Dimension screen, Stage stage);
}
