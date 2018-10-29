package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.Morale;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Technology;
import se.jbee.game.scs.ecs.constant.Trait;

/**
 * A combat unit. A group of crew members on a {@link Spacecraft} or
 * {@link Spacestation}.
 */
public final class Troop extends Manifestation {

	/**
	 * E.g. death-rays directly affect the {@link #hitPoints} of troops.
	 */
	@NonNegative
	public byte hitPoints;

	/**
	 * The damage the unit can deal during close combat.
	 *
	 * The effective damage is the {@link #baseDamage} boosted by the
	 * {@link Morale#boost}.
	 */
	@NonNegative
	public byte baseDamage;

	/**
	 * The number of slots the unit can move within the structure in a single round.
	 */
	@NonNegative
	public byte baseMovement;

	public Morale.Ref morale;

	/**
	 * The {@link Player} and {@link Race} that serve as template for this
	 * {@link Troop} unit with the {@link Technology}, {@link Ability}s and
	 * {@link Trait}s.
	 */
	public Player.Ref byPlayer;
}
