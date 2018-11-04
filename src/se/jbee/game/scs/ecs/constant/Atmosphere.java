package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Classification;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Entity;

@Entity(":atmosphere")
public final class Atmosphere extends Classification {

	public static final class Ref extends ByteRef<Atmosphere> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Atmosphere> entityType() {
			return Atmosphere.class;
		}
	}

}
