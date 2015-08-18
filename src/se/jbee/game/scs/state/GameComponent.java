package se.jbee.game.scs.state;

import se.jbee.game.common.state.Component;

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

	// 0-3 are defined in Component

	// value component ant its possible values (also type like entities)
	int VAL  = 4, /*=*/ NUM = 5, TXT = 6, SET = 7, LIST = 8, ENTITY = 9; 
	
	// * = pointer to (ID of another entity)
	// # = number (of) (a numeric value)
	// ? = boolean (1=true, 0=false)
	// [...] = list
	// {...} = set
	
	/**
	 * Actions
	 */
	int 
	ACTION_EXIT = 0,
	ACTION_SAVE = 1,	
	ACTION_LOAD = 2,
	ACTION_INIT = 3;
	
	
	/**
	 * Components
	 */
	int
	/*
	 * Game and Players
	 */
	GAME = 10,
		SEED = 11,
		SEEDS = 12,   // [#,#,...] for any pseudo random progression
		TURN = 13, // # (0 = game setup, 1 is first active turn)
		PLAYERS = 14, // [*x,*y,*z]
		GALAXIES = 15,// [*x,*y,*z]
		
		// view control
		SCREEN = 20,
		SCREEN_ENTITY = 21, // {*a,..} (e.g. the colony in the colony screen, the player in the galaxy screen; the appearance of each screen might be linked to a set of entities; their type is usually an expected one)  
		RETURN_SCREEN = 22, // the screen one goes back to from e.g. load/save dialog screen
		PAGE = 23, // # (used for paging - e.g. load screen)
		
		// action state
		ACTION = 30, // 0 = exit, 1 = save
		SAVEGAME = 31, // name of the game to save/saved
		RENAME = 32, // [*entity, *comp, #font, #size, #x, #y, #w, #h]

	PLAYER = 40,
		// TURN (player sets its turn to the current game turn when finished; in turn zero TURN set to -1 until player has finished setup)
		NO = 41, //#
		// SEEDS [#,#,...]
		// RACE *x
		HOME = 42, // *x (planet)
		STATUS = 43, // [?AI, ?ALIVE]
		REACH = 44, // # the maximum distance the player knows how to travel 
		// TECHNOLOGY (in progress)
		// (things belong to the player)
		PLANS = 51,
		RELATIONS = 52, // [#,#,...] (the diplomatic points for each player, in order of game players)
		TECHNOLOGIES = 53, // {*x,*y,*z} (researched)
		ABILITIES = 54, // {*x,*y,*z} (gained from technology, etcetera)
		COLONIES = 55, // {*x,*y,*z}
		ORBITS = 56, // {*x,*y,*z}
		FLEETS = 57, // {*x,*y,*z}
		OFFERS = 58, // {*x,*y,*z}
		// (things known to (/discovered by) the player)
		// PLANETS {*x,*y,*z} 
		// SOLAR_SYSTEMS {*x,*y,*z}
		// CLUSTERS {*x,*y,*z}
		// GALAXIES {*x,*y,*z}
		// MODULES {*x,*y,*z}
		// UNITS {*x,*y,*z}
		// PARTS {*x,*y,*z} (what can be used by a player)
		
	PLAN = 70,
		PARTICIPANTS = 71, // {*x,*y,... } (a set of colonies, orbits, space-crafts, constructions) that belong to the plan
	
	MISSION = 80,
		
	/*
	 * Galaxy
	 */
	GALAXY = 100,
		// SEEDS [#,#,...]
		CLUSTERS = 101, // {*x,*y,*z}
	
	CLUSTER = 110,
		// SEEDS [#,#,...]
		SOLAR_SYSTEMS = 111, // {*x,*y,*z}
		POSITION = 112, // [#x,#y, #z] (in the galaxy; say there is a plane through the center of a cluster/galaxy the z-axis is how much above or below that plane a system is)
	
	SOLAR_SYSTEM = 120,
		// SEEDS [#,#,...]
		PLANETS = 121, // {*x,*y,*z}
		// POSITION (in the cluster)		
		//FLEETS = {*x,*y,*z} (set of all the fleets arrived/staying in a solar system)
	
	PLANET = 130,
		// SEEDS [#,#,...]
		// KIND *x
		// COLONY *y (cross ref)
		// POSITION (in the solar system)
	
	KIND = 140, // of planet (mostly a description for humans)
		PROBABILITIES = 141, // [#,#,...] of special resources			
		
	/*
	 * Diplomacy and Trading
	 */
	OFFER = 200,
		// PLANET *x
		// SOLAR_SYSTEMS {*x,*y,*z} (where it is offered)
		QUANTITY = 201, // #
		TURNS = 202, // # 
		PRICE = 203,
		
	PERCEPTION = 210, // like friendly, angry, enemy, ally  
		RANGE = 211, // [#min, #max] (of diplomaty points)
	
	/*
	 * Technology and Abilities
	 */
	TECHNOLOGY = 300,
		COSTS = 301, // # (number of research points required)
		//PARTS {#x,#y,...} (what becomes known to the player when discovered given by a part-code)
	
	ABILITY = 310,
		
	/*
	 * Colonies and Orbits
	 */	
	COLONY = 400,
		// PLANET *x
		// ORBIT *y
		// UNIT *z
	
	ORBIT = 410,
		// PLANET *x
		// UNIT *x

	/*
	 * Space-crafts
	 */
	SPACECRAFT = 500,
		// UNIT *x
		// CLASS *x
		// POSITION

	CLASS = 510,
		CHARACTERISTICS = 511, // [#,#,#,..] (ideal number of properties a spacecraft needs to have to be of a certain class, like lasers, shield strength, crew; closest matches)

	FLEET = 520,
		SHIPS = 521, // [*x,*y,*z]
		// POSITION	
		
	COMMANDER = 530,
	
	/*
	 * Units, Modules, Parts and Constructions
	 */
	UNIT = 600, // a compound of modules
		HOLDER = 601,  // *x (a reference back to a planet, orbit or spacecraft)
		MODULES = 601, // [*x,*y,*z]
		WORKERS = 602, // [ #farmers, #scientists, #engineers ]
		GROWTH  = 603, // [ #food, #research-points, #production-points ] (per turn)
		// COSTS # (total construction points required)
		
	MODULE = 610,
		SLOTS = 611,
		PARTS = 612,
		POPULATION = 623,
		// COSTS # (total construction points required)		
		
	PART = 620,
		CODE = 621, // # a statically unique value for all parts (e.g. used to refer to a part from technology)
		// COSTS # (of construction points required)
	
	CONSTRUCTION = 630,
		//UNIT *x
		PROGRESS = 631, // [#,#,..] (points for each module in the unit)

	/*
	 * Race and Traits
	 */
	RACE = 700,
		TRAITS = 701,
	
	TRAIT = 710
		//ABILITIES {*x,*y,..}
	;
}
