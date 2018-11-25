package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Squad;
import se.jbee.game.scs.ecs.system.TrainingSystem;

/**
 * A {@link Device} (building) for training and housing {@link Squad}s.
 */
@EntityType("quarters")
public final class Quarters extends Device<TrainingSystem, TrainingSystem.Ref> {

	/**
	 * The maximum possible number of quarters in this {@link Device}.
	 */
	@NonNegative @Aggregated
	public short totalNumberOfTroopQuarters;

	/**
	 * The maximum usable number of quarters for {@link Squad}.
	 *
	 * This is the effective upper limit of troop training.
	 */
	@NonNegative @Aggregated
	public short effectiveNumberOfTroopQuarters;

	/**
	 * The {@link Squad}s currently living and training in this {@link Quarters}s.
	 */
	public se.jbee.game.any.ecs.comp.Refs<Squad> squads;

	//TODO rate of training per turn - xp gain (also can be improved through Traits)

	@Override
	protected void aggregate(State state, TrainingSystem system) {
		// TODO Auto-generated method stub

	}
}
