package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("controlsystem")
public final class ControlSystem extends System {

	public static final class Ref extends System.Ref<ControlSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<ControlSystem> entityType() {
			return ControlSystem.class;
		}
	}

	@Override
	public Function function() {
		return Function.SUPPORT;
	}
}
