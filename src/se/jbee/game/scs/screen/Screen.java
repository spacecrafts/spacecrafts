package se.jbee.game.scs.screen;

import java.awt.Dimension;

import se.jbee.game.scs.process.Scene;
import se.jbee.game.state.State;


public interface Screen {

	void show(State game, Dimension screen, Scene scene);
}
