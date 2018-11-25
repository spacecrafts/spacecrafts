package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.TeleportationSystem;

@EntityType("teleporter")
public class Teleporter extends Device<TeleportationSystem, TeleportationSystem.Ref> {

	public static final class Ref extends Device.Ref<Teleporter> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Teleporter> entityType() {
			return Teleporter.class;
		}
	}

	@Override
	protected void aggregate(State state, TeleportationSystem system) {
		// TODO Auto-generated method stub

	}
}
