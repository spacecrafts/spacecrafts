package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("orbit")
public final class Orbit extends Host {

	public static final class _Orbit extends Host._Host<Orbit> {
	
		public _Orbit(short serial) {
			super(serial);
		}
		@Override
		public Class<Orbit> entityType() {
			return Orbit.class;
		}
	}

	public Planet._Planet orbitedPlanet;
}
