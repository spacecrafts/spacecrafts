package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

@Entity("moon")
public final class Moon extends Host {

	public static final class _Moon extends Host._Host<Moon> {
	
		public _Moon(short serial) {
			super(serial);
		}
		@Override
		public Class<Moon> entityType() {
			return Moon.class;
		}
	}

	@NonNegative
	public byte nr;
	public Planet._Planet orbitedPlanet;

	@Override
	public String toString() {
		return nr + ". moon of planet " + orbitedPlanet.serial;
	}
}
