package se.jbee.game.scs.process;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.List;

import se.jbee.game.scs.logic.Autosave;
import se.jbee.game.scs.logic.Save;
import se.jbee.game.scs.logic.Setup;
import se.jbee.game.scs.logic.Step;
import se.jbee.game.scs.screen.Colony;
import se.jbee.game.scs.screen.Encounter;
import se.jbee.game.scs.screen.Galaxy;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.screen.LoadGame;
import se.jbee.game.scs.screen.LoadingGame;
import se.jbee.game.scs.screen.Orbit;
import se.jbee.game.scs.screen.SaveGame;
import se.jbee.game.scs.screen.SavingGame;
import se.jbee.game.scs.screen.SetupGame;
import se.jbee.game.scs.screen.SetupPlayer;
import se.jbee.game.scs.screen.SolarSystem;
import se.jbee.game.scs.screen.SplashScreen;
import se.jbee.game.scs.screen.UserSettings;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.gfx.Stage.AreaMapping;
import se.jbee.game.uni.gfx.Stage.AreaObject;
import se.jbee.game.uni.gfx.Stage.KeyMapping;
import se.jbee.game.uni.process.Player;
import se.jbee.game.uni.screen.Screen;
import se.jbee.game.uni.screen.ScreenNo;
import se.jbee.game.uni.state.Change;
import se.jbee.game.uni.state.Change.Op;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * The {@link Humans} process takes the role of the currently active human
 * player. It prepares the screen and actions for the current state of that
 * player so the display process shows them. It encapsulates human player input
 * and conceptual graphical output capabilities.
 * 
 * The process processes UI input events, determines change-sets and applies
 * them. In such cases it updates itself after a change-set had been applied.
 * Then it waits for more events or an update signal from the game process.
 * 
 * The game process might send an update signal when the game process itself has
 * done state manipulations, like when battles occur or a new turn has started.
 * 
 * The currently active player is not stored within this process but part of the
 * game state.
 */
public final class Humans implements Runnable, Player, GameComponent, UserComponent, KeyListener, MouseListener, MouseMotionListener {

	private final State game;
	private final State user;
	private final Stage stage;
	private final Screen[] screens;
	
	private int ignoredKeyCode = 0;
	private boolean quit = false;

	public Humans(State game, State user, Stage stage) {
		super();
		this.game = game;
		this.user = user;
		this.stage = stage;
		this.screens = ScreenNo.Init.screens(SplashScreen.class, SaveGame.class, SavingGame.class, LoadGame.class, LoadingGame.class, UserSettings.class, SetupGame.class, SetupPlayer.class, Encounter.class, 
				Galaxy.class, SolarSystem.class, Orbit.class, Colony.class);
		initGlobalKeys(game, stage);
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
		final Entity gamE = game.single(GAME);
		final Entity u1 = user.single(USER);
		while (!quit) {
			ignoredKeyCode = 0;
			int[] resolution = u1.list(RESOLUTION);
			int screenNo = gamE.num(SCREEN);
			stage.startOver();
			screens[screenNo].show(user, game, new Dimension(resolution[0], resolution[1]), stage);
			stage.ready();
			
			if (gamE.has(ACTION)) {
				doAction();
			} else {
				doWait();
			}
		}
		System.out.println("Shuting down human players interface");
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

	private void doAction() {
		final Entity gamE = game.single(GAME);
		int action = gamE.num(ACTION);
		switch(action) {
		case ACTION_EXIT  : new Autosave().progress(user, game); System.exit(0); break;
		case ACTION_SAVE  : new Save().progress(user, game); break;
		case ACTION_SETUP : new Setup().progress(user, game); break;
		case ACTION_STEP  : new Step().progress(user, game); break;
		case ACTION_LOAD  : new Autosave().progress(user, game); gamE.put(ACTION, ACTION_INIT); // Intentional fall-through 
		case ACTION_INIT  : doWait(); return; // in case player wakes up before it is quit when loading we just wait again
		}
		gamE.erase(ACTION);
	}

	private static void initGlobalKeys(State game, Stage stage) {
		int gameId = game.single(GAME).id();
		stage.onGlobalKey(KeyEvent.VK_ESCAPE,
				new Change(gameId, RETURN_SCREEN, Op.COPY, gameId, SCREEN),
				new Change(gameId, SCREEN, Op.PUT, GameScreen.SCREEN_MAIN));
	} 
	
	/*
	 * -----------------------------------------------------
	 * Input Event Handling 
	 * -----------------------------------------------------
	 */

	@Override
	public void mouseDragged(MouseEvent e) { /* not used */ }
	@Override
	public void mousePressed(MouseEvent e) { /* not used */ }
	@Override
	public void mouseReleased(MouseEvent e) { /* not used */ }
	@Override
	public void mouseEntered(MouseEvent e) { /* not used */ }
	@Override
	public void mouseExited(MouseEvent e) { /* not used */ }
	@Override
	public void keyTyped(KeyEvent e) { /* not used */ }
	@Override
	public void keyReleased(KeyEvent e) { /* not used */ }
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!stage.isReady())
			return;
		for (AreaObject m : stage.onMouseOver) {
			if (m.area.contains(e.getPoint())) {
				stage.highlight(m.objects);
				if (m.cursor >= 0) {
					e.getComponent().setCursor(Cursor.getPredefinedCursor(m.cursor));
				}
				e.consume();
				return;
			}
		}
		e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		stage.highlight(Collections.<int[]>emptyList());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!stage.isReadyForInputs())
			return;
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: react(e, stage.onLeftClick); break;
		case MouseEvent.BUTTON3: react(e, stage.onRightClick); break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!stage.isReadyForInputs() || e.getKeyCode() == ignoredKeyCode)
			return;
		ignoredKeyCode = e.getKeyCode();
		if (!react(e, stage.onKeyPress)) {
			react(e, stage.globalOnKeyPress);
		}
	}

	private boolean react(KeyEvent e, List<KeyMapping> mappings) {
		final int keyCode = e.getKeyCode();
		for (KeyMapping m : mappings) {
			if (keyCode == m.keyCode) {
				e.consume();
				reactWith(m.changeset);
				return true;
			}
		}
		return false;
	}

	private void react(MouseEvent e, List<AreaMapping> mappings) {
		for (AreaMapping m : mappings) {
			if (m.area.contains(e.getPoint())) {
				e.consume();
				reactWith(m.changeset);
				return;
			}
		}
	}

	private void reactWith(Change[] changeset) {
		apply(changeset, game);
		move();
		return;
	}

	private static void apply(Change[] changeset, State game) {
		for (Change c : changeset) {
			c.apply(game);
		}
	}
}
