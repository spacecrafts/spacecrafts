package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

@Entity("propulsionsystem")
public final class PropulsionSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<PropulsionSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<PropulsionSystem> entityType() {
			return PropulsionSystem.class;
		}
	}

	@NonNegative
	public byte thrust;

	@Override
	public Area area() {
		return Area.SUPPORT;
	}
}
