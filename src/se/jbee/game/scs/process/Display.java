package se.jbee.game.scs.process;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.jbee.game.common.gfx.Palette;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.process.Scene;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Renderer1;

/**
 * The screen or canvas the game is drawn on.
 * 
 * This is a thread continuously transforming abstract figures to concrete draw
 * instructions executed on the canvas.
 */
public class Display extends Canvas implements Runnable, Gfx {

	private static final long FRAME_DELAY_MS = 15;
	
	private final Scene scene; 

	public Display(Scene scene, KeyListener onKey, MouseListener onMouseClick, MouseMotionListener onMouseMove) {
		super();
		this.scene = scene;

		JFrame frame = new JFrame("SPACECRAFTS");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true); 
		frame.setLocation(0, 0);
		
		JPanel panel = (JPanel) frame.getContentPane();
		Dimension screen = getToolkit().getScreenSize();
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
		
		//disableEvents(eventsToDisable);
		addKeyListener(onKey);
		addMouseListener(onMouseClick);
		addMouseMotionListener(onMouseMove);
		
		
		requestFocus(); // request the focus so key events come to us
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		createBufferStrategy(2);
	}

	@Override
	public void run() {
		final BufferStrategy strategy = getBufferStrategy();
		final Dimension screen = getSize();
		final Renderer renderer = new Renderer1();
		final Palette palette = initPalette();
		while (true) {
			long loopStart = System.currentTimeMillis();
			Graphics2D gfx = (Graphics2D) strategy.getDrawGraphics();
			
			renderer.render(scene, screen, palette, gfx);
			
			gfx.dispose();
			strategy.show();

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < FRAME_DELAY_MS) {
				try { Thread.sleep(FRAME_DELAY_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}		
	}

	private Palette initPalette() {
		Color[] colors = { new Color(0x8899FF), Color.WHITE, new Color(0x8899FF), new Color(238, 238, 119) }; 
		Font[][] fonts = new Font[2][64];
		fonts[FONT_REGULAR][0] = Palette.loadFont("font/Roboto-Regular.ttf");
		fonts[FONT_LIGHT][0] = Palette.loadFont("font/Roboto-Light.ttf");
		final Palette palette = new Palette(colors, fonts);
		return palette;
	}
		
}
