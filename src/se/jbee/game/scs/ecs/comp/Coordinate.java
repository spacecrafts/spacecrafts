package se.jbee.game.scs.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;

/**
 * 3D position in space.
 */
public final class Coordinate implements ComponentType {

	@Component(0)
	public final int x;
	@Component(1)
	public final int y;
	@Component(2)
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
