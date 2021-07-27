package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Squad;

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
	public Function function() {
		return Function.CREW;
	}

	/**
	 * The number of {@link Squad}s living in this type of crew's quarters.
	 *
	 * This scales, as usual, with the {@link System#scaleBonus}.
	 */
	@NonNegative
	public byte troopCapacity;

	/**
	 * Some number telling how effective new troops can be trained
	 */
	public byte training;

	/**
	 * Some number telling how morale of troops is modified
	 */
	public byte moraleBoost;

	// ideas: crew-quarters, holo-suite, captains-quaters
}
