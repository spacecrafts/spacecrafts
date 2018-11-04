package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.comp.Coordinate;

@Entity("spacecraft")
public final class Spacecraft extends Frame<Fleet.Ref> {

	public static final class Ref extends Frame.Ref<Spacecraft> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Spacecraft> entityType() {
			return Spacecraft.class;
		}
	}

	@Override
	public Kind kind() {
		return Kind.SPACECRAFT;
	}

	public Coordinate position;

}
