package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.EntityType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.Entity;

public abstract class Refs<T extends EntityType> implements ComponentType {

	/**
	 * The {@link Class} is serialised and de-serialised using {@link Entity}
	 * annotation that it is expected to have.
	 */
	@Component(1)
	private final Class<T> entityType;

	public Refs(Class<T> entityType) {
		this.entityType = entityType;
	}

	public abstract int size();

	public abstract int serial(int n);

	public final Class<T> entityType() {
		return entityType;
	}

}
