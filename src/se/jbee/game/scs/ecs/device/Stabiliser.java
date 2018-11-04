package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.StabilisationSystem;

public class Stabiliser extends Device<StabilisationSystem, StabilisationSystem.Ref> {

	public static final class Ref extends Device.Ref<Stabiliser> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Stabiliser> entityType() {
			return Stabiliser.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, StabilisationSystem system) {
		// TODO Auto-generated method stub

	}
}
