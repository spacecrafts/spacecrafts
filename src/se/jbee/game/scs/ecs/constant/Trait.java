package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Range;

@Entity("trait")
public final class Trait extends Option {

	/**
	 * Number of points the {@link Trait} costs to develop (chose)
	 */
	@Range(min = -10, max = 10)
	public byte evolutionPoints;

	/**
	 * Grouping are given through {@link Option#groupCode()}. Group {@code 0} can be
	 * used for traits not belonging to a specific group. A group with a default is
	 * a group were a choice has to be selected, initially the default
	 */
	public boolean isDefault;

	// is mutual exclusion needed? list of codes?
}
