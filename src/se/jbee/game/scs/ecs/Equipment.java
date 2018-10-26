package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.comp.Curve;

public abstract class Equipment extends Option { // a.k.a. Components

	public static abstract class Ref<T extends Equipment> extends ShortRef<T> {
	
		public Ref(short serial) {
			super(serial);
		}
	}

	public EquipmentType.Ref type;

	@NonNegative
	public byte weight;

	@Positive
	public byte constructionCosts;
	@Positive
	public byte rareMaterialCosts;
	@NonNegative
	public byte energyConsumption;
	/**
	 * TODO Change bank calculation so that the increase is gained depending on how
	 * many neighbours of each bank cell also belong to the bank
	 * (up/down/left/right). That way the form is more relevant
	 */
	@Percent
	public Curve bankIncrease;
	@NonNegative
	public byte destrictionCosts;

	/**
	 * Will the component work when it is damaged?
	 */
	@Percent
	public byte reliability;

	/**
	 * When grid components are damaged they loose output proportional to the
	 * damage. Non-grid components either function or malfunction depending on their
	 * {@link #reliability}.
	 */
	public boolean grid;

	public boolean isDestructable() {
		return destrictionCosts > 0;
	}

	public Facility asFacility() {
		return (Facility) this;
	}

	public Weapon asWeapon() {
		return (Weapon) this;
	}

	public Shield asShield() {
		return (Shield) this;
	}
}
