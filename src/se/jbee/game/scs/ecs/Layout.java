package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * There are two types of {@link Layout}s: Parts called {@link Module} and
 * wholes called {@link Blueprint}.
 *
 * @see Blueprint
 * @see Module
 */
public abstract class Layout extends Composition {

	@NonNegative @Aggregated
	public int totalConstructionCosts;
	@NonNegative @Aggregated
	public int totalStructure;
	@NonNegative @Aggregated
	public int totalWeight;
	/**
	 * The control points generated by the unit
	 */
	@NonNegative @Aggregated
	public int totalControl;
}
