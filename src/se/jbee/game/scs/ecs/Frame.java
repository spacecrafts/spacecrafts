package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.Detail;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.Code;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.constant.Model;
import se.jbee.game.scs.ecs.device.Device;
import se.jbee.game.scs.ecs.layout.Blueprint;
import se.jbee.game.scs.ecs.layout.Module;

/**
 * A {@link Frame} is the base type for {@link Detail}s of a
 * {@link Blueprint}. It consists of one or more {@link Module} that are
 * connected together.
 *
 * Simple ships have a single {@link Module}. Larger ships often have multiple
 * {@link Module}s that can be separated and recomposed again.
 *
 * As soon as a {@link Player} starts building a new {@link Spacecraft},
 * {@link Spacestation}, {@link Colony} or {@link Outpost} the instance is
 * created. It builds up in its details over time using the {@link Blueprint} as
 * it blueprint. Each {@link Colony} or {@link Outpost} has its own copy that
 * diverges from the initial {@link Blueprint} over time while
 * {@link Spacecraft}s and {@link Spacestation}s usually share a
 * {@link Blueprint}.
 */
public abstract class Frame<T extends Base.Ref<?>> extends Composition {

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

	public Model.Ref model;
	public Blueprint.Ref prototype;
	/**
	 * The list of {@link Device}s in this {@link Frame}.
	 *
	 * The order set by the player is the order in which the {@link Device}s are build.
	 */
	public Device.Refs devices;

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
	public int foodUnitsInStock;

	@NonNegative @Aggregated
	public int effectiveHousingProvided;
	@NonNegative @Aggregated
	public int effectiveProductionPoints;
	@NonNegative @Aggregated
	public int effectiveFoodProduction;
	@NonNegative @Aggregated
	public int effectiveRareMaterialsOutput;
	@NonNegative @Aggregated
	public int effectiveResearchPoints;
	@NonNegative @Aggregated
	public int effectiveWisdomGain;
	@NonNegative @Aggregated
	public int effectiveEnergyProduction;
	@NonNegative @Aggregated
	public int effectiveEnergyConsumption;

	// maybe make a list of custom configurations and damage that modify the data as given by the design
}
