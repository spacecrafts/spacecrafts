package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.ControlSystem;

@EntityType("bridge")
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
	protected void aggregate(State state, ControlSystem system) {
		// TODO Auto-generated method stub

	}
}
