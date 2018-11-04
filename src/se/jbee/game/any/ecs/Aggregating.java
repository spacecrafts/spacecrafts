package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.meta.Aggregated;

/**
 * Must be implemented of types with {@link Aggregated} fields.
 */
public interface Aggregating {

	/**
	 * Aggregates the value for all {@link Aggregated} fields of this
	 * {@link EntityType}.
	 *
	 * @param state
	 *            current state used to resolve reference during aggregation
	 *            computations.
	 */
	void updateAggregated(State state);
}
