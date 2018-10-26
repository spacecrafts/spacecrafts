package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * {@link StructuralUnit}s are parts of a {@link Prototype}.
 *
 * @see Prototype
 * @see Tier
 * @see Segment
 */
public abstract class StructuralUnit extends Composition {

	@NonNegative @Aggregated
	public int totalConstructionCosts;
	@NonNegative @Aggregated
	public int totalStructure;
	@NonNegative @Aggregated
	public int totalWeight;
}
