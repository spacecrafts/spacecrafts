package se.jbee.game.scs.process;

import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

/**
 * An AI is a process that does the same kind of modifications to a specific
 * players state like a human player does through the UI.
 * 
 * The process pauses when all changes are done for the current turn. It is
 * notified to continue by the main game process when a new turn has started.
 * It might, however, also start after a game is loaded so it has to check if it
 * is already done when it becomes active.
 * 
 * This AI does all the economic and strategic decisions but is not concerned
 * with fighting battles.
 */
public class AI implements Runnable, GameComponent {

	private final State game;
	private final Entity ai;

	private boolean pause = false;
	
	public AI(State game, int eAI) {
		super();
		this.game = game;
		this.ai = game.entity(eAI);
	}

	
	public void resume() {
		pause = false;
	}
	
	public void pause() {
		pause = true;
	}
	
	@Override
	public void run() {
		makeTurnMoves();
	}

	private void makeTurnMoves() {
		// TODO Auto-generated method stub
		
		ai.set(TURN, game.root().num(TURN)); // AI is done
	}
	
	@Override
	public String toString() {
		return ""+ai.id();
	}

}
