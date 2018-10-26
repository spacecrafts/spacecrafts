package se.jbee.game.scs.ecs;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Group;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Range;

/**
 * General {@link Game} settings.
 *
 * All fields should have a defined value range that allows to auto generate a
 * settings page from it.
 *
 * The {@link Settings} also contain setting based calculations.
 */
@Entity("settings")
public final class Settings extends Composition {

	public static final class Ref extends ByteRef<Settings> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Settings> entityType() {
			return Settings.class;
		}
	}

	/**
	 * Set true if data of constants should be persisted within a save-game file.
	 */
	@Group("gameplay")
	public boolean storeConstants = true;

	/**
	 * Number of over- or underflowing food units per population unit that cause
	 * population growth by one unit.
	 *
	 * Example: 2 means that a planet needs 2x its current size to grow by one unit
	 *
	 * This mainly determines the game speed.
	 */
	@Range(min = 1, max = 10)
	@Group("expansion")
	public byte foodUnitsPerPopulation;

	public int foodUnitsForPupolationGrowth(short population) {
		return population * foodUnitsPerPopulation;
	}

	/**
	 * Construction in {@link Colony}: Percentage of currently constructed
	 * {@link Segment}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent
	@Group("construction")
	public byte constructionSwitchPenaltyColony;
	/**
	 * Construction in {@link Spacecraft}: Percentage of currently constructed
	 * {@link Segment}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent
	@Group("construction")
	public byte constructionSwitchPenaltyShip;
	/**
	 * Construction in {@link Spacestation}: Percentage of currently constructed
	 * {@link Segment}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent
	@Group("construction")
	public byte constructionSwitchPenaltyStation;

	/**
	 * How many percent points the bank size is increased per adjacent bank
	 * component. Standard of 25% means that the maximum of possible 4 adjacent
	 * borders give a boost of 4x25%=100% meaning that the component with 4 adjacent
	 * components adds 1 (base) + 1 (boost) to the bank size.
	 *
	 * A higher value makes placement more relevant, a lower less.
	 */
	@Percent
	@Group("construction")
	public byte equipmentNeighbouringBoost = 10;

	/**
	 * When components are automated the boost might increase what increases the
	 * total output of each component. Naturally this settings has to be equal
	 * (automation off) or higher than {@link #equipmentNeighbouringBoost}.
	 */
	@Percent
	@Group("construction")
	public byte equipmentNeighbouringAutomationBoost = 25;

	/**
	 * Linear increasing number of knowledge points required technology. Example:
	 * base of 10 means first has a base cost of 10, second of 20, third of 30 and
	 * so forth. This does not include the quadratic and cubic cost increase.
	 */
	@Range(min = 10, max = 30)
	@Group("tech")
	public byte technologyBaseCost = 20;

	/**
	 * A higher weight of quadratic cost increase causes a faster increase in total technology costs.
	 */
	@Percent
	@Group("research")
	public byte technologyQuadraticWeight = 50;

	/**
	 * The cubic cost increase should mostly be used to give a long term total cost increase that suits the game speed.
	 * Too high values will make research very hard or impossible after a certain number of technologies.
	 */
	@Percent
	@Group("research")
	public byte technologyCubicWeight = 15;

	public int technologyCost(byte n) {
		double quadratic = n * technologyQuadraticWeight / 100d;
		double cubic = n * technologyCubicWeight / 100d;
		return (int) floor(n * technologyBaseCost  + quadratic * quadratic + cubic * cubic * cubic);
	}

	/**
	 * Impulse {@link Engine}s are summed to a total impulse thrust that counteracts
	 * a ship's inertia. This settings controls how much thrust is needed per unit
	 * of weight/mass to get one unit of impulse speed. A lower settings means less
	 * thrust is needed to get same speed so less {@link Engine} components need to
	 * be build and supplied.
	 */
	@Range(min = 1, max = 10)
	@Group("spacetravel")
	public byte impulseThrustPerShipWeight = 2;

	/**
	 * Same as {@link #impulseThrustPerWeight} just that orbital {@link Engine}s are
	 * special versions of impulse {@link Engine} made to stabilise a position in
	 * orbit. They are cheaper then impulse {@link Engine} but are special purpose
	 * components that cannot be used as impulse {@link Engine}.
	 */
	@Range(min = 1, max = 10)
	@Group("spacetravel")
	public byte orbitalThrustPerShipWeight = 2;

	/**
	 * In principle similar to {@link #impulseThrustPerWeight} just that wrap
	 * {@link Engine} do not care about weight but size (number of components) in a
	 * ship as this determines the volume of space that needs to be wrapped.
	 *
	 * A higher value makes the game progress slower as more wrap {@link Engine} is
	 * required to reach distant systems.
	 */
	@Range(min = 1, max = 10)
	@Group("spacetravel")
	public byte wrapThrustPerShipSize = 1;

	@Range(min = 5, max = 20)
	@Group("spacetravel")
	public byte maximumImpulseSpeed = 10;

	@Range(min = 5, max = 20)
	@Group("spacetravel")
	public byte maximumWrapSpeed = 10;

	public float impulseSpeed(int totalWeight, int totalThrust) {
		return min(maximumImpulseSpeed, totalWeight * impulseThrustPerShipWeight / (float) totalThrust);
	}

	public float wrapSpeed(int totalSize, int totalThrust) {
		return min(maximumWrapSpeed, totalSize * wrapThrustPerShipSize / (float) totalThrust);
	}

	public float orbitalStability(int totalWeight, int totalThrust) {
		return totalWeight * orbitalThrustPerShipWeight / (float) totalThrust;
	}

	public boolean isOrbitalStable(int totalWeight, int totalThrust) {
		return orbitalStability(totalWeight, totalThrust) >= 1;
	}

	/**
	 * How strongly reputation gets worse just by time passing without positive refreshment.
	 */
	@Percent
	@Group("diplomacy")
	public byte reputationDrain = 5;
}
