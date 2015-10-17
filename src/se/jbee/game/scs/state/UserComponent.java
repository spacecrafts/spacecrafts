package se.jbee.game.scs.state;

import se.jbee.game.any.state.Component;

public interface UserComponent extends Component {

	
	int
	
	/*
	 * User/Settings
	 */
	USER = 1000,
		RESOLUTION = 1001, // [#w, #h]
		SAVEGAME_DIR = 1002 // the directory where to store savegames
		
		;
}
