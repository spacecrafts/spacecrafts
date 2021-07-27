package se.jbee.game.scs2;

/**
 * Container class used for the model of the game.
 */
public interface Spacecrafts {

	enum ResourceType {
		FOOD,
		ENERGY,
		RARE_MATERIALS
	}
	
	/**
	 * A specific "cell" in the grid 
	 */
	final class Component {
		
		int structure;
		
	}
}
