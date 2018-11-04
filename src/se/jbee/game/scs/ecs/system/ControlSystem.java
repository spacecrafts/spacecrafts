package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("controlsystem")
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
	public Area area() {
		return Area.SUPPORT;
	}
}
