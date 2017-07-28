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
	 * logic of this {@link Transition}.
	 * 
	 * The {@link Logic} gives "abstract" access to other {@link Transition}s
	 * that might be incorporated into this transition. This however doesn't
	 * change the fact that a {@link Transition} only can change {@link State}
	 * and has no other side effects.
	 * 
	 * @return the new game state. return null to indicate no change, the given
	 *         state to indicate mutable change or a fresh instance to indicate
	 *         complete state change (new game loaded).
	 */
	State transit(State game, Logic logic) throws Exception;
}
