package se.jbee.game.scs.state;

import se.jbee.game.common.state.Component;

public interface UserComponent extends Component {

	
	int
	
	/*
	 * User/Settings
	 */
	USER = 1000,
		SAVEGAME_DIR = 1001 // the directory where to store savegames
		
		;
}
