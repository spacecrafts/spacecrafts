package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.Troop;
import se.jbee.game.scs.ecs.system.TrainingSystem;

/**
 * A {@link Device} (building) for training and housing {@link Troop}s.
 */
@Entity("quarters")
public final class Quarters extends Device<TrainingSystem, TrainingSystem.Ref> {

	@NonNegative @Aggregated
	public short effectiveNumberOfTroopQuarters;

	//TODO rate of training per turn (also can be improved through Traits)

	@Override
	protected void updateAggregated(State state, Frame<?> frame, TrainingSystem system) {
		// TODO Auto-generated method stub

	}
}
