package se.jbee.game.scs.process;

import static se.jbee.game.common.state.Entity.codePoints;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import se.jbee.game.common.process.Stage;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Component;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.screen.GameScreen;
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
		new Game().run();
	}
	
	@Override
	public void run() {
		final State user = State.base();
		initComponents(user, UserComponent.class);
		initUser(user);		
		
		State game = State.base();
		initComponents(game, GameComponent.class);
		Entity gamE = initGame(game);
		
		Display display = new Display();
		Dimension size = display.getSize();
		user.single(USER).put(RESOLUTION, new int[] {size.width, size.height});
		Thread gameDisplay = daemon(display, "SCS Display");

		boolean init = true;
		
		while (true) {
			long loopStart = System.currentTimeMillis();
			
			if (init) {
				System.out.println("Starting a new game...");

				Stage stage = newStage(game);
				Players players = new Players(game, user, stage);
				Thread humanPlayers = daemon(players, "SCS Players");

				display.setStage(stage);
				display.setInputHandler(players);
				
				humanPlayers.start();
				if (gameDisplay.getState() == java.lang.Thread.State.NEW) {
					gameDisplay.start();
				}
				init = false;
				System.out.println(Thread.activeCount()+" threads running...");
			}
			
			// should another game be loaded?
			if (gamE.num(ACTION) == GameComponent.ACTION_LOAD) {
				System.out.println("Loading game...");
				try {
					game = State.load(new File(user.single(USER).text(UserComponent.SAVEGAME_DIR), gamE.text(SAVEGAME)));
					gamE = game.single(GAME);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				init = true;
			} else {
				//TODO all players done? -> advance to next turn, wake-up players (incl. AI)
			}
			
			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < 10) {
				try { Thread.sleep(10 - cycleTimeMs); } catch (Exception e) {}
			}
		}
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

	private static Stage newStage(State game) {
		int gameId = game.single(GAME).id();
		Stage stage = new Stage();
		stage.onGlobalKey((char)27, //ESC
				new Change(gameId, RETURN_SCREEN, Op.COPY, gameId, SCREEN),
				new Change(gameId, SCREEN, Op.PUT, GameScreen.SCREEN_MAIN));
		return stage;
	}

	private static Thread daemon(Runnable r, String name) {
		Thread t = new Thread(r, name);
		t.setDaemon(true);
		return t;
	}
}
