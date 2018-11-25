package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Classification;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.Name;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("&surface")
public final class Surface extends Classification {

	public static final class Ref extends ByteRef<Surface> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Surface> entityType() {
			return Surface.class;
		}
	}

	public Name name;

}
