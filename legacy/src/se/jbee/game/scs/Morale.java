package se.jbee.game.scs;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.scs.ecs.Squad;

public final class Morale extends Spectrum {

	public static final class Ref extends ByteRef<Morale> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Morale> entityType() {
			return Morale.class;
		}
	}

	/**
	 * The boost given to a {@link Squad}s damage.
	 */
	@Percent
	public byte boost;
}
