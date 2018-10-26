package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("stellarzone")
public final class StellarZone extends Spectrum {

	public static final class Ref extends ByteRef<StellarZone> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<StellarZone> entityType() {
			return StellarZone.class;
		}
	}

}
