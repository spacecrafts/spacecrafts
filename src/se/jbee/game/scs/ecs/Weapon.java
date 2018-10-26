package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percentage;
import se.jbee.game.any.ecs.meta.Positive;

@Entity("weapon")
public class Weapon extends Equipment {

	public static final class Ref extends Equipment.Ref<Weapon> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Weapon> entityType() {
			return Weapon.class;
		}
	}

	/**
	 * Weapons fire in order of their fire delay starting with zero increasing until
	 * all weapons have been used.
	 */
	@Positive
	public int fireDelay;
	@NonNegative
	public int damange;
	@Percentage
	public int damageModification;

	/**
	 * A higher velocity makes a weapons damage more independent from the targets
	 * speed. Fast targets might get less damage if they partially outpace the
	 * weapons velocity. The concrete formula has to be defined.
	 */
	@NonNegative
	public int velocity;

	@NonNegative
	public int ammunitionPerUse;

	/**
	 * Damage for encapsulation beams has to reach the structure of the target to
	 * put the weapon into effect.
	 */
	public boolean structureDependentDamage;

}
