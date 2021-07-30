package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.EntityType;

/**
 * Each {@link Planet} has an {@link Orbit}.
 */
@EntityType("orbit")
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
