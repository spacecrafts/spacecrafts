package se.jbee.game.scs.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Renderer extends Canvas {
	
	private static final long LOOP_TIME_MS = 25; 
	
	private int mx, my;
	
	/**
	 * Construct our game and set it running.
	 */
	public Renderer() {
		JFrame container = new JFrame("Spacecraft");
		container.setExtendedState(JFrame.MAXIMIZED_BOTH);
		container.setUndecorated(true); 
		container.setLocation(0, 0);
		
		JPanel panel = (JPanel) container.getContentPane();
		Dimension screen = getToolkit().getScreenSize();
		panel.setPreferredSize(screen);
		panel.setLayout(null);
		setBounds(0,0, screen.width, screen.height);
		panel.add(this);
		
		// No AWT repaint for canvas - done manually
		setIgnoreRepaint(true);
		
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(container);
		
		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new KeyInputHandler());
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mx=e.getX();
				my=e.getY();
				
			}
		});
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
	}
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	private static void gameLoop(BufferStrategy strategy, Dimension screen, Renderer game) {
		long loopStart = System.currentTimeMillis();
		
		Random rnd = new Random();
		BufferedImage bi = new BufferedImage(5, 5,
		        BufferedImage.TYPE_INT_RGB);
		while (true) {
			loopStart = System.currentTimeMillis();
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(Color.black);
			g.fillRect(0, 0, screen.width, screen.height);
			

			Shapes.planet(g, 600, 400, 200, new Color(130,99,63));
			Shapes.planet(g, 100, 100, 500, new Color(255,80,20));
			Shapes.planet(g, 100, 600, 300, new Color(0x006600));
			
			g.setColor(Color.YELLOW);
			g.fillOval(game.mx, game.my, 20, 20);
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();

			// sleep so that drawing + sleeping = loop time
			long loopDurationMs = System.currentTimeMillis() - loopStart;
			System.out.println(loopDurationMs);
			if (loopDurationMs < LOOP_TIME_MS) {
				try { Thread.sleep(LOOP_TIME_MS - loopDurationMs); } catch (Exception e) {}
			}
		}
	}

	/**
	 * A class to handle keyboard input from the user. The class
	 * handles both dynamic input during game play, i.e. left/right 
	 * and shoot, and more static type input (i.e. press any key to
	 * continue)
	 * 
	 * This has been implemented as an inner class more through 
	 * habbit then anything else. Its perfectly normal to implement
	 * this as seperate class if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and game
	 * loop.
	 * 
	 * @param argv The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
		Renderer g =new Renderer();

		// Start the main game loop, note: this method will not
		// return until the game has finished running. Hence we are
		// using the actual main thread to run the game.
		gameLoop(g.getBufferStrategy(), g.getSize(), g);
	}
}
