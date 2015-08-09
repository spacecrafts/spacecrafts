package se.jbee.game.scs.process;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.State;

/**
 * The game process it the master process. 
 * It is the only process existing when starting or loading a game.
 * 
 * Dependent on the game state it spawns the {@link Players} and {@link AI} processes.
 * It also monitors their health and restarts them should that be necessary. 
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
public class Game implements Runnable, GameComponent {

	private final State game;
	private final State settings;
	
	public Game(State game, State settings) {
		super();
		this.game = game;
		this.settings = settings;
		// setup all entities and components if this is not a loaded game
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
