package se.jbee.game.any.process;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.process.AI;
import se.jbee.game.scs.state.GameComponent;

/**
 * The game process it the "master control program" process.
 * It is the only process existing when starting or loading a game.
 *
 * Dependent on the game state it spawns the {@link ScreenSwitch} and {@link AI} processes.
 * It also monitors their health and restarts them should that be necessary.
 *
 * The actual {@link State} {@link Change}s are done using {@link Transition}s.
 */
public class Game implements Runnable, GameComponent {

	private static final int CYCLE_TIME_MS = 20;
	
	private final Logic logic;
	private final Display display;
	
	public Game(Logic logic, Display display) {
		super();
		this.logic = logic;
		this.display = display;
	}

	@Override
	public void run() {
		Thread displayDaemon = Game.daemon(display, "Game Paint Loop");
		State game = logic.runLoop(null);
		display.setGame(game);
		displayDaemon.start();
		while (true) {
			long loopStart = System.currentTimeMillis();

			State game2 = logic.runLoop(game);
			if (game != game2) {
				game = game2;
				display.setGame(game);
			}

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < CYCLE_TIME_MS) {
				try { Thread.sleep(CYCLE_TIME_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}
	}

	public static Thread daemon(Runnable r, String name) {
		Thread t = new Thread(r, name);
		t.setDaemon(true);
		return t;
	}
	
}
