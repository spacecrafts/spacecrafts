package se.jbee.game.scs.process;

import static se.jbee.game.common.state.Entity.codePoints;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.common.state.Component;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.screen.LoadGame;
import se.jbee.game.scs.screen.SaveGame;
import se.jbee.game.scs.screen.SavingGame;
import se.jbee.game.scs.screen.SolarSystem;
import se.jbee.game.scs.screen.SplashScreen;
import se.jbee.game.scs.screen.UserSettings;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

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
public class Game implements Runnable, GameComponent, UserComponent {

	public static void main(String[] args) {
		Game g = new Game(State.base(), State.base());
		g.run();
	}
	
	
	private final State game;
	private final State user;
	
	private final Entity game1;
	
	private final List<Thread> players = new ArrayList<>();
	
	public Game(State game, State user) {
		super();
		this.game = game;
		this.user = user;
		initComponents(game, GameComponent.class);
		int[] gameId = game.all(GAME);
		this.game1 = gameId.length == 0 ? initGame(game) : game.entity(gameId[0]);
		initComponents(user, UserComponent.class);
		initUser(user);
	}
	
	/**
	 * This is also done for loaded game so that one can be sure that the
	 * current code has all the components.
	 */
	public static void initComponents(State game, Class<? extends Component> components) {
		for (Field f : components.getDeclaredFields()) {
			try {
				int type = f.getInt(null);
				if (!game.hasComponent(type)) {
					game.defComponent(type).put(NAME, codePoints(f.getName()));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static Entity initGame(State game) {
		Entity g = game.defEntity(GAME);
		g.put(TURN, 0);
		g.put(SCREEN, 0);
		Entity p1 = game.defEntity(PLAYER);
		g.put(PLAYERS, p1.id());
		return g;
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

	@Override
	public void run() {
		while (true) {
			int turn = game1.num(TURN);
			if (turn == 0 && players.isEmpty()) {
				Thread player = new Thread(new Players(game, user, 
						SplashScreen.class, SaveGame.class, SavingGame.class, LoadGame.class, UserSettings.class, SolarSystem.class						
						), "SCS Players");
				player.setDaemon(true);
				players.add(player);
				player.start();
			}
		}
	}

}
