package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.TeleportationSystem;

@Entity("teleporter")
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
	protected void updateAggregated(State state, Frame<?> frame, TeleportationSystem system) {
		// TODO Auto-generated method stub

	}
}
