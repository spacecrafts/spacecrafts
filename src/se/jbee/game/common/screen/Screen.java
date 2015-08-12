package se.jbee.game.common.screen;

import java.awt.Dimension;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.state.State;


public interface Screen {

	void show(State user, State game, Dimension screen, Scene scene);
}
