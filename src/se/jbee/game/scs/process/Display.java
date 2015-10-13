package se.jbee.game.scs.process;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
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

import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Renderer1;
import se.jbee.game.scs.gfx.obj.Background;
import se.jbee.game.scs.gfx.obj.Border;
import se.jbee.game.scs.gfx.obj.FocusBox;
import se.jbee.game.scs.gfx.obj.Icon;
import se.jbee.game.scs.gfx.obj.Knob;
import se.jbee.game.scs.gfx.obj.Line;
import se.jbee.game.scs.gfx.obj.Planet;
import se.jbee.game.scs.gfx.obj.Ring;
import se.jbee.game.scs.gfx.obj.Star;
import se.jbee.game.scs.gfx.obj.Techwheel;
import se.jbee.game.uni.gfx.Colouring;
import se.jbee.game.uni.gfx.Dimension;
import se.jbee.game.uni.gfx.Renderer;
import se.jbee.game.uni.gfx.Stage;
import se.jbee.game.uni.gfx.Styles;
import se.jbee.game.uni.gfx.obj.Text;

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
		final Dimension screen = new Dimension(getSize());
		final Styles styles = initStyles(screen);
		final Renderer renderer = initRenderer();
		int frameDone = -1;
		boolean onlyDrawOnChange = false;
		while (true) {
			long loopStart = System.currentTimeMillis();

			Stage currentStage = stage;
			if (currentStage != null) {
				int frame = currentStage.frame();
				if (!onlyDrawOnChange || frameDone != frame) { // only draw if something has changed
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
				try { Thread.sleep(FRAME_DELAY_MS - cycleTimeMs); } catch (Exception e) {
					// just go on
				}
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

	private Renderer initRenderer() {
		Renderer1 r = new Renderer1();
		r.assoc(OBJ_TEXT, new Text());
		r.assoc(OBJ_TECH_WHEEL, new Techwheel());
		r.assoc(OBJ_KNOB, new Knob());
		r.assoc(OBJ_RING, new Ring());
		r.assoc(OBJ_BACKGROUND, new Background());
		r.assoc(OBJ_STAR, Star.CIRCLE);
		r.assoc(OBJ_STAR_CLIP, Star.CLIP);
		r.assoc(OBJ_PLANET, Planet.CIRCLE);
		r.assoc(OBJ_PLANET_CLIP, Planet.CLIP);
		r.assoc(OBJ_ICON, new Icon());
		r.assoc(OBJ_BORDER, new Border());
		r.assoc(OBJ_FOCUS_BOX, new FocusBox());
		r.assoc(OBJ_FOCUS_LINE, new Line());
		r.assoc(OBJ_TIME_LINE, new Line());
		return r;
	}

	private Styles initStyles(Dimension screen) {
		final Styles s = new Styles(26, 3, 4, 6);
		s.addColor(COLOR_TRANSPARENT, 0x00000000);
		s.addColor(COLOR_DEFAULT, 0xFF8899FF);
		s.addColor(COLOR_WHITE, 0xFFffffff);
		s.addColor(COLOR_BLACK, 0xFF000000);
		s.addColor(COLOR_TEXT_HIGHLIGHT, 0xFFFFFFFF);
		s.addColor(COLOR_TEXT_NORMAL, 0xFF8899FF);
		s.addColor(COLOR_TEXT_SPECIAL, 0xFFeeee77);
		s.addColor(COLOR_SLOT, 0xFF223355);
		// components
		s.addColor(COLOR_SHIELD, 0xffAAFFEE);
		s.addColor(COLOR_ENERGY, 0xff00CC55);
		s.addColor(COLOR_WEAPON, 0xffFF7777);
		s.addColor(COLOR_CONTROL, 0xffDD8855);
		s.addColor(COLOR_DRIVE, 0xffCC44CC);
		s.addColor(COLOR_SPECIAL, 0xffDD8855);
		s.addColor(COLOR_SCANNER, 0xff0088FF);
		// buildings
		s.addColor(COLOR_ACADEMY, 0xffff8080);
		s.addColor(COLOR_BIOSPHERE, 0xff80ff00);
		s.addColor(COLOR_FARM, 0xffffff80);
		s.addColor(COLOR_LAB, 0xff0080ff);
		s.addColor(COLOR_YARD, 0xffffff00);

		s.addFont(FONT_REGULAR, "font/Roboto-Regular.ttf");
		s.addFont(FONT_LIGHT, "font/Roboto-Light.ttf");
		s.addFont(FONT_THIN, "font/Roboto-Thin.ttf");

		s.addNoise(NOISE_STAR_LARGE, 500, 80, 666);
		s.addNoise(NOISE_STAR_SMALL, 50, 60, 700);
		s.addNoise(NOISE_PLANET_LARGE, 500, 60, 7000);
		s.addNoise(NOISE_PLANET_SMALL, 100, 40, 6000);

		int h = screen.height;
		System.out.println("Init textures "+h);
		s.addTexture(TEXTURE_STAR_200x2000_LARGE, (Styles styles) -> { return Styles.texture(200, h, styles.noise(NOISE_STAR_LARGE), STAR); });
		s.addTexture(TEXTURE_STAR_200x2000_SMALL, (Styles styles) -> { return Styles.texture(200, h, styles.noise(NOISE_STAR_SMALL), STAR); });
		s.addTexture(TEXTURE_PLANET_200x2000_LARGE, (Styles styles) -> { return Styles.texture(200, h, styles.noise(NOISE_PLANET_LARGE), PLANET); });
		s.addTexture(TEXTURE_PLANET_200x2000_SMALL, (Styles styles) -> { return Styles.texture(200, h, styles.noise(NOISE_PLANET_SMALL), PLANET); });
		s.addTexture(TEXTURE_PLANET_600x600_LARGE, (Styles styles) -> { return Styles.texture(600, 600, styles.noise(NOISE_PLANET_LARGE), PLANET2); });
		s.addTexture(TEXTURE_PLANET_600x600_SMALL, (Styles styles) -> { return Styles.texture(600, 600, styles.noise(NOISE_PLANET_SMALL), PLANET2); });

		s.ready();
		return s;
	}

}
