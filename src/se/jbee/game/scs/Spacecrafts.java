package se.jbee.game.scs;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Toolkit;

import se.jbee.game.any.gfx.Colouring;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.any.gfx.obj.Text;
import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.process.Display;
import se.jbee.game.any.process.Game;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
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
		new Game(gameLogic(), gameDisplay()).run();
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
	
	private static Display gameDisplay() {
		return new Display(new GfxRenderer(), 
				initResources(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())),
				initScreens());
	}
	
	private static Screen[] initScreens() {
		return ScreenNo.Init.screens(GlobalControls.class, ErrorJournal.class, Blank.class, IconInfo.class, Menu.class, SavingGame.class, LoadGame.class, LoadingGame.class, UserSettings.class, SetupGame.class, SetupPlayer.class, Encounter.class, 
				Galaxy.class, SolarSystem.class, Orbit.class, Colony.class, TechWheelScreen.class);		
	}
	
//	private static GfxRenderer initRenderer() {
//		GfxRenderer r = new GfxRenderer();
//		r.register(OBJ_TEXT, new Text());
//		r.register(OBJ_TECH_WHEEL, new Techwheel());
//		r.register(OBJ_BUTTON, new Button());
//		r.register(OBJ_RING, new Ring());
//		r.register(OBJ_BACKGROUND, new Background());
//		r.register(OBJ_STAR, Star.FULL);
//		r.register(OBJ_STAR_CLIP, Star.CUT);
//		r.register(OBJ_PLANET, Planet.FULL);
//		r.register(OBJ_PLANET_CLIP, Planet.CUT);
//		r.register(OBJ_ICON, new Icon());
//		r.register(OBJ_RECT, new Rect());
//		r.register(OBJ_PATH, new Path());
//		return r;
//	}
	
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
		s.addColor(COLOR_TEXT_NORMAL, 0xFF9bcfff);
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

//		s.addFont(FONT_REGULAR, "font/Roboto-Regular.ttf");
//		s.addFont(FONT_LIGHT, "font/Roboto-Light.ttf");
//		s.addFont(FONT_THIN, "font/Roboto-Thin.ttf");
		
//		s.addFont(FONT_REGULAR, "font/MavenPro-Medium.ttf");
//		s.addFont(FONT_LIGHT, "font/MavenPro-Regular.ttf");
//		s.addFont(FONT_THIN, "font/MavenPro-Regular.ttf");
		
		s.addFont(FONT_REGULAR, "font/Orbitron-Medium.ttf");
		s.addFont(FONT_LIGHT, "font/Orbitron-Regular.ttf");
		s.addFont(FONT_THIN, "font/Orbitron-Regular.ttf");		

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
}
