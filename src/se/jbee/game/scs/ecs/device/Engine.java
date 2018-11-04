package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.PropulsionSystem;

@Entity("engine")
public final class Engine extends Device<PropulsionSystem, PropulsionSystem.Ref> {

	public static final class Ref extends Device.Ref<Engine> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Engine> entityType() {
			return Engine.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, PropulsionSystem system) {
		// TODO Auto-generated method stub

	}

	@NonNegative
	public int totalThrust;

	// wrap drive: wrap 1 means 1 parsec per turn, wrap 9 means 9 per turn
}
