package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.comp.Coordinate;

@Entity("spacecraft")
public final class Spacecraft extends Platform {

	public static final class Ref extends Platform.Ref<Spacecraft> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Spacecraft> entityType() {
			return Spacecraft.class;
		}
	}
	public ShipFamily.Ref family;
	public Coordinate position;
}
