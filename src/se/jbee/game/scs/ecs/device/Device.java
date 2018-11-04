package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.Aggregating;
import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.Module;
import se.jbee.game.scs.ecs.Player;
import se.jbee.game.scs.ecs.Prototype;
import se.jbee.game.scs.ecs.system.System;

/**
 * An array of a particular {@link System} (as used in a {@link Prototype})
 * is instantiated as a concrete {@link Device}.
 *
 * The {@link Module}s of a {@link Prototype} are analysed and expressed as a
 * list of {@link Device}s. These are the functional instances within a
 * {@link Frame} that express the state of a number of grouped
 * {@link System}s of same kind.
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
public abstract class Device<T extends System, R extends System.Ref<? extends T>> extends Manifestation implements Aggregating {

	public static abstract class Ref<T extends Device<?, ?>> extends IntRef<T> {

		public Ref(int serial) {
			super(serial);
		}
	}

	/**
	 * The {@link Frame} this {@link Device} is a part of.
	 */
	public Frame.Ref<?> partOfFrame;

	/**
	 * Refers to the {@link Module} this {@link Device} is one concrete part for.
	 */
	public Module.Ref partOfModule;

	/**
	 * Refers to the {@link System} this {@link Device} represents an instance of.
	 */
	public R system;

	/**
	 * The number of {@link #system} cells belonging to this {@link Device}.
	 */
	public short size;
	//TODO how do devices refer to the cells in the module they include? list of module positions? = size

	/**
	 * Any {@link Device} can be disabled by the {@link Player} (to save energy).
	 *
	 * {@link System#grid} operating {@link System}s can disable any number of cells
	 * while other are either on or off.
	 */
	@NonNegative
	public short disabledCells;

	/**
	 * Number of {@link System} cells not working in this {@link Device}.
	 *
	 * Based on the {@link #system} of this {@link Device} it will stop working at
	 * certain level of {@link #structuralDamage} or it gets reduced to a lower
	 * number of cells.
	 */
	@NonNegative @Aggregated
	public short malfunctioningCells;

	/**
	 * Number of {@link System} cells working. Cells could either be
	 * {@link #malfunctioningCells} or the {@link Device} could be
	 * {@link #disabledCells}.
	 */
	@NonNegative @Aggregated
	public short effectiveCells;

	/**
	 * Each {@link Frame} is build {@link Device} by {@link Device}. Each
	 * {@link Device} does its accounting of how many of the
	 * {@link #totalConstructionCosts} are already completed.
	 */
	@NonNegative
	public short structureCompleted;

	/**
	 * The number of {@link System} cells build based on the
	 * {@link #structureCompleted} so far.
	 */
	@NonNegative @Aggregated
	public short completedCells;

	/**
	 * Absolute amount of mechanical damage this {@link Device} has taken.
	 */
	@NonNegative
	public short structuralDamage;

	@NonNegative @Aggregated
	public short effectiveEnergyConsumption;

	/**
	 * The weight of a fully completed {@link Device}.
	 */
	@NonNegative @Aggregated
	public short totalWeight;

	/**
	 * The building costs of a fully completed {@link Device}
	 */
	@NonNegative @Aggregated
	public short totalConstructionCosts;

	@Override
	public final void updateAggregated(State state) {
		T s = state.entity(system);
		Frame<?> frame = state.entity(partOfFrame);
		//TODO update malfunctioningCells, effectiveCells
		this.effectiveEnergyConsumption = (short) (effectiveCells * s.energyConsumption);
		this.totalWeight = (short) (size * s.weight);
		updateAggregated(state, frame, s);
	}

	protected abstract void updateAggregated(State state, Frame<?> frame, T system);
}
