package se.jbee.game.scs.screen;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.state.State;

public class DesignShip implements Screen {

	@Override
	public void show(State game, Dimension screen, Stage stage) {
		
		// flow:
		// 1. player marks all cells that should be contained in the new module
		//    (for fast large modules the rect area between the first two can be "filled")
		// 2. player switches border mode via right click (cornered, rounded, ...)
		// 3. ESC cancels (unmarks all cells), enter/space/return creates the module
		
		// find the first point:
		// pick a point and try to reach an edge by only passing empty cells
		
		// outline:
		// from the first point follow the border clockwise
		// from the center move to the corner with no marked cells beside or the middle with no marked cell beside
	}

}
