package se.jbee.game.scs.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;

public final class Curve implements ComponentType {

	/**
	 * The independent axis, usually the size or round (time)
	 */
	public short[] reference;
	/**
	 * The dependent axis
	 */
	public short[] output;

	public int at(int n) {
		// TODO Auto-generated method stub
		return 0;
	}
}
