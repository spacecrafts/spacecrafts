package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public abstract class ByteRef<T extends Entity> extends EntityRef<T> {

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

}
