package se.jbee.game.scs.process;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Canvas;
import java.awt.Color;
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

import se.jbee.game.any.gfx.Colouring;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.gfx.Stage.AreaMapping;
import se.jbee.game.any.gfx.Stage.Hover;
import se.jbee.game.any.gfx.Stage.KeyMapping;
import se.jbee.game.any.gfx.obj.Text;
import se.jbee.game.any.state.Change;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.GfxRenderer;
import se.jbee.game.scs.gfx.obj.Background;
import se.jbee.game.scs.gfx.obj.Button;
import se.jbee.game.scs.gfx.obj.Icon;
import se.jbee.game.scs.gfx.obj.Path;
import se.jbee.game.scs.gfx.obj.Planet;
import se.jbee.game.scs.gfx.obj.Rect;
import se.jbee.game.scs.gfx.obj.Ring;
import se.jbee.game.scs.gfx.obj.Star;
import se.jbee.game.scs.gfx.obj.Techwheel;
import data.Data;

/**
 * The screen or canvas the game is drawn on.
 *
 * This is a thread continuously transforming abstract figures to concrete draw
 * instructions executed on the canvas.
 */
public class UI extends Canvas implements Runnable, Gfx, KeyListener, MouseListener, MouseMotionListener {

	private static final long FRAME_DELAY_MS = 15;

	private Stage stage = new Stage();

	public UI() {
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
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);		
	}
	
	public Stage getStage() {
		return stage;
	}

	@Override
	public void run() {
		final BufferStrategy strategy = getBufferStrategy();
		final Dimension screen = new Dimension(getSize());
		final Resources styles = initStyles(screen);
		final GfxRenderer renderer = initRenderer();
		int frameDone = -1;
		boolean onlyDrawOnChange = true;
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

	private static final Colouring STAR_RED = (float rgb) -> {
		float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
		Color c = new Color(1f, rgb, rgb/2f, a/1.4f);
		return c.getRGB();
	};

	private static final Colouring STAR_BLUE = (float rgb) -> {
		float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
		Color c = new Color(rgb/2f, rgb, 1f, a/1.4f);
		return c.getRGB();
	};

	private static final Colouring STAR = (float rgb) -> {
		float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
		Color c = new Color(rgb, rgb-rgb/4, rgb-rgb/5, a/1.4f);
		return c.getRGB();
	};

	private static final Colouring PLANET = (float rgb) -> {
		return new Color(rgb, rgb, rgb, rgb/4).getRGB();
	};

	private static final Colouring PLANET2 = (float rgb) -> {
		return new Color(rgb, rgb, rgb, 0.10f).getRGB();
	};

	private static final Colouring CLOUDS = (float rgb) -> {
		return  rgb > 0.4f
				? new Color(rgb, rgb, rgb, rgb/2).getRGB()
				: new Color(rgb, rgb, rgb, 0f).getRGB();
	};

	private static final Colouring CRATARS = (float rgb) -> {
		return  rgb > 0.55f && rgb < 0.6f
				? new Color(0.3f, 0.4f, 0.3f, (1f-rgb)/3f).getRGB()
				: rgb > 0.6f && rgb < 0.8f
					? new Color(0.2f, 0.3f, 0.2f, (1f-rgb)/3f).getRGB()
					: new Color(rgb, rgb, rgb, 0f).getRGB();
	};

	private GfxRenderer initRenderer() {
		GfxRenderer r = new GfxRenderer();
		r.register(OBJ_TEXT, new Text());
		r.register(OBJ_TECH_WHEEL, new Techwheel());
		r.register(OBJ_BUTTON, new Button());
		r.register(OBJ_RING, new Ring());
		r.register(OBJ_BACKGROUND, new Background());
		r.register(OBJ_STAR, Star.FULL);
		r.register(OBJ_STAR_CLIP, Star.CUT);
		r.register(OBJ_PLANET, Planet.FULL);
		r.register(OBJ_PLANET_CLIP, Planet.CUT);
		r.register(OBJ_ICON, new Icon());
		r.register(OBJ_RECT, new Rect());
		r.register(OBJ_PATH, new Path());
		return r;
	}

	private Resources initStyles(Dimension screen) {
		final Resources s = new Resources(26, 3, 4, 7);
		// game texts
		s.texts.index(Data.class, "game.texts");
		s.texts.index(Data.class, "star-class.texts");
		s.texts.index(Data.class, "star-class.texts");
		s.texts.index(Data.class, "planet-class.texts");

		// base colors
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
		s.addTexture(TEXTURE_STAR_200x2000_LARGE_RED, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_STAR_LARGE), STAR_RED); });
		s.addTexture(TEXTURE_STAR_200x2000_LARGE_BLUE, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_STAR_LARGE), STAR_BLUE); });
		s.addTexture(TEXTURE_STAR_200x2000_SMALL, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_STAR_SMALL), STAR); });
		s.addTexture(TEXTURE_PLANET_200x2000_LARGE, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_PLANET_LARGE), PLANET); });
		s.addTexture(TEXTURE_PLANET_200x2000_SMALL, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_PLANET_SMALL), PLANET); });
		s.addTexture(TEXTURE_PLANET_600x600_LARGE, (Resources resources) -> { return Resources.texture(600, 600, resources.noise(NOISE_PLANET_LARGE), PLANET2); });
		s.addTexture(TEXTURE_PLANET_600x600_SMALL, (Resources resources) -> { return Resources.texture(600, 600, resources.noise(NOISE_PLANET_SMALL), PLANET2); });

		s.ready();
		return s;
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
	public void keyReleased(KeyEvent e) { /* not used */ }
	
	
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
		stage.highlight(Collections.<int[]>emptyList());
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
	public void keyPressed(KeyEvent e) {
		if (e.getID() != KeyEvent.KEY_RELEASED || !stage.isReadyForInputs())
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
