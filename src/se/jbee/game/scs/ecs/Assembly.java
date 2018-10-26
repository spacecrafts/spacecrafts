package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.comp.IntRef;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.comp.Location;

/**
 * A {@link Assembly} is the current state of an instance of a particular
 * {@link Prototype} located on a {@link Platform}.
 *
 * @see Platform
 */
public final class Assembly extends Manifestation {

	public static final class Ref extends IntRef<Assembly> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Assembly> entityType() {
			return Assembly.class;
		}
	}
	/**
	 * The {@link Colony}, {@link Spacestation} or {@link Spacecraft} that hosts this {@link Assembly}
	 */
	public Platform.Ref<?> buildUpon;
	/**
	 * Points to the grid cell of the {@link Platform} {@link #buildUpon} where the
	 * top left corner of the {@link Assembly} is located.
	 */
	public Location platformTopLeft;
	public Prototype.Ref blueprint;
	public int population;
	/**
	 * Actual number of staff working in the {@link EquipmentArray}s of the {@link Assembly}.
	 */
	public byte staff;

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
