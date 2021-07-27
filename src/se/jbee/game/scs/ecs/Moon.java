package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Describable;
import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;

@EntityType("moon")
public final class Moon extends Base implements Describable {

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
	public void describe(State s, StringBuilder b) {
		b.append(nr).append(". moon of ").append(s.entity(orbitedPlanet).name);
	}

}
