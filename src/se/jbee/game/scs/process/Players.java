package se.jbee.game.scs.process;

import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
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

	private final State game;
	private final State user;
	
	private final List<AreaMapping> onLeftClick = new ArrayList<>();
	private final List<AreaMapping> onRightClick = new ArrayList<>();
	private final List<AreaMapping> onMouseHover = new ArrayList<>();
	private final List<KeyMapping>  onKeyPress = new ArrayList<>();
	private final List<KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
	private final AtomicReference<List<int[]>> figures = new AtomicReference<>();
	
	private final Thread display;
	
	public Players(State game, State user) {
		super();
		this.game = game;
		this.user = user;
		this.display = new Thread(new Display(figures, this, this, this), "Display");
		this.display.setDaemon(true);
	}

	@Override
	public void run() {
		display.start();
		Random rnd = new Random();
		int[] colors = new int[] { 0x006600, 0x82633F, 0xFF5014 };
		while (true) {
			List<int[]> l = new ArrayList<>();
			l.add(new int[] { rnd.nextInt(1000), rnd.nextInt(600), rnd.nextInt(400)+2, colors[rnd.nextInt(colors.length)] });
			figures.set(l);
			try { Thread.sleep(5); } catch (Exception e) {}
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

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: react(e, onLeftClick);
		case MouseEvent.BUTTON2: react(e, onRightClick);
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
				return;
			}
		}
	}
	
	private static void apply(Change[] changeset, State game) {
		for (Change c : changeset) {
			c.apply(game);
		}
	}

	static final class AreaMapping {
		final Shape area;
		final Change[] changeset;
		public AreaMapping(Shape area, Change[] changeset) {
			super();
			this.area = area;
			this.changeset = changeset;
		}
	}
	
	static final class KeyMapping {
		final int key;
		final Change[] changeset;
		public KeyMapping(int key, Change[] changeset) {
			super();
			this.key = key;
			this.changeset = changeset;
		}
	}
}
