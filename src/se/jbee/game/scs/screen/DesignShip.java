package se.jbee.game.scs.screen;

import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.state.State;

public class DesignShip implements Screen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		// TODO Auto-generated method stub
		
		// flow:
		// 1. player starts a new module by marking/un-marking cell(s) via left click
		// 2. view reacts to 1 by drawing a shape containing all the marked cells
		// 3. player switches the shape via right click
		// 4. like 2
		// 5. player completes the module with enter/space/return or cancels it by ESC
		
		// drawing a shape around sample points:
		// 1. the area surrounding a cell is split like an "x" dividing the space in top, left, bottom, right regions
		// 2. search for the region with the fewest other points contain within. 
		//    if two regions have the lest both control the direction, 
		//    if three have the lest the middle one controls the direction
		// 3. move the point by a half cell length (shape border line goes through the center, top, left, right or bottom middle point of the cell) until the resulting shape actually contains all cells.
	}

}
