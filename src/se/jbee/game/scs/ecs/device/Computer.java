package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.TargetingSystem;

@EntityType("computer")
public final class Computer extends Device<TargetingSystem, TargetingSystem.Ref> {

	public static final class Ref extends Device.Ref<Computer> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Computer> entityType() {
			return Computer.class;
		}
	}

	@Override
	protected void aggregate(State state, TargetingSystem system) {
		// TODO Auto-generated method stub

	}
}
