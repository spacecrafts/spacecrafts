package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.constant.WeaponType;

/**
 * {@link WeaponSystem} fire in order of their {@link TacticalSystem#applicationDelay}.
 * Multiple shots in a round are delayed by the {@link TacticalSystem#actuationDelay}.
 */
@Entity("weaponsystem")
public final class WeaponSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<WeaponSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<WeaponSystem> entityType() {
			return WeaponSystem.class;
		}
	}

	@Override
	public Area area() {
		return Area.ATTACK;
	}

	/**
	 * All weapons have one particular type of {@link WeaponType}.
	 */
	public WeaponType.Ref type;

	/**CREW
	 * A higher velocity makes a weapons damage more independent from the targets
	 * speed. Fast targets might get less damage if they partially outpace the
	 * weapons velocity. The concrete formula has to be defined.
	 */
	@NonNegative
	public byte velocity;

	/**
	 * The base for calculations of the damage caused by this weapon
	 */
	@NonNegative
	public int baseDamage;

	//TODO weapons might have modes like: multi-shot that modify or set the delays and damage
}
