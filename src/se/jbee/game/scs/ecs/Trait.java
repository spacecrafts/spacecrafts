package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("trait")
public final class Trait extends Option {

	/**
	 * Number of points the {@link Trait} costs to develop (chose)
	 */
	public byte evolutionPoints;

	//TODO groups and excluding references
}
