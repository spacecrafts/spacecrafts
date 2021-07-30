package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.EntityType;

public abstract class UniRefs<T extends Entity> implements Refs<T> {

	/**
	 * The {@link Class} is serialised and de-serialised using {@link EntityType}
	 * annotation that it is expected to have.
	 */
	@Component(1)
	private final Class<T> entityType;

	public UniRefs(Class<T> entityType) {
		this.entityType = entityType;
	}

	@Override
	public final Class<T> entityType(int n) {
		return entityType;
	}

	@Override
	public final String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[').append(entityType.getAnnotation(EntityType.class).value());
		for (int i = 0; i < size(); i++) {
			b.append(':').append(serial(i));
		}
		b.append(']');
		return b.toString();
	}
}
