package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.EntityType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public abstract class ShortRef<T extends EntityType> implements Ref<T> {

	@Component(0)
	@NonNegative
	public final short serial;

	public ShortRef(short serial) {
		this.serial = serial;
	}

	@Override
	public final int serial() {
		return serial;
	}

	@Override
	public final String toString() {
		return entityType().getSimpleName() + "[" + serial + "]";
	}
}
