package se.jbee.game.scs.process;

import static java.lang.Math.max;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import se.jbee.game.common.process.Scene;
import se.jbee.game.common.process.Scene.AreaMapping;
import se.jbee.game.common.process.Scene.AreaObject;
import se.jbee.game.common.process.Scene.KeyMapping;
import se.jbee.game.common.screen.Screen;
import se.jbee.game.common.screen.ScreenNo;
import se.jbee.game.common.state.Change;
import se.jbee.game.common.state.Change.Op;
import se.jbee.game.common.state.Entity;
import se.jbee.game.common.state.State;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

/**
 * The {@link Players} process takes the role of the currently active human
 * player. It prepares the screen and actions for the current state of that
 * player so the display process shows them.
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
public final class Players implements Runnable, GameComponent, UserComponent, KeyListener, MouseListener, MouseMotionListener {

	private final State game;
	private final State user;

	private final Display display;
	private final Scene scene = new Scene();

	private final Screen[] screens;
	
	private int ignoredKeyCode = 0;

	@SafeVarargs
	public Players(State game, State user, Class<? extends Screen>... screens) {
		super();
		this.game = game;
		this.user = user;
		this.display = new Display(scene, this, this, this);
		this.screens = initScreens(screens);
		int gameId = game.single(GAME).id();
		scene.bindGlobalKey((char)27, //ESC
				new Change(gameId, RETURN_SCREEN, Op.COPY, gameId, SCREEN),
				new Change(gameId, SCREEN, Op.PUT, GameScreen.SCREEN_MAIN));
	} 

	@Override
	public void run() {
		Thread displayThread = new Thread(display, "SCS Display");
		displayThread.setDaemon(true);
		displayThread.start();

		final Entity g1 = game.single(GAME);
		while (true) {
			ignoredKeyCode = 0;
			int screenNo = g1.num(SCREEN);
			scene.startOver();
			screens[screenNo].show(user, game, display.getSize(), scene);
			scene.ready();
			if (g1.has(ACTION)) {
				doAction(g1);
			} else {
				try { synchronized (this) {
					wait();
				} } catch ( InterruptedException e) {}
			}
		}
	}

	private void doAction(final Entity g1) {
		int action = g1.num(ACTION);
		switch(action) {
		case ACTION_EXIT:
			// TODO create an auto-save (this way one slips asking annoying "Really?" dialogs
			System.exit(0);
		case ACTION_SAVE:
			saveGame();
		}
		g1.erase(ACTION);
	}
	
	private Screen[] initScreens(Class<? extends Screen>... screenTypes) {
		int max = 0;
		for (Class<?> c : screenTypes) {
			ScreenNo no = c.getAnnotation(ScreenNo.class);
			max = max(max, no.value());
		}
		Screen[] screens = new Screen[max+1];
		for (int i = 0; i < screenTypes.length; i++) {
			try {
				Class<? extends Screen> screen = screenTypes[i];
				ScreenNo no = screen.getAnnotation(ScreenNo.class);				
				screens[no.value()] = screen.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return screens;
	}
	
	private void saveGame() {
		Entity game1 = game.single(GAME);
		File file = new File(user.single(USER).text(SAVEGAME_DIR), game1.text(SAVEGAME)+".game");
		game1.erase(SAVEGAME);
		try {
			game.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	public void mouseMoved(MouseEvent e) {
		if (!scene.isReady())
			return;
		for (AreaObject m : scene.onMouseOver) {
			if (m.area.contains(e.getPoint())) {
				scene.accentuate(m.objects);
				e.consume();
				return;
			}
		}
		scene.accentuate(Collections.<int[]>emptyList());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!scene.isReady())
			return;
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: react(e, scene.onLeftClick); break;
		case MouseEvent.BUTTON3: react(e, scene.onRightClick); break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!scene.isReady() || e.getKeyCode() == ignoredKeyCode)
			return;
		ignoredKeyCode = e.getKeyCode();
		if (!react(e, scene.onKeyPress)) {
			react(e, scene.globalOnKeyPress);
		}
	}

	private boolean react(KeyEvent e, List<KeyMapping> mappings) {
		final char keyChar = e.getKeyChar();
		for (KeyMapping m : mappings) {
			if (keyChar == m.key) {
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
		synchronized (this) {
			notify();
		}
		return;
	}

	private static void apply(Change[] changeset, State game) {
		for (Change c : changeset) {
			c.apply(game);
		}
	}
}
