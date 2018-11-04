package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Troop;

/**
 * Each slot has a base teleport capacity of one {@link Troop}. Normal
 * {@link System#sizeBonus} applies. The resulting number is the number
 * of {@link Troop}s that can be teleported per round.
 */
@Entity("teleportationsystem")
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
	public Area area() {
		return Area.ATTACK;
	}
}
