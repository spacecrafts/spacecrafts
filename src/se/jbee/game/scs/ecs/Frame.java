package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Code;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.constant.FrameClassification;
import se.jbee.game.scs.ecs.device.Device;

/**
 * A {@link Frame} is the base type for {@link Manifestation}s of a
 * {@link Prototype}. It consists of one or more {@link Module} that are
 * connected together.
 *
 * Simple ships have a single {@link Module}. Larger ships often have multiple
 * {@link Module}s that can be separated and recomposed again.
 *
 * As soon as a {@link Player} starts building a new {@link Spacecraft},
 * {@link Spacestation}, {@link Colony} or {@link Outpost} the instance is
 * created. It builds up in its details over time using the {@link Prototype} as
 * it blueprint. Each {@link Colony} or {@link Outpost} has its own copy that
 * diverges from the initial {@link Prototype} over time while
 * {@link Spacecraft}s and {@link Spacestation}s usually share a
 * {@link Prototype}.
 */
public abstract class Frame<T extends Base.Ref<?>> extends Manifestation {

	//TODO maybe do multi module protypes using multi-frame with the benefit of boni applying to all connected frames and frames protecting each other.

	public static abstract class Ref<T extends Frame<?>> extends IntRef<T> {

		public Ref(int serial) {
			super(serial);
		}
	}

	public static enum Kind {
		@Code('C') COLONY,
		@Code('O') OUTPOST,
		@Code('c') SPACECRAFT,
		@Code('s') SPACESTATION
	}

	public abstract Kind kind();

	public T base;

	public FrameClassification.Ref classification;
	public Prototype.Ref prototype;
	public Player.Ref controlledBy;
	/**
	 * The list of {@link Device}s in this {@link Frame}.
	 *
	 * The order set by the player is the order in which the {@link Device}s are build.
	 */
	public Refs<Device<?, ?>> devices;

	/**
	 * Given in thousands. A full thousand is one unit.
	 */
	@NonNegative
	public int population;

	/**
	 * Actual number of staff working in the {@link Device}s of the {@link Frame}.
	 */
	@NonNegative
	public byte staffUnits;
	/**
	 * Actual number of crew units available.
	 */
	@NonNegative
	public byte crewUnits;

	@NonNegative
	public int effectiveHousingProvided;
	@NonNegative
	public int effectiveProductionPoints;
	@NonNegative
	public int effectiveFoodProduction;
	@NonNegative
	public int effectiveRareMaterialsOutput;
	@NonNegative
	public int effectiveResearchPoints;
	@NonNegative
	public int effectiveWisdomGain;
	@NonNegative
	public int effectiveEnergyProduction;
	@NonNegative
	public int effectiveEnergyConsumption;
	@NonNegative
	public int foodUnitsInStock;

	// maybe make a list of custom configurations and damage that modify the data as given by the design
}
