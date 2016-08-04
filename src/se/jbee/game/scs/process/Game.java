package se.jbee.game.scs.process;

import java.awt.Dimension;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.process.Player;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

/**
 * The game process it the "master control program" process.
 * It is the only process existing when starting or loading a game.
 *
 * Dependent on the game state it spawns the {@link User} and {@link AI} processes.
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
 * 
 * The actual {@link State} {@link Change}s are done using {@link Transition}s.
 */
public class Game implements Runnable, GameComponent, UserComponent {

	private static final int CYCLE_TIME_MS = 20;
	
	private final Logic logic;
	
	public Game(Logic logic) {
		super();
		this.logic = logic;
	}

	@Override
	public void run() {
		final State user = State.base().defComponents(UserComponent.class);
		initUser(user);

		UI ui = new UI();
		Dimension size = ui.getSize();
		//TODO not a game thing here - move
		user.single(USER).set(RESOLUTION, new int[] {size.width, size.height});
		
		Thread painter = Player.daemon(ui, "Game Paint Loop");
		State game = logic.runLoop(null);
		User users = new User(game, user, logic, ui.getStage());
		Thread controller = Player.daemon(users, "Game Screen Loop");
		painter.start();
		controller.start();
		while (true) {
			long loopStart = System.currentTimeMillis();

			State game2 = logic.runLoop(game);
			if (game != game2) {
				game = game2;
				users.setGame(game);
			}

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < CYCLE_TIME_MS) {
				try { Thread.sleep(CYCLE_TIME_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}
	}
	
	public static void initUser(State user) {
		if (user.all(USER).length == 0) {
			Entity u1 = user.defEntity(USER);
		}
	}
}
