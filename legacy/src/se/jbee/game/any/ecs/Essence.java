package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.comp.Name;

/**
 * An {@link Entity} type that models essential things or concepts in the game
 * which are created by the game without direct player action. Often created
 * from game data files that specify the details of a particular
 * {@link Instance} or derived from other entities already created.
 */
public abstract class Essence extends Instance {

	public long[] seeds;
	public Name name;
}
