package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

/**
 * Each {@link Planet} has an {@link Orbit}.
 */
@Entity("orbit")
public final class Orbit extends Base {

	public static final class Ref extends Base.Ref<Orbit> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Orbit> entityType() {
			return Orbit.class;
		}
	}

	public Planet.Ref ofPlanet;
}
