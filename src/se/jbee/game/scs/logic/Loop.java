package se.jbee.game.scs.logic;

import java.util.ArrayList;
import java.util.List;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.process.Game;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.process.AI;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.PlayerStatus;

/**
 * The game loop has completed one time, see what has happened and react.
 * 
 * The main game loop also takes care of controlling the AI players. 
 * 
 * Usually it polls the game state to see if all players are done.
 * Than all battles (with or without human players) are carried out.
 * Finally the game state is advanced a turn. This includes:
 *
 * - moving spaceships
 * - accumulating incomes and outgoings
 * - add finished researches
 * - unroll previously undiscovered galaxies/clusters/solar systems
 * - ...
 */
public class Loop implements Transition, GameComponent {

	private final List<AI> AIs = new ArrayList<AI>();
	private final List<Thread> AIworkers = new ArrayList<Thread>();
	
	@Override
	public State transit(State game, Logic logic) throws Exception {
		if (game == null) {
			return logic.run(Init.class, State.base());
		}
		final Entity gamE = game.root();
		if (gamE.has(ACTION)) {
			game = runActions(game, logic);
		}
		if (Turn.isEndOfTurn(game)) { // TODO do this within the "default" transition? return null can be used to indicate no change => no move
			// TODO run encounters (battles ordered or resulting from an conflict due to simultaneous space occupation.
			
			// advance to next turn
			logic.run(Turn.class, game);
			moveAIs(game);
		}
		return game;
	}

	private State runActions(State game, Logic logic) throws Exception {
		final Entity gamE = game.root();
		int[] actions = gamE.list(ACTION);
		gamE.unset(ACTION); // unset before potential save
		for (int action : actions) {
			switch(action) {
			case ACTION_EXIT  : System.exit(0); break;
			case ACTION_ERROR : gamE.set(SCREEN, GameScreen.SCREEN_ERROR); break;
			case ACTION_AUTOSAVE: logic.run(Autosave.class, game); break;
			case ACTION_SAVE  : logic.run(Save.class, game); break;
			case ACTION_LOAD  :	game = logic.run(Load.class, game); break;
			case ACTION_MOVE_AI: moveAIs(game); break;
			case ACTION_QUIT_AI: quitAIs(); break;
			case ACTION_SETUP : logic.run(Setup.class, game); break;
			case ACTION_DONE  : // Intentional fall-through (these 3 are almost the same except that players intentions are explicit in ending a plan or turn)
			case ACTION_NEXT_PLAN : 
			case ACTION_NEXT_TASK : logic.run(Next.class, game); break;
			}
		}
		return game;
	}
	
	private void moveAIs(State game) throws InterruptedException {
		if (!AIworkers.isEmpty()) {
			quitAIs();
		}
		if (AIs.isEmpty()) {
			for (Entity player : game.entities(game.root().list(PLAYERS))) {
				if (player.isBitSet(STATUS, PlayerStatus.AI)) {
					AIs.add(new AI(game, player.id()));
				}
			}
		}
		for (Runnable ai : AIs) {
			AIworkers.add(Game.daemon(ai, "AI "));
		}
		for (Thread worker : AIworkers)
			worker.start();
	}
	
	private void quitAIs() throws InterruptedException {
		for (AI ai : AIs) 
			ai.quit();
		for(Thread worker : AIworkers)
			if (worker.isAlive())
				worker.join();
		AIworkers.clear();
		//FIXME after load AIs need to be rebuild but not otherwise - new separate action
		// this can be done "automatically" by tracking change of game instance! 
	}
	
}
