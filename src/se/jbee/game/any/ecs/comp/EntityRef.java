package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.EntityType;

public abstract class EntityRef <T extends Entity> implements Ref<T> {

	@Override
	public final String toString() {
		return entityType().getAnnotation(EntityType.class).value() + ":" + serial();
	}
}
