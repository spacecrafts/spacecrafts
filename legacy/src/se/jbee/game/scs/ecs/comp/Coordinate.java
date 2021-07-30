package se.jbee.game.scs.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * 3D position in space.
 */
public final class Coordinate implements ComponentType {

	@NonNegative @Component(0)
	public final int x;
	@NonNegative @Component(1)
	public final int y;
	@NonNegative @Component(2)
	public final int z;

	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + "|" + y + "|" + z + ")";
	}
}
