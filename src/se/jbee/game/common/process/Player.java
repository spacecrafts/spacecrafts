package se.jbee.game.common.process;

public interface Player {

	/**
	 * notifies a waiting player to now continue doing its move.
	 */
	void move();
	
	/**
	 * instructs the player to stop (terminate)
	 */
	void quit();
}
