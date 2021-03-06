package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Code;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.Layout;
import se.jbee.game.scs.ecs.Settings;
import se.jbee.game.scs.ecs.comp.Curve;
import se.jbee.game.scs.ecs.constant.SystemType;
import se.jbee.game.scs.ecs.device.Device;

/**
 * {@link System}s are the types of components that can be placed in a
 * {@link Layout}.
 */
public abstract class System extends Preselection {

	public static abstract class Ref<T extends System> extends ByteRef<T> {

		public Ref(byte serial) {
			super(serial);
		}
	}

	public enum Area {
		@Code('A') ATTACK,
		@Code('C') CREW,
		@Code('D') DEFENCE,
		/**
		 * Systems that are necessary to enable life in space and interplanetary travel
		 */
		@Code('S') SUPPORT,
		/**
		 * System that have special purposes but are not strictly necessary on a ship
		 */
		@Code('X') SPECIAL,
		@Code('Y') SUPPLY,
	}

	public abstract System.Area area();

	public SystemType.Ref areaOfApplication;

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

	/**
	 * The maximum number of cells of this {@link System} type that can be combined
	 * into a {@link Device}.
	 */
	@Positive
	public short maxCells;

	/*
	 * Per cell data:
	 */

	@NonNegative
	public byte weight;

	@NonNegative
	public byte destructionCosts;
	@Positive
	public byte constructionCosts;
	@Positive
	public byte rareMaterialCosts;
	@NonNegative
	public byte energyConsumption;
	/**
	 * The {@link Settings#deviceNeighbouringBoost} or
	 * {@link Settings#deviceNeighbouringAutomationBoost} is applied before the size
	 * bonus is added.
	 *
	 * The total for size n: n times base value + bonus(n)
	 */
	@Percent
	public Curve scaleBonus;

	public boolean isDestructable() {
		return destructionCosts > 0;
	}

}
