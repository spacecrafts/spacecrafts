package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * The {@link Segment}s of a {@link Prototype} are analysed and expressed as a list
 * of {@link EquipmentArray}s. These are the functional instances within a {@link Assembly}
 * that express the state of a number of grouped {@link Equipment}s of same kind.
 *
 * Array Size
 *
 * Computation of a array size: each component gives a fix number of points,
 * usually 2. For each border (up/down/left/right) between components of the
 * bank that are adjacent to another component of the bank +1 is added. Example:
 * a single component has size 2. Two aside each other 2+2+1+1=6. 4 in s square
 * have 4x2+4x1+4x1=12 The bank size is then divided by the base points to get
 * the effective component number. This is the how much single components a bank
 * equals.
 */
public abstract class EquipmentArray<T extends Equipment> extends Manifestation {

	public static abstract class Ref<T extends EquipmentArray<?>> extends IntRef<T> {

		public Ref(int serial) {
			super(serial);
		}
	}

	Equipment.Ref<T> equipment;

	boolean disabled = false;
	/**
	 * absolute number of damage done to the bank.
	 */
	@NonNegative
	short structuralDamage;

	short effectiveEnergyConsumption;

}
