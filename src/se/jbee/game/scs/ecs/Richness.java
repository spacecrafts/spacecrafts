package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Classification;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("richnessclass")
public final class Richness extends Classification {

	public static final class Ref extends ByteRef<Richness> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Richness> entityType() {
			return Richness.class;
		}
	}

}
