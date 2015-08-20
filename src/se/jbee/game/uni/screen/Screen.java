package se.jbee.game.uni.screen;

import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.state.State;


public interface Screen {

	void show(State user, State game, Dimension screen, Stage stage);
}
