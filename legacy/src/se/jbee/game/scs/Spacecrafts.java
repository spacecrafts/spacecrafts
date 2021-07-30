package se.jbee.game.scs;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.LIGHT;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.REGULAR;
import static se.jbee.game.scs.gfx.Gfx.FontStyle.THIN;
import static se.jbee.game.scs.gfx.Hue.ACADEMY;
import static se.jbee.game.scs.gfx.Hue.BIOSPHERE;
import static se.jbee.game.scs.gfx.Hue.BLACK;
import static se.jbee.game.scs.gfx.Hue.CONTROL;
import static se.jbee.game.scs.gfx.Hue.DEFAULT;
import static se.jbee.game.scs.gfx.Hue.DRIVE;
import static se.jbee.game.scs.gfx.Hue.EMPTY_SLOT;
import static se.jbee.game.scs.gfx.Hue.ENERGY;
import static se.jbee.game.scs.gfx.Hue.FARM;
import static se.jbee.game.scs.gfx.Hue.LAB;
import static se.jbee.game.scs.gfx.Hue.SCANNER;
import static se.jbee.game.scs.gfx.Hue.SHIELD;
import static se.jbee.game.scs.gfx.Hue.SPECIAL;
import static se.jbee.game.scs.gfx.Hue.TEXT_HIGHLIGHT;
import static se.jbee.game.scs.gfx.Hue.TEXT_NORMAL;
import static se.jbee.game.scs.gfx.Hue.TEXT_SPECIAL;
import static se.jbee.game.scs.gfx.Hue.TRANSPARENT;
import static se.jbee.game.scs.gfx.Hue.WEAPON;
import static se.jbee.game.scs.gfx.Hue.WHITE;
import static se.jbee.game.scs.gfx.Hue.YARD;

import java.awt.Color;
import java.awt.Toolkit;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.texture.Colouring;
import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.process.DisplayLoop;
import se.jbee.game.any.process.GameLoop;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.gfx.Hue;
import se.jbee.game.scs.gfx.ScsRenderer;
import se.jbee.game.scs.logic.Autosave;
import se.jbee.game.scs.logic.Init;
import se.jbee.game.scs.logic.Load;
import se.jbee.game.scs.logic.Loop;
import se.jbee.game.scs.logic.Next;
import se.jbee.game.scs.logic.Save;
import se.jbee.game.scs.logic.Setup;
import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.screen.Blank;
import se.jbee.game.scs.screen.Colony;
import se.jbee.game.scs.screen.Encounter;
import se.jbee.game.scs.screen.ErrorJournal;
import se.jbee.game.scs.screen.Galaxy;
import se.jbee.game.scs.screen.GlobalControls;
import se.jbee.game.scs.screen.IconInfo;
import se.jbee.game.scs.screen.LoadGame;
import se.jbee.game.scs.screen.LoadingGame;
import se.jbee.game.scs.screen.Menu;
import se.jbee.game.scs.screen.Orbit;
import se.jbee.game.scs.screen.SavingGame;
import se.jbee.game.scs.screen.SetupGame;
import se.jbee.game.scs.screen.SetupPlayer;
import se.jbee.game.scs.screen.SolarSystem;
import se.jbee.game.scs.screen.TechWheelScreen;
import se.jbee.game.scs.screen.UserSettings;
import data.Data;

/**
 * The specifics of the SPACECRAFTS game. 
 */
public class Spacecrafts implements Gfx {

	public static void main(String[] args) throws Exception {
		new GameLoop(gameLogic(), gameDisplay()).run();
	}
	
	private static Logic gameLogic() throws Exception {
		return Logic.empty()
				.add(Loop.class) // this must be the first as that is the default loop
				.add(Init.class)
				.add(Load.class)
				.add(Save.class)
				.add(Autosave.class)
				.add(Next.class)
				.add(Setup.class)
				.add(Turn.class);
	}
	
