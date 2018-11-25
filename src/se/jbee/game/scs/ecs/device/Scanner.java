package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.DetectionSystem;

@EntityType("scanner")
public final class Scanner extends Device<DetectionSystem, DetectionSystem.Ref> {

	public static final class Ref extends Device.Ref<Scanner> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Scanner> entityType() {
			return Scanner.class;
		}
	}

	@Override
	protected void aggregate(State state, DetectionSystem system) {
		// TODO Auto-generated method stub

	}

}
