package se.jbee.game.scs.process;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.jbee.game.scs.gfx.Object;
import se.jbee.game.scs.gfx.Painter;

/**
 * The screen or canvas the game is drawn on.
 * 
 * This is a thread continuously transforming abstract figures to concrete draw
 * instructions executed on the canvas.
 */
public class Display extends Canvas implements Runnable, Object {

	private static final long LOOP_TIME_MS = 25;
	
	private AtomicReference<List<int[]>> objects; 

	public Display(AtomicReference<List<int[]>> objects, KeyListener onKey, MouseListener onMouseClick, MouseMotionListener onMouseMove) {
		super();
		this.objects = objects;

		JFrame frame = new JFrame("Spacecraft");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true); 
		frame.setLocation(0, 0);
		
		JPanel panel = (JPanel) frame.getContentPane();
		Dimension screen = getToolkit().getScreenSize();
		panel.setPreferredSize(screen);
		panel.setLayout(null);
		setBounds(0,0, screen.width, screen.height);
		panel.add(this);
		
		// No AWT repaint for canvas - done manually
		setIgnoreRepaint(true);
		
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
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		//disableEvents(eventsToDisable);
		addKeyListener(onKey);
		addMouseListener(onMouseClick);
		addMouseMotionListener(onMouseMove);
		
		// request the focus so key events come to us
		requestFocus();
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
	}

	@Override
	public void run() {
		final BufferStrategy strategy = getBufferStrategy();
		final Dimension screen = getSize();
		while (true) {
			long loopStart = System.currentTimeMillis();
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D gfx = (Graphics2D) strategy.getDrawGraphics();
			gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			long drawStart = System.currentTimeMillis();
			Painter.paint(gfx, screen, objects.get());
			
			System.out.println("d "+(System.currentTimeMillis() - drawStart));
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			gfx.dispose();
			strategy.show();

			// sleep so that drawing + sleeping = loop time
			long loopDurationMs = System.currentTimeMillis() - loopStart;
			System.out.println("l "+loopDurationMs);
			if (loopDurationMs < LOOP_TIME_MS) {
				try { Thread.sleep(LOOP_TIME_MS - loopDurationMs); } catch (Exception e) {}
			}
		}		
	}
		
}