	private static DisplayLoop gameDisplay() {
		return new DisplayLoop(new ScsRenderer(), 
				initResources(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())),
				initScreens());
	}
	
	private static Screen[] initScreens() {
		return ScreenNo.Init.screens(GlobalControls.class, ErrorJournal.class, Blank.class, IconInfo.class, Menu.class, SavingGame.class, LoadGame.class, LoadingGame.class, UserSettings.class, SetupGame.class, SetupPlayer.class, Encounter.class, 
				Galaxy.class, SolarSystem.class, Orbit.class, Colony.class, TechWheelScreen.class);		
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
	
	private static Resources initResources(Dimension screen) {
		final Resources s = new Resources(Hue.class, FontStyle.class, 5, 8);
		// game texts
		s.texts.index(Data.class, "game.texts");
		s.texts.index(Data.class, "star-class.texts");
		s.texts.index(Data.class, "star-class.texts");
		s.texts.index(Data.class, "planet-class.texts");

		// base colors
		s.addColor(TRANSPARENT, 0x00000000);
		s.addColor(DEFAULT, 0xFF8899FF);
		s.addColor(WHITE, 0xFFffffff);
		s.addColor(BLACK, 0xFF000000);
		s.addColor(TEXT_HIGHLIGHT, 0xFFFFFFFF);
		s.addColor(TEXT_NORMAL, 0xFF9bcfff);
		s.addColor(TEXT_SPECIAL, 0xFFeeee77);
		s.addColor(EMPTY_SLOT, 0xFF223355);
		// components
		s.addColor(SHIELD, 0xffAAFFEE);
		s.addColor(ENERGY, 0xff00CC55);
		s.addColor(WEAPON, 0xffFF7777);
		s.addColor(CONTROL, 0xffDD8855);
		s.addColor(DRIVE, 0xffCC44CC);
		s.addColor(SPECIAL, 0xffDD8855);
		s.addColor(SCANNER, 0xff0088FF);
		// buildings
		s.addColor(ACADEMY, 0xffff8080);
		s.addColor(BIOSPHERE, 0xff80ff00);
		s.addColor(FARM, 0xffffff80);
		s.addColor(LAB, 0xff0080ff);
		s.addColor(YARD, 0xffffff00);

		s.addFont(REGULAR, "font/Orbitron-Medium.ttf");
		s.addFont(LIGHT, "font/Orbitron-Regular.ttf");
		s.addFont(THIN, "font/Orbitron-Regular.ttf");		

		s.addNoise(NOISE_STAR_LARGE, 500, 80, 777);
		s.addNoise(NOISE_STAR_SMALL, 50, 60, 700);
		s.addNoise(NOISE_PLANET_LARGE, 500, 60, 7000);
		s.addNoise(NOISE_PLANET_SMALL, 100, 40, 6000);
		s.addNoise(NOISE_PLANET_SMALL, 100, 40, 6000);
		s.addNoise(NOISE_TEST, 200, 30, 1234);

		int h = screen.height;
		s.addTexture(TEXTURE_STAR_200x2000_LARGE_RED, (Resources resources) -> { return Resources.texture(600, h, resources.noise(NOISE_STAR_LARGE), STAR_RED); });
		s.addTexture(TEXTURE_STAR_200x2000_LARGE_BLUE, (Resources resources) -> { return Resources.texture(600, h, resources.noise(NOISE_STAR_LARGE), STAR_BLUE); });
		s.addTexture(TEXTURE_STAR_200x2000_SMALL, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_STAR_SMALL), STAR); });
		s.addTexture(TEXTURE_PLANET_200x2000_LARGE, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_PLANET_LARGE), PLANET); });
		s.addTexture(TEXTURE_PLANET_200x2000_SMALL, (Resources resources) -> { return Resources.texture(200, h, resources.noise(NOISE_PLANET_SMALL), PLANET); });
		s.addTexture(TEXTURE_PLANET_600x600_LARGE, (Resources resources) -> { return Resources.texture(600, 600, resources.noise(NOISE_PLANET_LARGE), PLANET2); });
		s.addTexture(TEXTURE_PLANET_600x600_SMALL, (Resources resources) -> { return Resources.texture(600, 600, resources.noise(NOISE_PLANET_SMALL), PLANET2); });
		s.addTexture(TEXTURE_TEST, (Resources resources) ->  { return Resources.texture(300, 300, resources.noise(NOISE_TEST), (a) -> new Color(a,a,a).getRGB()); });;
		
		s.ready();
		return s;
	}	
}
