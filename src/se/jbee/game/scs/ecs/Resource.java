package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Constant;
import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * While a fix set of resources is embedded into the game mechanics there is a
 * part for each resource that is best described and created like other
 * {@link Constant}s.
 */
@Entity("resource")
public final class Resource extends Option {

	public static final class Ref extends ByteRef<Resource> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Resource> entityType() {
			return Resource.class;
		}
	}
}
