package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.EntityType;

/**
 * The base class for all entities in the Entity Component System.
 *
 * {@link Component}s are fields in {@link Entity} classes annotated with the
 * {@link Component} annotation.
 */
public abstract class Entity {

	/**
	 * Each {@link Entity} has a {@link #serial} that is unique for the
	 * {@link EntityType} in a global {@link State} context.
	 */
	public short serial;

}
