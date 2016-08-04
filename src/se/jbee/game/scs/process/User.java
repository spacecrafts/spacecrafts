package se.jbee.game.scs.process;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.process.Player;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Change.Op;
import se.jbee.game.any.state.ChangeListener;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.screen.Blank;
import se.jbee.game.scs.screen.Colony;
import se.jbee.game.scs.screen.Encounter;
import se.jbee.game.scs.screen.ErrorJournal;
import se.jbee.game.scs.screen.Galaxy;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.screen.IconInfo;
import se.jbee.game.scs.screen.LoadGame;
import se.jbee.game.scs.screen.LoadingGame;
import se.jbee.game.scs.screen.Orbit;
import se.jbee.game.scs.screen.SavingGame;
import se.jbee.game.scs.screen.SetupGame;
import se.jbee.game.scs.screen.SetupPlayer;
import se.jbee.game.scs.screen.SolarSystem;
import se.jbee.game.scs.screen.SplashScreen;
import se.jbee.game.scs.screen.UserSettings;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

/**
 * The {@link User} process takes the role of the currently active human player.
 * All players usually share the same process.
 * 
 * On input events or change of screens the {@link Stage} is prepared with the
 * actual {@link State} of the game.
 */
public final class User implements Runnable, Player, GameComponent, UserComponent, ChangeListener {

	private static final int CYCLE_TIME_MS = 20; 
	
	private final State user;
	private final Logic logic;
	private final Stage stage;
	private final Screen[] screens;
	
	private State game;
	private boolean quit = false;
	private int lastScreen = -1;
	/**
	 * This is used so this thread actually applies the event changes coming in
	 * asynchronous to this thread that reads the same game {@link State}. With
	 * this however the human player changes are applied synchronous with the
	 * usage.
	 */
	private final AtomicReference<Change[]> unappliedChangeset = new AtomicReference<Change[]>();

	public User(State game, State user, Logic logic, Stage stage) {
		super();
		this.game = game;
		this.user = user;
		this.logic = logic;
		this.stage = stage;
		stage.listener = this;
		//TODO this list should occur in Starcrafts class
		this.screens = ScreenNo.Init.screens(ErrorJournal.class, Blank.class, IconInfo.class, SplashScreen.class, SavingGame.class, LoadGame.class, LoadingGame.class, UserSettings.class, SetupGame.class, SetupPlayer.class, Encounter.class, 
				Galaxy.class, SolarSystem.class, Orbit.class, Colony.class);
		initGlobalKeys(game, stage);
	}
	
	public void setGame(State game) {
		this.game = game;
	}

	@Override
	public void on(Change[] changeset) {
		if (unappliedChangeset.compareAndSet(null, changeset)) {
			move();
		} // we ignore changes coming in "to fast" (should not happen anyway)
	}
	
	@Override
	public void move() {
		lastScreen = -1;
	}
	
	@Override
	public void quit() {
		quit = true;
		move();
	}
	
	@Override
	public void run() {
		final Entity gamE = game.single(GAME);
		final Entity u1 = user.single(USER);
		final int[] resolution = u1.list(RESOLUTION);
		final Dimension screen = new Dimension(resolution[0], resolution[1]);
		while (!quit) {
			long loopStart = System.currentTimeMillis();
			Change[] changeset = unappliedChangeset.getAndSet(null);
			if (changeset != null) {
				Change.apply(changeset, game);				
			}
			int currentScreen = gamE.num(SCREEN);
			if (currentScreen != lastScreen) {
				stage.startOver();
				screens[currentScreen].show(user, game, screen, stage);
				stage.ready();
				lastScreen = currentScreen;
			}
			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < CYCLE_TIME_MS) {
				try { Thread.sleep(CYCLE_TIME_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}
		System.out.println("Shuting down human players interface");
	}

	//TODO should not be here...
	private static void initGlobalKeys(State game, Stage stage) {
		int gameId = game.single(GAME).id();
		stage.onGlobalKey(KeyEvent.VK_ESCAPE,
				new Change(gameId, RETURN_SCREEN, Op.COPY, gameId, SCREEN),
				new Change(gameId, SCREEN, Op.SET, GameScreen.SCREEN_MAIN));
		// TODO below just for test purposes
		stage.onGlobalKey(KeyEvent.VK_I,
				new Change(gameId, RETURN_SCREEN, Op.COPY, gameId, SCREEN),
				new Change(gameId, SCREEN, Op.SET, GameScreen.SCREEN_ICON_INFO));
	} 
	
}
