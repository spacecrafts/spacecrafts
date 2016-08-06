package se.jbee.game.scs.logic;

import java.util.ArrayList;
import java.util.List;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.process.Player;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;

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

	List<Player> AIs = new ArrayList<Player>();
	
	@Override
	public State transit(State game, Logic logic) throws Exception {
		if (game == null) {
			return logic.run(Init.class, State.base());
		}
		final Entity gamE = game.single(GAME);
		if (gamE.has(ACTION)) {
			int action = gamE.num(ACTION);
			gamE.unset(ACTION); // unset before potential save
			switch(action) {
			case ACTION_EXIT  : logic.run(Autosave.class, game); System.exit(0); break;
			case ACTION_ERROR : gamE.set(SCREEN, GameScreen.SCREEN_ERROR); break;
			case ACTION_SAVE  : logic.run(Save.class, game); break;
			case ACTION_SETUP : logic.run(Setup.class, game); break;
			case ACTION_DONE  : // Intentional fall-through (these 3 are almost the same except that players intentions are explicit in ending a plan or turn)
			case ACTION_NEXT_PLAN : 
			case ACTION_NEXT_TASK : logic.run(Next.class, game); break;
			case ACTION_LOAD  :  
				Player.quit(AIs);
				logic.run(Autosave.class, game);
				game = logic.run(Load.class, game);
				Player.move(AIs);
			}
			//TODO idea: have an action that stops AIs and syncs/waits until they are stopped
			// and another one that starts them again.
		}

		if (Turn.isEndOfTurn(game)) { // TODO do this within the "default" transition? return null can be used to indicate no change => no move
			// TODO run encounters (battles ordered or resulting from an conflict due to simultaneous space occupation.
			
			// advance to next turn
			logic.run(Turn.class, game);
			Player.move(AIs);
		}
		return game;
		
		//FIXME 5 situations:
		// 1. do nothing
		// 2. wake-up players for next turn
		// 3. quit AIs
		// 4. start AIs
		// 5. restart a completely different game (AIs + UI)
			
	}
	
}
