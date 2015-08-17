package se.jbee.game.scs.gfx;

/**
 * Contains all the type-like constants used in the context of game specific
 * graphics.
 */
public interface Gfx {

	/**
	 * Fonts
	 */
	int
	FONT_REGULAR = 0,
	FONT_LIGHT = 1,
	FONT_THIN = 2,
	FONT_DOTS = 3;
	
	/**
	 * Colors
	 */
	int
	COLOR_TEXT_HIGHLIGHT = 1,
	COLOR_TEXT_NORMAL = 2,
	COLOR_TEXT_SPECIAL = 3,
	COLOR_SLOT_BORDER = 4;
	
	/**
	 * Backgrounds
	 */
	int
	BG_BLACK = 0,
	BG_SPACE = 1;
	
	/**
	 * Noises
	 */
	int
	NOISE_STAR_LARGE = 0,
	NOISE_STAR_SMALL = 1,
	NOISE_PLANET_LARGE = 2,
	NOISE_PLANET_SMALL = 3;
	
	/**
	 * Objects
	 */
	int
	OBJ_BACKGROUND = 1,
	
	OBJ_BORDER = 20,
	OBJ_FOCUS_LINE = 21,
	OBJ_FOCUS_BOX = 22,
	
	OBJ_TEXT = 30,
	
	OBJ_STAR = 40,
	OBJ_STAR_CLIP = 41,
	OBJ_PLANET = 42,
	OBJ_PLANET_CLIP = 43,
	OBJ_ORBIT_ARC = 44,
	OBJ_ROUTE = 45,
	
	OBJ_SLOT = 50,
	OBJ_STRUCTURE = 51,
	OBJ_RESOURCE = 52,
	OBJ_ROCK = 53
	
	;
}
