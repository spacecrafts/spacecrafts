package se.jbee.game.any.process;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Drawable;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.gfx.Stage.AreaMapping;
import se.jbee.game.any.gfx.Stage.Hover;
import se.jbee.game.any.gfx.Stage.KeyMapping;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.ScsRenderer;

/**
 * A loop that looks at the {@link Stage}.
 * If there are changes they are drawn to the game canvas (what is also this object).
 */
public class DisplayLoop extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long FRAME_DELAY_MS = 15;

	private final Stage stage = new Stage();
	private final ScsRenderer renderer;
	private final Resources resources;
	private final InputLoop input;
	private final JFrame frame;
	
	public DisplayLoop(ScsRenderer renderer, Resources resources, Screen[] screens) {
		super();
		this.renderer = renderer;
		this.resources = resources;
		this.input = new InputLoop(stage, screens, this);
		this.frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setLocation(0, 0);

		JPanel panel = (JPanel) frame.getContentPane();
		java.awt.Dimension screen = getToolkit().getScreenSize();
		panel.setPreferredSize(screen);
		panel.setLayout(null);
		setBounds(0,0, screen.width, screen.height);
		panel.add(this);

		setIgnoreRepaint(true); // No AWT repaint for canvas - done manually

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		requestFocus(); // request the focus so key events come to us

		createBufferStrategy(2);
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);		
	}
	
	public void setGame(State game) {
		input.setGame(game);
		frame.setTitle(game.root().name());
	}
	
	public Stage getStage() {
		return stage;
	}

	@Override
	public void run() {
		Thread inputDaemon = GameLoop.daemon(input, "Game Input Loop");
		inputDaemon.start();
		final BufferStrategy strategy = getBufferStrategy();
		final Dimension screen = new Dimension(getSize());
		int frameDone = -1;
		while (true) {
			long loopStart = System.currentTimeMillis();

			Stage currentStage = stage;
			if (currentStage != null) {
				int frame = currentStage.frame();
				if (frameDone != frame) { // only draw if something has changed
					frameDone = frame;

					Graphics2D gfx = (Graphics2D) strategy.getDrawGraphics();
					renderer.render(currentStage, screen, resources, gfx);
					gfx.dispose();
					strategy.show();
				}
			}

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < FRAME_DELAY_MS) {
				try { Thread.sleep(FRAME_DELAY_MS - cycleTimeMs); } catch (Exception e) {
					// just go on
				}
			}
		}
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
	public void keyPressed(KeyEvent e) { /* not used */ }
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!stage.isReady())
			return;
		for (Hover m : stage.onPointing) {
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
		stage.highlight(Collections.<Drawable>emptyList());
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
	public void keyReleased(KeyEvent e) {
		if (!stage.isReadyForInputs())
			return;
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
		stage.listener.on(changeset);
	}
}
