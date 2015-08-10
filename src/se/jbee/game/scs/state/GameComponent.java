package se.jbee.game.scs.state;

import se.jbee.game.state.Component;

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
	
	int 
	/*
	 * Game and Players
	 */
	GAME = 10,
		SEEDS = 11,   // [#,#,...] for any pseudo random progression
		PLAYERS = 12, // [*x,*y,*z]
		GALAXIES = 13,// [*x,*y,*z]
		TURN = 14, // #
		SCREEN = 15,

	PLAYER = 20,
		// TURN (player sets its turn to the current game turn when finished)
		NO = 21, //#
		// SEEDS [#,#,...]
		// RACE *x
		HOME = 22, // *x (planet)
		STATUS = 23, // [?AI, ?ALIVE]
		// TECHNOLOGY (in progress)
		// things belong to the player:
		PLANS = 31,
		RELATIONS = 32, // [#,#,...] (the diplomatic points for each player, in order of game players)
		TECHNOLOGIES = 33, // {*x,*y,*z} (researched)
		ABILITIES = 34, // {*x,*y,*z} (gained from technology, etcetera)
		COLONIES = 35, // {*x,*y,*z}
		ORBITS = 36, // {*x,*y,*z}
		FLEETS = 37, // {*x,*y,*z}
		OFFERS = 38, // {*x,*y,*z}
		// things known to (/discovered by) the player
		// PLANETS {*x,*y,*z} 
		// SOLAR_SYSTEMS {*x,*y,*z}
		// CLUSTERS {*x,*y,*z}
		// GALAXIES {*x,*y,*z}
		// MODULES {*x,*y,*z}
		// UNITS {*x,*y,*z}
		// PARTS {*x,*y,*z} (what can be used by a player)
		
	PLAN = 50,
		PARTICIPANTS = 51, // {*x,*y,... } (a set of colonies, orbits, spacecrafts, constructions) that belong to the plan
		
	/*
	 * Galaxy
	 */
	GALAXY = 100,
		// SEEDS [#,#,...]
		CLUSTERS = 101, // {*x,*y,*z}
	
	CLUSTER = 110,
		// SEEDS [#,#,...]
		SOLAR_SYSTEMS = 111, // {*x,*y,*z}
		POSITION = 112, // [#x,#y] (in the galaxy)
	
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
