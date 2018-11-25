package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public final class ByteRefs<T extends Entity> extends UniRefs<T> {

	@Component(1)
	@NonNegative
	private volatile byte[] serials;

	public ByteRefs(Class<T> entityType, byte[] serials) {
		super(entityType);
		this.serials = serials;
	}

	@Override
	public int size() {
		return serials.length;
	}

	@Override
	public int serial(int n) {
		return serials[n];
	}

}
