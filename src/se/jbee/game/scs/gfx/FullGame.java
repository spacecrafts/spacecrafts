package se.jbee.game.scs.gfx;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * mostly kept to have some example on how to use full screen and display modes. not sure we want this.  
 */
public class FullGame {

	private static Color[] COLORS = new Color[] {
		Color.red, Color.blue, Color.green, Color.white, Color.black,
		Color.yellow, Color.gray, Color.cyan, Color.pink, Color.lightGray,
		Color.magenta, Color.orange, Color.darkGray };
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
		new DisplayMode(1280, 1024, -1, 0),
		new DisplayMode(1024, 768, -1, 0),
		new DisplayMode(1280, 720, -1, 0),
		new DisplayMode(640, 480, 32, 0),
		new DisplayMode(640, 480, 16, 0),
		new DisplayMode(640, 480, 8, 0)
	};

	Frame mainFrame;

	public FullGame(int numBuffers, GraphicsDevice device) {
		try {
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			mainFrame = new Frame(gc);
			mainFrame.setUndecorated(true);
			mainFrame.setIgnoreRepaint(true);
			device.setFullScreenWindow(mainFrame);
			if (device.isDisplayChangeSupported()) {
				chooseBestDisplayMode(device);
			}
			// add a listener to respond to the user closing the window. If they
			// do we'd like to exit the game
			mainFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			
			// add a key input system (defined below) to our canvas
			// so we can respond to key pressed
			mainFrame.addKeyListener(new KeyInputHandler());			
			
			
			Rectangle bounds = mainFrame.getBounds();
			System.out.println(bounds);
			mainFrame.createBufferStrategy(numBuffers);
			BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
			while (true) {
				long loopStart = System.currentTimeMillis();
				Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
				if (!bufferStrategy.contentsLost()) {
					g.setColor(Color.black);
					g.fillRect(0,0,bounds.width, bounds.height);

					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					GradientPaint gradient = new GradientPaint(10,10,Color.red,30,30,Color.green,true);
					g.setPaint(gradient);
					g.fillRect(100,100,1000,500);			

					// draw here
					g.setColor(new Color(170, 221, 255));
					g.drawArc(0, 0, 300, 100, 90, 180);

					for (int j = 0; j < 2000; j++) {
						//g.drawArc(-150, i, 300, 100, 90, -180);
						g.drawOval(j%800, j, 20, 20);
						//g.drawLine(0, i, 100, 100+i);
					}	                        


					bufferStrategy.show();
					long loopDurationMs = System.currentTimeMillis() - loopStart;
					System.out.println(loopDurationMs);
					g.dispose();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			device.setFullScreenWindow(null);
		}
	}
	
	private class KeyInputHandler extends KeyAdapter {
		
		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}	

	private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
		for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
			DisplayMode[] modes = device.getDisplayModes();
			for (DisplayMode m : modes) {
				System.out.println(String.format("%d %d %d %d", m.getWidth(), m.getHeight(), m.getBitDepth(), m.getRefreshRate()));
			}
			for (int i = 0; i < modes.length; i++) {
				if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
						&& modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
						&& modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()
						) {
					return BEST_DISPLAY_MODES[x];
				}
			}
		}
		return null;
	}

	public static void chooseBestDisplayMode(GraphicsDevice device) {
		DisplayMode best = getBestDisplayMode(device);
		if (best != null) {
			device.setDisplayMode(best);
		}
	}

	public static void main(String[] args) {
		try {
			int numBuffers = 2;
			if (args != null && args.length > 0) {
				numBuffers = Integer.parseInt(args[0]);
				if (numBuffers < 2 || numBuffers > COLORS.length) {
					System.err.println("Must specify between 2 and "
							+ COLORS.length + " buffers");
					System.exit(1);
				}
			}
			GraphicsEnvironment env = GraphicsEnvironment.
					getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();
			FullGame test = new FullGame(numBuffers, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}

