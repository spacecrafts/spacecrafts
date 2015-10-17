package se.jbee.game.scs.process;

import se.jbee.game.any.process.Player;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

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
public class AI implements Runnable, Player, GameComponent {

	private final State game;
	private final Entity aiPlayer;

	private boolean quit = false;
	
	public AI(State game, int aiPlayer) {
		super();
		this.game = game;
		this.aiPlayer = game.entity(aiPlayer);
	}
	
	@Override
	public void move() {
		doMove();
	}
	
	@Override
	public void quit() {
		quit = true;
		doMove();
	}
	
	@Override
	public void run() {
		while (!quit) {
			makeTurnMoves();
			if (!quit) {
				doWait();
			}
		}
	}

	private void makeTurnMoves() {
		// TODO Auto-generated method stub
		
		aiPlayer.put(TURN, game.single(GAME).num(TURN)); // AI is done
	}

	private void doMove() {
		synchronized (this) {
			notify();
		}		
	}
	
	private void doWait() {
		try { synchronized (this) {
			wait();
		} } catch ( InterruptedException e) {}
	}

}
