package se.jbee.game.scs.state;

import se.jbee.game.any.state.Component;

/**
 * A list of ALL the components used in the game.
 *
 * This is essentially a long list of all "things" and their properties that
 * exist in the game.
 *
 * !!! ATTENTION !!!
 * The most important part is to NEVER change the value of a component as soon
 * as there is any code using it. Each number stands for the semantics of the
 * thing or property. Changing the number does change the meaning of stored game
 * state!
 */
public interface GameComponent extends Component {

	// 0-4 are defined in Component

	// * = pointer to (ID of another entity)
	// # = number (of) (a numeric value)
	// ? = boolean (1=true, 0=false)
	// [...] = list
	// {...} = set

	/**
	 * Actions
	 */
	int
	// (AI threads should be terminated)
	ACTION_EXIT = 0,
	ACTION_ERROR = 1,
	ACTION_SAVE = 2,
	ACTION_AUTOSAVE = 3,
	ACTION_LOAD = 4,
	ACTION_SETUP = 5, // creates players in a currently setup game
	ACTION_MOVE_AI = 6,
	ACTION_QUIT_AI = 7,
	// (AI threads might be active)
	ACTION_NEXT_TASK = 10,  // player is done with an entity; next screen derived from player status
	ACTION_NEXT_PLAN = 11,  // player is done with a plan; next screen derived from player status
	ACTION_DONE = 12        // (next player) player is done (with turn); next screen derived from game status
	;

	/**
	 * Setup (Indexes)
	 */
	int
	// game setup
	SETUP_NUMBER_OF_PLAYERS = 0,
	SETUP_NUMBER_OF_AIS = 1,
	SETUP_GALAXY_SIZE = 2,
	// player setup
	SETUP_PLAYER_NAME_TYPE = 0;

	/**
	 * Components
	 */
	int
	/*
	 * Game and Players
	 */
	GAME = 10,
		// game as an entity
		SEED = 11,
		SEEDS = 12,   // [#,#,...] for any pseudo random progression
		TURN = 13, // # (0 = game setup, 1 is first active turn)
		SETUP = 14, // [#humans, #ais, #galaxy-size, ...]

		// view control
		SCREEN = 20,
		BASE_ENTITY = 21, // {*a,..} ( what; galaxy; e.g. the colony in the colony screen, the galaxy in the galaxy screen; the appearance of each screen might be linked to a set of entities; their type is usually an expected one)
		RETURN_SCREEN = 22, // the screen one goes back to from e.g. load/save dialog screen
		PAGE = 23, // # (used for paging - e.g. on load screen)
		CURSOR = 24, // similar to the tab stop in the browser - each view makes sense of the number given, if it is too big it does a modulo so that any number has a position (if there is cursor support)
		RESOLUTION = 25, // [#w, #h]		

		// action state
		ACTION = 30, // like 0 = exit, 1 = save
		SAVEGAME_DIR = 31, // the directory where to store savegames
		SAVEGAME = 32, // name of the game to save/saved
		RENAME = 33, // [*entity, *comp, #font, #size, #x, #y, #w, #h]
		ACTIVE_PLAYERS = 34, // [*x,*y,*z] players that are still doing something (AI with thread or human)

		// domain data
		PLAYERS = 41, // [*x,*y,*z]
		GALAXIES = 42,// [*x,*y,*z]

	PLAYER = 50,
		// TURN (player sets its turn to the current game turn when finished; in turn zero TURN set to -1 until player has finished setup)
		NO = 51, //#
		// SEEDS [#,#,...]
		// RACE *x
		HOME = 52, // *x (planet)
		STATUS = 53, // [?AI, ?ALIVE]
		REACH = 54, // # the maximum distance the player knows how to travel
		// TECHNOLOGY (in progress)
		// (things belong to the player)
		PLANS = 61,
		RELATIONS = 62, // [#,#,...] (the diplomatic points for each player, in order of game players)
		TECHNOLOGIES = 63, // {*x,*y,*z} (researched)
		ABILITIES = 64, // {*x,*y,*z} (gained from technology, etcetera)
		COLONIES = 65, // {*x,*y,*z}
		ORBITS = 66, // {*x,*y,*z}
		FLEETS = 67, // {*x,*y,*z}
		OFFERS = 68, // {*x,*y,*z}
		// (things known to (/discovered by) the player)
		// PLANETS {*x,*y,*z}
		// SOLAR_SYSTEMS {*x,*y,*z}
		// CLUSTERS {*x,*y,*z}
		// GALAXIES {*x,*y,*z}
		// SHIPS {*x,*y,*z} (of other players)
		// MODULES {*x,*y,*z}
		// COMPONENTS {*x,*y,*z} (what can be used by a player)
		//-- AI only --
		UNHANDLED = 71, // [*x, *y, *z] a list of entities that should be managed during the current turn 

	PLAN = 80,
		PARTICIPANTS = 71, // {*x,*y,... } (a set of colonies, orbits, space-crafts, constructions) that belong to the plan

	MISSION = 90,

	/*
	 * Galaxy
	 */
	GALAXY = 100,
		// SEEDS [#,#,...]
		CLUSTERS = 101, // {*x,*y,*z}
		SIZE = 102, // [#x,#y,#z] side length of x, y and z axis in value space

