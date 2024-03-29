package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("targetingsystem")
public final class TargetingSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<TargetingSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<TargetingSystem> entityType() {
			return TargetingSystem.class;
		}
	}

	@Override
	public Function function() {
		return Function.ATTACK;
	}
}
