package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public abstract class IntRef<T extends Entity> extends EntityRef<T> {

	@Component(0)
	@NonNegative
	public final int serial;

	public IntRef(int serial) {
		this.serial = serial;
	}

	@Override
	public final int serial() {
		return serial;
	}

}