	CLUSTER = 110,
		// SEEDS [#,#,...]
		STARS = 111, // {*x,*y,*z}
		POSITION = 112, // [#x,#y, #z] (in the galaxy; say there is a plane through the center of a cluster/galaxy the z-axis is how much above or below that plane a system is)

	STAR = 120,
		// SEEDS [#,#,...]
		PLANETS = 121, // {*x,*y,*z} (orbiting)
		// POSITION (in the cluster)
		// SIZE [#] (8-16)
		//FLEETS = {*x,*y,*z} (set of all the fleets arrived/staying in a solar system)
		MASS = 122,
		/// STAR_CLASS *x
		RGB = 123, //[#] (an int, bytes as follows RRGGBB; derived from SEED, but to be save against algorithm changes)
		CLOSEST = 124, // distance to the closest other star

	PLANET = 130,
		// RGB
		// SEEDS [#,#,...]
		// PLANET_CLASS *x
		// WEALTH *x
		// COLONY *y (cross ref)
		// POSITION (in the solar system)
		MOONS = 131, // (orbiting)

	MOON = 140,
		// PLANET

	STAR_CLASS = 150,
		// CODE
		// RGB (typical; closest match to actual color determines type)
		// SIZE [#] (~8-30=small-huge)
		ABUNDANCE = 152, // [#%] at what chance does it occur

	PLANET_CLASS = 160, // of a planet; or a "planet type" (mostly a description for humans)
		//CODE (a static constant value known to gfx to draw the appropriate image, random values from planet are used to make each appear somewhat unique
		// RGB (typical, see star types)
		// ABUNDANCE
		ATMOSPHERE = 161,
		SUFRACE = 162,
		ZONE = 163, // (hot/cold/Ecosphere/rogue)
		LIFE = 164, // how suitable it the planet for colonization ( high temp, high pressure, etc)
		MATERIALS = 165, // [#:#,*y,...] map: material(code) => prop

	WEALTH = 170, // of rare materials on a planet (mostly a description for humans)
		RATE = 171, // # (percentage of depots in relation to all planet slots)

	MATERIAL = 180, // (mostly a description for humans)
		//CODE

	/*
	 * Diplomacy and Trading
	 */
	OFFER = 200,
		// PLANET *x
		// SOLAR_SYSTEMS {*x,*y,*z} (where it is offered)
		QUANTITY = 201, // #
		TURNS = 202, // # (duration)
		PRICE = 203,

	PERCEPTION = 210, // like friendly, angry, enemy, ally
		RANGE = 211, // [#min, #max] (of diplomacy points)

	/*
	 * Technology and Abilities
	 */
	TECHNOLOGY = 300,
		//CODE
		//COMPONENTS {#x,#y,...} (what becomes known to the player when discovered given by a component-code)
		LEVEL = 301, // # (0-5; 0 is in the center)
		//BRANCH # (the POSITION of the BRANCH)
		//POSITION # (in the ring-branch 0-4)

	BRANCH = 310,
		//POSITON # (position in the wheel; 0-7)

	ABILITY = 320,

	/*
	 * Colonies and Orbits
	 */
	COLONY = 400,
		// PLANET *x
		// ORBIT *y
		// STRUCTURE *z

	ORBIT = 410,
		// PLANET *x
		// STRUCTURE *x

	/*
	 * Space-crafts
	 */
	SPACECRAFT = 500,
		// STRUCTURE *x
		// CLASS *x
		// POSITION

	CLASS = 510,
		CHARACTERISTICS = 511, // [#,#,#,..] (ideal number of properties a spacecraft needs to have to be of a certain class, like lasers, shield strength, crew; closest matches)

	FLEET = 520,
		SHIPS = 521, // [*x,*y,*z]
		// POSITION
		TARGET = 522, // *x (the star they are heading)

	COMMANDER = 530,

	/*
	 * Structures, Modules, Components and Constructions
	 */
	STRUCTURE = 600, // a compound of modules
		LOCATION = 601,  // *x (a reference back to a planet, orbit or space-craft)
		MODULES = 602, // [*x,*y,*z]
		OFFSETS = 603, // (for each module, also relative in 32x32 grid - 1D array)
		POPULATION = 604,
		WORKERS = 605, // [ #farmers, #scientists, #engineers ]
		OUTPUTS = 606, // [ #food, #research-points, #production-points ] (per turn)
		COSTS = 607,// # (total construction points required)

	MODULE = 610,
		POSITIONS = 611, // [#,#,...] absolute positions in the 32x32 grid (1D array)
		COMPONENTS = 612, // [*x,*y,...] references to the part entities
		// COSTS # (total construction points required)

	COMPONENT = 620,
		// CODE # a statically unique value for all components (e.g. used to refer to a part from technology)
		// COSTS # (of construction points required)
		// TODO effects (pairs of [what, how; or: given, then] e.g. [shields, +5]) // or is this only in the code ?

	CONSTRUCTION = 630,
		//MODULE *x (structures are build module wise)
		COMPLETIONS = 631, // [#,#,..] (points for each module in the unit)

	/*
	 * Race and Traits
	 */
	RACE = 700,
		TRAITS = 701,

	TRAIT = 710
		//CODE
		//ABILITIES {*x,*y,..}
	;
}
