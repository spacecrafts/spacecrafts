package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.IntRef;

/**
 * A {@link Platform} hosts a {@link Assembly}.
 *
 * @see Spacecraft
 * @see Spacestation
 * @see Colony
 */
public abstract class Platform extends Composition {

	public static abstract class Ref<T extends Platform> extends IntRef<T> {

		public Ref(int serial) {
			super(serial);
		}
	}

	public Assembly.Ref assembly;

}
