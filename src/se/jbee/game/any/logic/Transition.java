package se.jbee.game.any.logic;

import se.jbee.game.any.state.State;

/**
 * It is the answer to the question: "What happens (in the game) when... ?"
 * 
 * But so far the main purpose of this interface is to give all game transitions
 * a uniform interface and to act as a unit of thought for proceedings in the
 * game.
 * 
 * At some point it also may allow for easier generic programming.
 */
public interface Transition {

	/**
	 * The game state is manipulated so that the game advances according to the
	 * logic of this {@link Transition}. The user state usually is just read.
	 */
	void transit(State user, State game);
}
