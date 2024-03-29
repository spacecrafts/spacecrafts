package se.jbee.game.scs.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.layout.Blueprint;

/**
 * 2D position within the building matrix of a {@link Blueprint}.
 */
public final class Location implements ComponentType {

	@NonNegative @Component(0)
	public final byte x;
	@NonNegative @Component(1)
	public final byte y;

	public Location(byte x, byte y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return x + ":" + y;
	}
}
