package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("detectionsystem")
public final class DetectionSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<DetectionSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<DetectionSystem> entityType() {
			return DetectionSystem.class;
		}
	}

	@Override
	public Function function() {
		return Function.SPECIAL;
	}
}
