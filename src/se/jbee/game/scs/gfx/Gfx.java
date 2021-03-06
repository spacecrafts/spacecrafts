package se.jbee.game.scs.gfx;

/**
 * Contains all the type-like constants used in the context of game specific
 * graphics.
 */
public interface Gfx {

	/**
	 * Fonts Styles
	 */
	enum FontStyle { 
		REGULAR, LIGHT, THIN, DOTS	
	}
	
	/**
	 * Text Alignment
	 */
	enum Align {
		N, NE, E, SE, S, SW, W, NW, EYE, HCENTER // x,y describe the center point
	}
	
	/**
	 * Outline Styles
	 */
	int
	PATH_EDGY = 1;

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
	NOISE_PLANET_SMALL = 3,
	NOISE_TEST = 4;

	/**
	 * Textures
	 */
	int
	TEXTURE_STAR_200x2000_LARGE_RED = 0,
	TEXTURE_STAR_200x2000_LARGE_BLUE = 1,
	TEXTURE_STAR_200x2000_SMALL = 2,
	TEXTURE_PLANET_200x2000_LARGE = 3,
	TEXTURE_PLANET_200x2000_SMALL = 4,
	TEXTURE_PLANET_600x600_LARGE = 5,
	TEXTURE_PLANET_600x600_SMALL = 6,
	TEXTURE_TEST = 7;

	/**
	 * Objects
	 * (this might be changed at any time as they do not occur in the game data - just in rendering)
	 */
	int
	// backs
	OBJ_BACKGROUND = 1,
	OBJ_STAR = 2,
	OBJ_PLANET = 3,
	OBJ_STAR_CLIP = 4,
	OBJ_PLANET_CLIP = 5,
	OBJ_TECH_WHEEL = 6,

	// figures
	OBJ_PATH = 10,
	OBJ_RECT = 11,
	OBJ_RING = 12,

	// information
	OBJ_TEXT = 20,
	OBJ_ICON = 21,

	// input elements
	OBJ_BUTTON = 30
	;

	/**
	 * Icons
	 *
	 * (Icons do match most of the actual technologies/components but
	 * conceptually a component refers to its icon and color)
	 */
	int 
	ICON_DAMPING_FIELD = 1,
	ICON_FORCE_FIELD = 2,
	ICON_CLOAKING_FIELD = 3,
	ICON_PARTICLE_FIELD = 4,
	ICON_DEFLECTOR_FIELD = 5,
	ICON_FIELD_CAPACITOR = 6,
	ICON_IMPULSE_GENERATOR = 7,

	ICON_STASIS_FIELD = 10,
	ICON_LASER = 11,
	ICON_MASS_GUN = 12,
	ICON_ROCKET = 13,
	ICON_PLASMA_TORPEDO = 14,
	ICON_DISRUPTOR = 15,
	ICON_TURBO = 16,
	ICON_TRACTOR = 17,
	ICON_PHASER = 18,
	ICON_PLASMA_CANNON = 19,
	ICON_PARTICLE_CANNON = 20,

	ICON_IMPULSE_DRIVE = 21,
	ICON_WARP_DRIVE = 22,

	ICON_REACTOR1 = 31,
	ICON_REACTOR2 = 32,
	ICON_REACTOR3 = 33,
	ICON_REACTOR4 = 34,
	ICON_REACTOR5 = 35,
	ICON_ENERGY = 36,
	ICON_ENERGY_OFF = 37,
	ICON_TRANSPORTER = 38,

	ICON_SELF_DESTRUCT = 40,
	ICON_REPAIR_DRONE = 41,
	ICON_TARGET_COMPUTER = 42,
	ICON_JAMMER = 43,

	ICON_SLOT = 50,
	ICON_BUILDING = 51,
	ICON_RESOURCE = 52,
	ICON_ROCK = 53

	;
}
