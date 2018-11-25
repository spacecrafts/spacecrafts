package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("&rank")
public final class Rank extends Spectrum {

	public static final class Ref extends ByteRef<Rank> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Rank> entityType() {
			return Rank.class;
		}
	}
}
