package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.Squad;

/**
 * Each slot has a base teleport capacity of one {@link Squad}. Normal
 * {@link System#scaleBonus} applies. The resulting number is the number
 * of {@link Squad}s that can be teleported per round.
 */
@EntityType("teleportationsystem")
public final class TeleportationSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<TeleportationSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<TeleportationSystem> entityType() {
			return TeleportationSystem.class;
		}
	}

	@Override
	public Function function() {
		return Function.ATTACK;
	}
}
