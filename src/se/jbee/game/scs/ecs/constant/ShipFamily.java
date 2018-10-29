package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Range;
import se.jbee.game.scs.ecs.Race;
import se.jbee.game.scs.ecs.Spacecraft;
import se.jbee.game.scs.ecs.Spacestation;
import se.jbee.game.scs.ecs.Race.Ref;

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

	public Race.Ref originRace;

	/**
	 * Flag to mark a family that describes a {@link Spacestation} rather than a {@link Spacecraft}.
	 */
	public boolean station;

	@Range(min = 1, max = 40)
	public byte maxWidth;

	@Range(min = 1, max = 30)
	public byte maxHeight;

	// ideal number of properties a spacecraft needs to have to be of a certain class, like lasers, shield strength, crew; closest matches
}
