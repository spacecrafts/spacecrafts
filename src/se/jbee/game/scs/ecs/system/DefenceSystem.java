package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.comp.Curve;
import se.jbee.game.scs.ecs.constant.WeaponType;
import se.jbee.game.scs.ecs.device.Shield;

/**
 * The shield {@link TacticalSystem#applicationDelay} controls when the
 * {@link #recoveryPerRound} takes effect.
 */
@Entity("defencesystem")
public final class DefenceSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<DefenceSystem> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<DefenceSystem> entityType() {
			return DefenceSystem.class;
		}
	}

	/**
	 * The amount of damage absorbed per cell in the {@link Shield}.
	 * The sum is the total amount that can be absorbed.
	 * It recovers by {@link DefenceSystem#recoveryPerRound} at the end of each round (per component).
	 */
	@Positive
	public byte strength;

	/**
	 * When not set the full summed {@link #strength} can absorb damage. This is
	 * particularly effective against strong weapons but also works well against
	 * many weaker weapons. But such shields require more energy to reach same
	 * shield strength and loose strength proportional to the damage while absorbing
	 * fields usually have higher protection per energy consumption ratio.
	 *
	 * When set a fixed damage is absorbed, the remaining damage gets through. This
	 * is particularly effective against many weak weapons.
	 */
	@NonNegative
	public Curve damageAbsorbtion;

	/**
	 * Some shields deflect a certain percentage of damage as long as the total
	 * damage (in that round) is smaller than the shield strength. The shield is not
	 * reducing in strength when deflecting damage.
	 *
	 * The benefit of deflecting shields is that they keep a constant strength (they
	 * usually are {@link #dynamic}), the down-side is that some damage always
	 * passes and that they give no protection against especially strong weapons.
	 */
	@Percent
	public byte damageDeflection;

	/**
	 * Some shields make it harder for scanners to pierce through which makes
	 * targeting components harder.
	 */
	@NonNegative
	public byte interferenceEffect;

	/**
	 * The shield strength gained at the end of each round as long as energy is applied.
	 */
	@NonNegative
	public byte recoveryPerRound;

	/**
	 * The number of slots in every direction around the {@link DefenceSystem} it spreads
	 * depending on the {@link Shield} size.
	 */
	public Curve spread;

	/**
	 * A permanent shield remains effective even when the shield generating
	 * components are malfunctioning as they have created a protecting layer of some
	 * kind that remains. However, a shield never recovers when malfunctioning.
	 */
	public boolean permanent;

	/**
	 * A dynamic shield has its nominal {@link #strength} at the nominal
	 * {@link System#energyConsumption}. When less energy is given the output
	 * {@link #strength} lowers proportionally.
	 */
	public boolean dynamic;

	/**
	 * The types of {@link WeaponType} the shield protects against
	 */
	public Refs<WeaponType> affectedDamageTypes;
}
