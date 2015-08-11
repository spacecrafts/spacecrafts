package se.jbee.game.scs.process;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.List;

import se.jbee.game.scs.process.Scene.AreaMapping;
import se.jbee.game.scs.process.Scene.AreaObject;
import se.jbee.game.scs.screen.Screen;
import se.jbee.game.scs.screen.Screen1;
import se.jbee.game.scs.screen.Screen2;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.Entity;
import se.jbee.game.state.State;

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
public final class Players implements Runnable, GameComponent, KeyListener, MouseListener, MouseMotionListener {

	private static final Screen[] SCREENS = { new Screen1(), new Screen2() }; 
	
	private final State game;
	private final State user;
	
	private final Display display;
	private final Scene scene = new Scene();
	
	public Players(State game, State user) {
		super();
		this.game = game;
		this.user = user;
		this.display = new Display(scene, this, this, this);
	}

	@Override
	public void run() {
		Thread displayThread = new Thread(display, "SCS Display");
		displayThread.setDaemon(true);
		displayThread.start();
		
		final Entity g1 = game.entity(game.all(GAME)[0]);
		while (true) {
			int screenNo = g1.num(SCREEN);
			scene.startOver();
			SCREENS[screenNo].show(game, display.getSize(), scene);
			scene.ready();
			try { synchronized (this) {
				wait();
			} } catch ( InterruptedException e) {}
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
	public void keyPressed(KeyEvent e) { /* not used */ }
	@Override
	public void keyReleased(KeyEvent e) { /* not used */ }

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!scene.isReady())
			return;
		for (AreaObject m : scene.onMouseOver) {
			if (m.area.contains(e.getPoint())) {
				scene.areaObjects.set(m.objects);
				e.consume();
				return;
			}
		}
		scene.areaObjects.set(Collections.<int[]>emptyList());
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
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 27) {
			System.exit(0);
		}
	}
	
	private void react(MouseEvent e, List<AreaMapping> mappings) {
		for (AreaMapping m : mappings) {
			if (m.area.contains(e.getPoint())) {
				apply(m.changeset, game);
				e.consume();
				synchronized (this) {
					notify();
				}
				return;
			}
		}
	}
	
	private static void apply(Change[] changeset, State game) {
		for (Change c : changeset) {
			c.apply(game);
		}
	}
}
