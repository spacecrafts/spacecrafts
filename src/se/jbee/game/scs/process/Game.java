package se.jbee.game.scs.process;

import static se.jbee.game.uni.state.Entity.codePoints;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.process.Player;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.Name;
import se.jbee.game.uni.state.State;

/**
 * The game process it the master process.
 * It is the only process existing when starting or loading a game.
 *
 * Dependent on the game state it spawns the {@link Humans} and {@link AI} processes.
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
public class Game implements Runnable, GameComponent, UserComponent {

	public static void main(String[] args) {
		new Game().run();
	}

	@Override
	public void run() {
		final State user = State.base().defComponents(UserComponent.class);
		initUser(user);

		State game = State.base().defComponents(GameComponent.class);
		initGame(game);

		Display display = new Display();
		Dimension size = display.getSize();
		user.single(USER).put(RESOLUTION, new int[] {size.width, size.height});
		Thread displayThread = daemon(display, "SCS Display");

		List<Player> players = new ArrayList<Player>();

		boolean init = true;
		while (true) {
			long loopStart = System.currentTimeMillis();

			if (init) {
				System.out.println("Starting a new game...");

				Stage stage = new Stage();
				Humans humans = new Humans(game, user, stage);
				Thread humanPlayers = daemon(humans, "SCS Players");
				players.add(humans);

				display.setStage(stage);
				display.setInputHandler(humans);

				humanPlayers.start();
				if (displayThread.getState() == java.lang.Thread.State.NEW) {
					displayThread.start();
				}
				init = false;
				System.out.println(Thread.activeCount()+" threads running...");
			}

			if (game.single(GAME).num(ACTION) == ACTION_INIT) { // should another game be loaded?
				System.out.println("Loading game...");
				quit(players);
				game = loadGame(user.single(USER).text(SAVEGAME_DIR), game.single(GAME).text(SAVEGAME));
				game.defComponents(GameComponent.class); // also done for loaded game so that one can be sure that the current code has all the components.
				init = true;
			} else {
				if (isEndOfTurn(game)) {
					// TODO run encounters (battles ordered or resulting from an conflict due to simultaneous space occupation.

					// advance to next turn
					new Turn().progress(user, game);

					// wake-up players
					move(players);
				}
			}

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < 20) {
				try { Thread.sleep(20 - cycleTimeMs); } catch (Exception e) {}
			}
		}
	}

	private static void quit(List<Player> players) {
		for (Player p : players) {
			p.quit();
		}
	}

	private static void move(List<Player> players) {
		for (Player p : players) {
			p.move();
		}
	}

	private State loadGame(String dir, String file) {
		try {
			return State.load(new File(dir, file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String savegameName(Entity gamE) {
		return gamE.text(NAME).replace(' ', '_')+"-"+String.valueOf(gamE.num(TURN));
	}
	
	public static String autosavegameName(Entity gamE) {
		return "auto-"+savegameName(gamE);
	}

	private static boolean isEndOfTurn(State game) {
		Entity gamE = game.single(GAME);
		final int turn = gamE.num(TURN);
		int[] players = gamE.list(PLAYERS);
		for (int i = 0; i < players.length; i++) {
			if (game.entity(players[i]).num(TURN) != turn)
				return false;
		}
		return true;
	}

	public static void initGame(State game) {
		Entity gamE = game.defEntity(GAME);
		long seed = System.currentTimeMillis();
		gamE.put(SEED, seed);
		gamE.put(NAME, codePoints(Name.name(Name.NAME_GALAXIA, seed)));
		gamE.put(TURN, 0);
		gamE.put(SCREEN, GameScreen.SCREEN_MAIN);
		Entity p1 = game.defEntity(PLAYER);
		p1.put(NO, 1);
		p1.put(TURN, -1);
		gamE.put(PLAYERS, p1.id());
		gamE.put(SETUP, new int[] {1,1,1});
	}

	public static void initUser(State user) {
		if (!user.hasComponent(USER)) {
			user.defComponent(USER);
			user.defComponent(SAVEGAME_DIR);
		}
		if (user.all(USER).length == 0) {
			Entity u1 = user.defEntity(USER);
			u1.put(SAVEGAME_DIR, codePoints(System.getProperty("user.home")+File.separator+"spacecrafts"));
		}
	}

	private static Thread daemon(Runnable r, String name) {
		Thread t = new Thread(r, name);
		t.setDaemon(true);
		return t;
	}

	/**
	 * The current player is always dreived from game state. The first player
	 * that has not finished the current turn is the current player.
	 */
	public static Entity currentPlayer(State game) {
		Entity gamE = game.single(GAME);
		int turn = gamE.num(TURN);
		int[] players = gamE.list(PLAYERS);
		for (int i = 0; i < players.length; i++) {
			Entity player = game.entity(players[i]);
			if (player.num(TURN) < turn)
				return player;
		}
		return null;
	}
}
