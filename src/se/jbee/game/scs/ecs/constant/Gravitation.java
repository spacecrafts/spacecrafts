package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.System;

/**
 * {@link Gravitation} has an effect on the output of all staffed {@link System}s.
 */
@EntityType("&gravitation")
public final class Gravitation extends Spectrum {

	public static final class Ref extends ByteRef<Gravitation> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Gravitation> entityType() {
			return Gravitation.class;
		}
	}

	// from G
	// to G
	// production penalty
}