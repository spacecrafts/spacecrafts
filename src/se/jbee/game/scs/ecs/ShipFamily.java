package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("shipfamily")
public final class ShipFamily extends Spectrum {

	public static final class Ref extends ShortRef<ShipFamily> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<ShipFamily> entityType() {
			return ShipFamily.class;
		}
	}

	Race.Ref race;

	/**
	 * Flag to mark a family that describes a {@link Spacestation} rather than a {@link Spacecraft}.
	 */
	public boolean station;

	// ideal number of properties a spacecraft needs to have to be of a certain class, like lasers, shield strength, crew; closest matches
}
