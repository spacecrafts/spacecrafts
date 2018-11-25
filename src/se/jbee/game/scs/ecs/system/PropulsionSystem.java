package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;

@EntityType("propulsionsystem")
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
	public byte orbitalThrust;

	@NonNegative
	public byte impulseThrust;

	@NonNegative
	public byte wrapThrust;

	@Override
	public Area area() {
		return Area.SUPPORT;
	}
}
