package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

/**
 * A {@link Spacestation} is for {@link Orbit} what {@link Colony} is for
 * {@link Planet}.
 *
 * {@link Spacecraft}s can transition to become a {@link Spacestation} and
 * backwards if they match the criteria required for the {@link ShipFamily}.
 *
 * While any {@link Spacecraft} can become a stationary in principle it has to
 * be able to get into {@link Orbit}. A craft build on {@link Planet} or
 * {@link Moon} needs to overcome the gravitation which means it requires the
 * ship to be able to go at a certain minimum impulse speed. Something usually
 * not needed once the station is in orbit. This can be made more smooth
 * building and using "tug ships" which are normal {@link Spacecraft}s that have
 * over-sized {@link Engine} able to lift the attached station with it. These
 * can be coupled with the later station {@link Segment}s using coupling or
 * tractor beam {@link Equipment}.
 */
@Entity("spacestation")
public final class Spacestation extends Platform {

	public static final class Ref extends Platform.Ref<Spacestation> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Spacestation> entityType() {
			return Spacestation.class;
		}
	}
	public ShipFamily.Ref family;
	public Orbit._Orbit home;
}
