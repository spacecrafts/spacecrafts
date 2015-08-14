package se.jbee.game.scs.process;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.jbee.game.common.gfx.Palette;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.gfx.SimplexNoise;
import se.jbee.game.common.process.Stage;
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
	
	private Stage stage; 

	public Display() { 
		super();
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
		
		requestFocus(); // request the focus so key events come to us

		createBufferStrategy(2);
	}
	
	public <T extends KeyListener & MouseMotionListener & MouseListener> void setInputHandler(T handler) {
		//disableEvents(eventsToDisable);
		addKeyListener(handler);
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void run() {
		final BufferStrategy strategy = getBufferStrategy();
		final Dimension screen = getSize();
		final Renderer renderer = new Renderer1();
		final Palette palette = initPalette();
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		while (true) {
			long loopStart = System.currentTimeMillis();
			Graphics2D gfx = (Graphics2D) strategy.getDrawGraphics();
			
			Stage currentStage = stage;
			if (currentStage != null) {
				renderer.render(currentStage, screen, palette, gfx);
			}
			
			gfx.dispose();
			strategy.show();
			toolkit.sync();

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
		Supplier<BufferedImage>[] backgrounds = (Supplier<BufferedImage>[]) new Supplier<?>[] { 
			() -> { return SimplexNoise.image(200, 2000, 500, 80, 666, 0.15f, 0f); },
			() -> { return SimplexNoise.image(200, 2000, 50, 60, 700, 0.15f, 0f); },
			() -> { return SimplexNoise.image(500, 500, 500, 60, 7000, 0.15f, 0f); },
			() -> { return SimplexNoise.image(500, 500, 100, 40, 6000, 0.15f, 0f); }
		};
		final Palette palette = new Palette(colors, fonts, backgrounds);
		return palette;
	}
		
}
