package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.ControlSystem;

@Entity("bridge")
public final class Bridge extends Device<ControlSystem, ControlSystem.Ref> {

	public static final class Ref extends Device.Ref<Bridge> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Bridge> entityType() {
			return Bridge.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, ControlSystem system) {
		// TODO Auto-generated method stub

	}
}
