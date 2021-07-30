package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("researchfield")
public final class ResearchField extends Spectrum {

	public static final class Ref extends ByteRef<ResearchField> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<ResearchField> entityType() {
			return ResearchField.class;
		}
	}

}
