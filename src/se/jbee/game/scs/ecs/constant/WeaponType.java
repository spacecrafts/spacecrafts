package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.comp.Curve;
import se.jbee.game.scs.ecs.system.WeaponSystem;

/**
 * The {@link WeaponType} is the abstation used to connect attack and defence systems.
 *
 * It also describes how a {@link WeaponSystem} affects the target.
 */
@EntityType("&weapontype")
public final class WeaponType extends Preselection { //TODO maybe this should be about how damage is caused only in the sense that a beam and a mass weapon are the same or almost the same

	public static final class Ref extends ByteRef<WeaponType> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<WeaponType> entityType() {
			return WeaponType.class;
		}
	}

	// examples: beam, forcefield, mass, Missiles,bomber,fighter (as in tie-fighter)

	// tracktor beam slows against thrust
	// stasis field stops and damages but has to overcome thrust completely
	// beam, mass, missiles damage hull and components
	// missile damages other time
	// death-rays damages organic (which might be hull for organic races) but usually is just the troops (how is that computed?) => should be a full protection for it
	// can the weapon fire through own shields etcetera?

	/**
	 * Some force-fields reduce the effective thrust of the target instead of damaging
	 */
	public byte impulseThrustDrainRatio; // or shield dependent

	public boolean damagesHull; //or on weapon

	/**
	 * damage over time (rounds)
	 */
	public Curve roundProgression;
}
