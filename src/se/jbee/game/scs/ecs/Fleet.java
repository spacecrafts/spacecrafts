package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.comp.Coordinate;

@Entity("fleet")
public final class Fleet extends Composition {

	public static final class Ref extends Base.Ref<Fleet> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Fleet> entityType() {
			return Fleet.class;
		}
	}

	public Refs<Spacecraft> ships;
	public SolarSystem.Ref destination;
	public Coordinate position;
	public int speed;
}
