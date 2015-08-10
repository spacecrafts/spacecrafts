package se.jbee.game.scs.process;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.Status;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

/**
 * An AI is a process that does the same kind of modifications to a specific
 * players state like a human player does through the UI.
 * 
 * The process pauses when all changes are done for the current turn. It is
 * notified to continue by the main game process when a new turn has started.
 * However, it might also start after a game is loaded so it has to check if it
 * is already done when it becomes active.
 * 
 * This AI does all the economic and strategic decisions but is not concerned
 * with fighting battles.
 */
public class AI implements Runnable, GameComponent {

	private final State game;
	private final Entity aiPlayer;

	public AI(State game, int aiPlayer) {
		super();
		this.game = game;
		this.aiPlayer = game.entity(aiPlayer);
	}

	@Override
	public void run() {
		while (aiPlayer.isSet(STATUS, Status.ALIVE)) {
			
		}
	}

}
