package se.jbee.game.scs.ecs.system;

import se.jbee.game.scs.ecs.Troop;

public final class TrainingSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<TrainingSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<TrainingSystem> entityType() {
			return TrainingSystem.class;
		}
	}

	@Override
	public Area area() {
		return Area.CREW;
	}

	/**
	 * The number of {@link Troop}s living in this type of crew's quarters.
	 *
	 * This scales, as usual, with the {@link System#sizeBonus}.
	 */
	public byte troopQuarters;
}
