package se.jbee.game.any.ecs;

public abstract class EntityType {

	/**
	 * Each {@link EntityType} has a {@link #serial} that is unique for the type of
	 * {@link EntityType} in the global {@link State} context.
	 */
	public short serial;

}
