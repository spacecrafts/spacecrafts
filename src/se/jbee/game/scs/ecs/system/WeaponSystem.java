package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percentage;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.constant.WeaponType;

/**
 * {@link WeaponSystem} fire in order of their {@link TacticalSystem#applicationDelay}.
 */
@Entity("weaponsystem")
public class WeaponSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<WeaponSystem> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<WeaponSystem> entityType() {
			return WeaponSystem.class;
		}
	}

	/**
	 * All weapons have one particular type of {@link WeaponType}.
	 */
	public WeaponType.Ref type;

	/**
	 * A higher velocity makes a weapons damage more independent from the targets
	 * speed. Fast targets might get less damage if they partially outpace the
	 * weapons velocity. The concrete formula has to be defined.
	 */
	@NonNegative
	public byte velocity;

	@NonNegative
	public int damangePerShot;
	/**
	 * Most weapons shot one time per round. Some weapons allow multiple shots
	 * delayed by {@link TacticalSystem#actuationDelay} after that first shot fired
	 * as usual (but usually very early).
	 */
	@Positive
	public byte shotsPerRound;

	@Percentage
	public int damageModification;

	/**
	 * Zero for weapons that do not fire ammunition.
	 */
	@NonNegative
	public int ammunitionPerShot;

}
