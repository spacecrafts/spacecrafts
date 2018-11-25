package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.constant.Model;
import se.jbee.game.scs.ecs.system.PropulsionSystem;
import se.jbee.game.scs.ecs.system.System;

/**
 * A {@link Spacestation} is for {@link Orbit} what {@link Colony} is for
 * {@link Planet}.
 *
 * {@link Spacecraft}s can transition to become a {@link Spacestation} and
 * backwards if they match the criteria required for the {@link Model}.
 *
 * While any {@link Spacecraft} can become a stationary in principle it has to
 * be able to get into {@link Orbit}. A craft build on {@link Planet} or
 * {@link Moon} needs to overcome the gravitation which means it requires the
 * ship to be able to go at a certain minimum impulse speed. Something usually
 * not needed once the station is in orbit. This can be made more smooth
 * building and using "tug ships" which are normal {@link Spacecraft}s that have
 * over-sized {@link PropulsionSystem} able to lift the attached station with it. These
 * can be coupled with the later station {@link Module}s using coupling or
 * tractor beam {@link System}.
 */
@EntityType("spacestation")
public final class Spacestation extends Frame<Orbit.Ref> {

	public static final class Ref extends Frame.Ref<Spacestation> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Spacestation> entityType() {
			return Spacestation.class;
		}
	}

	@Override
	public Kind kind() {
		return Kind.SPACESTATION;
	}
}
