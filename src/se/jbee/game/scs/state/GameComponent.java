package se.jbee.game.scs.state;

import se.jbee.game.state.Component;

public interface GameComponent extends Component {

	// 0-3 are defined in Component

	// value component ant its possible values (also type like entities)
	int VAL  = 4, /*=*/ NUM = 5, TXT = 6, SET = 7, LIST = 8, ENTITY = 9; 
	
	// * = pointer to (ID of another entity)
	// # = number (of) (a numeric value)
	// ? = boolean (1=true, 0=false)
	
	int 
	/*
	 * Game and Players
	 */
	GAME = 10,
		SEEDS = 11,   // [#,#,...] for any pseudo random progression
		PLAYERS = 12, // [*x,*y,*z]
		GALAXIES = 13,// [*x,*y,*z]

	PLAYER = 20,
		// SEEDS [#,#,...]
		HOME = 21, // *x (planet)
		STATUS = 22, // [?AI, ?ALIVE]
		TECHNOLOGIES = 23, // [*x,*y,*z] (researched)
		// TECHNOLOGY (in progress)
		COLONIES = 24, // [*x,*y,*z]
		ORBITS = 25, // [*x,*y,*z]
		FLEETS = 26, // [*x,*y,*z]
		// PLANETS (known/discovered)
		// SOLAR_SYSTEMS (known/discovered)
		// CLUSTERS (known/discovered)
		// GALAXIES (known/discovered)
		
	/*
	 * Galaxy
	 */
	GALAXY = 30,
		// SEEDS [#,#,...]
		CLUSTERS = 31, // [*x,*y,*z]
	
	CLUSTER = 40,
		// SEEDS [#,#,...]
		SOLAR_SYSTEMS = 41, // [*x,*y,*z]
	
	SOLAR_SYSTEM = 50,
		// SEEDS [#,#,...]
		PLANETS = 51, // [*x,*y,*z]
	
	PLANET = 60,
		// SEEDS [#,#,...]
		// KIND *x
		// COLONY *y (cross ref)
		POSITION = 61, // [#x,#y]
	
	KIND = 70, // of planet (mostly a description for humans)
		PROBABILITIES = 71, // [#,#,...] of special resources			
		
	/*
	 * Diplomacy and Trading
	 */
	OFFER = 100,
		// PLANET *x
		// SOLAR_SYSTEMS [*x,*y,*z] (where it is offered)
		QUANTITY = 101, // #
		TURNS = 102, // # 
		PRICE = 103,		
	
	/*
	 * Technology
	 */
	TECHNOLOGY = 100,

	
	/*
	 * Colonies and Orbits
	 */	
	COLONY = 200,
		// PLANET *x
		// MODULE *x
		WORKERS = 221, // [#farmers, #scientists, #engineers ]
	
	ORBIT = 210,
		// PLANET *x
		// SPACECRAFT *x

	/*
	 * Space-crafts
	 */
	SPACECRAFT = 300,
		MODULES = 301, // [*x,*y,*z]
		// WORKSERS
		// CLASS *x
		// POSITION

	CLASS = 310, 

	FLEET = 320,
		SHIPS = 321, // [*x,*y,*z]
		// POSITION	
	
	/*
	 * Modules and Parts
	 */
	MODULE = 400,
		SLOTS = 401,
		PARTS = 402,
		POPULATION = 403,
		
	PART = 410;
	

	

}
