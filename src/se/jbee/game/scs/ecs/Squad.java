package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Detail;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.Morale;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Technology;
import se.jbee.game.scs.ecs.constant.Trait;

/**
 * A combat unit. A group of crew members on a {@link Spacecraft} or
 * {@link Spacestation}.
 */
@EntityType("squad")
public final class Squad extends Detail {

	public static final class Ref extends IntRef<Squad> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Squad> entityType() {
			return Squad.class;
		}
	}

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
	 * {@link Squad} unit with the {@link Technology}, {@link Ability}s and
	 * {@link Trait}s.
	 */
	public Player.Ref byPlayer;
}
