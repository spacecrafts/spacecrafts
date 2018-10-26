package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.EntityType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public abstract class ByteRef<T extends EntityType> implements Ref<T> {

	@Component(0)
	@NonNegative
	public final byte serial;

	public ByteRef(byte serial) {
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
