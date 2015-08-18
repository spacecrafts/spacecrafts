package se.jbee.game.scs.process;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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

import se.jbee.game.common.gfx.Colouring;
import se.jbee.game.common.gfx.Renderer;
import se.jbee.game.common.gfx.Styles;
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
		final Styles styles = initStyles();
		final Renderer renderer = new Renderer1();
		int frameDone = -1;
		while (true) {
			long loopStart = System.currentTimeMillis();

			Stage currentStage = stage;
			if (currentStage != null) {
				int frame = currentStage.frame();
				if (frameDone != frame) { // only draw if something has changed
					frameDone = frame;

					Graphics2D gfx = (Graphics2D) strategy.getDrawGraphics();
					renderer.render(currentStage, screen, styles, gfx);
					gfx.dispose();
					strategy.show();
				}
			}

			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < FRAME_DELAY_MS) {
				try { Thread.sleep(FRAME_DELAY_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}		
	}

	private static final Colouring STAR = (float rgb) -> { 
		float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
		Color c = new Color(1f, rgb, rgb/2f, a); 
		return c.getRGB();
	};
	
	private static final Colouring PLANET = (float rgb) -> { 
		return new Color(rgb, rgb, rgb, 0.25f).getRGB();
	};
	
	private static final Colouring PLANET2 = (float rgb) -> { 
		return new Color(rgb, rgb, rgb, 0.15f).getRGB();
	};
	
	private Styles initStyles() {
		final Styles s = new Styles(5, 3, 4, 6);
		s.addColor(0, 0xFF8899FF);
		s.addColor(COLOR_TEXT_HIGHLIGHT, 0xFFFFFFFF);
		s.addColor(COLOR_TEXT_NORMAL, 0xFF8899FF);
		s.addColor(COLOR_TEXT_SPECIAL, 0xFFeeee77);
		s.addColor(COLOR_SLOT_BORDER, 0xFF223355); 
		
		s.addFont(FONT_REGULAR, "font/Roboto-Regular.ttf");
		s.addFont(FONT_LIGHT, "font/Roboto-Light.ttf");
		s.addFont(FONT_THIN, "font/Roboto-Thin.ttf");
		
		s.addNoise(NOISE_STAR_LARGE, 500, 80, 666);
		s.addNoise(NOISE_STAR_SMALL, 50, 60, 700);
		s.addNoise(NOISE_PLANET_LARGE, 500, 60, 7000);
		s.addNoise(NOISE_PLANET_SMALL, 100, 40, 6000);

		s.addTexture(TEXTURE_STAR_200x2000_LARGE, (Styles styles) -> { return Styles.texture(200, 2000, styles.noise(NOISE_STAR_LARGE), STAR); });
		s.addTexture(TEXTURE_STAR_200x2000_SMALL, (Styles styles) -> { return Styles.texture(200, 2000, styles.noise(NOISE_STAR_SMALL), STAR); });
		s.addTexture(TEXTURE_PLANET_200x2000_LARGE, (Styles styles) -> { return Styles.texture(200, 2000, styles.noise(NOISE_PLANET_LARGE), PLANET); });
		s.addTexture(TEXTURE_PLANET_200x2000_SMALL, (Styles styles) -> { return Styles.texture(200, 2000, styles.noise(NOISE_PLANET_SMALL), PLANET); });
		s.addTexture(TEXTURE_PLANET_600x600_LARGE, (Styles styles) -> { return Styles.texture(600, 600, styles.noise(NOISE_PLANET_LARGE), PLANET2); });
		s.addTexture(TEXTURE_PLANET_600x600_SMALL, (Styles styles) -> { return Styles.texture(600, 600, styles.noise(NOISE_PLANET_SMALL), PLANET2); });
		
		s.ready();
		return s;
	}

}
