package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Essence;
import se.jbee.game.any.ecs.Instance;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.scs.ecs.system.System;

/**
 * Something that a {@link Frame} can be placed upon.
 */
public abstract class Base extends Essence {

	public static abstract class Ref<T extends Instance> extends ShortRef<T> {

		public Ref(short serial) {
			super(serial);
		}
	}
	public SolarSystem.Ref homeSystem;
	/**
	 * A rectangular 2D area of size N x M given row by row. True means the grid
	 * cell accepts {@link System}s, false does not.
	 */
	boolean[][] grid;

	//TODO add occurrences of "resources" that can be minded in some way or that just block usage.
}
