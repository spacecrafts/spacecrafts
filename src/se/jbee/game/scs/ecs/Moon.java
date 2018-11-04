package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

@Entity("moon")
public final class Moon extends Base {

	public static final class Ref extends Base.Ref<Moon> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Moon> entityType() {
			return Moon.class;
		}
	}

	@NonNegative
	public byte nr;
	public Planet.Ref orbitedPlanet;

	@Override
	public String toString() {
		return nr + ". moon of planet " + orbitedPlanet.serial;
	}
}
