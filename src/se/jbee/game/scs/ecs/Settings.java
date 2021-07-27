package se.jbee.game.scs.ecs;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Default;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Group;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Preference;
import se.jbee.game.any.ecs.meta.Range;
import se.jbee.game.scs.ecs.comp.Coordinate;
import se.jbee.game.scs.ecs.constant.Trait;
import se.jbee.game.scs.ecs.layout.Module;
import se.jbee.game.scs.ecs.system.PropulsionSystem;

/**
 * General {@link Game} settings.
 *
 * All fields should have a defined value range that allows to auto generate a
 * settings page from it.
 *
 * The {@link Settings} also contain setting based calculations.
 */
@EntityType("settings")
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
	@Group("gameplay") @Default(1)
	@Preference
	public boolean storeConstants;

	/**
	 * Number of over- or underflowing food units per population unit that cause
	 * population growth by one unit.
	 *
	 * Example: 2 means that a planet needs 2x its current size to grow by one unit
	 *
	 * This mainly determines the game speed.
	 */
	@Range(min = 1, max = 10) @Default(5)
	@Group("expansion")
	public byte foodUnitsPerPopulation;

	public int foodUnitsForPupolationGrowth(short population) {
		return population * foodUnitsPerPopulation;
	}

	/**
	 * Construction in {@link Colony}: Percentage of currently constructed
	 * {@link Module}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent @Default(5)
	@Group("construction")
	public byte constructionSwitchPenaltyColony;
	/**
	 * Construction in {@link Spacecraft}: Percentage of currently constructed
	 * {@link Module}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent @Default(10)
	@Group("construction")
	public byte constructionSwitchPenaltyShip;
	/**
	 * Construction in {@link Spacestation}: Percentage of currently constructed
	 * {@link Module}'s construction points required to switch to another
	 * construction before it is finished
	 */
	@Percent @Default(10)
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
	@Percent @Default(10)
	@Group("construction")
	public byte deviceNeighbouringBoost;

	/**
	 * When components are automated the boost might increase what increases the
	 * total output of each component. Naturally this settings has to be equal
	 * (automation off) or higher than {@link #deviceNeighbouringBoost}.
	 */
	@Percent @Default(25)
	@Group("construction")
	public byte deviceNeighbouringAutomationBoost;

	/**
	 * Linear increasing number of knowledge points required technology. Example:
	 * base of 10 means first has a base cost of 10, second of 20, third of 30 and
	 * so forth. This does not include the quadratic and cubic cost increase.
	 */
	@Range(min = 10, max = 30) @Default(20)
	@Group("tech")
	public byte technologyBaseCost;

	/**
	 * A higher weight of quadratic cost increase causes a faster increase in total technology costs.
	 */
	@Percent @Default(50)
	@Group("research")
	public byte technologyQuadraticWeight;

	/**
	 * The cubic cost increase should mostly be used to give a long term total cost increase that suits the game speed.
	 * Too high values will make research very hard or impossible after a certain number of technologies.
	 */
	@Percent @Default(15)
	@Group("research")
	public byte technologyCubicWeight;

	public int technologyCost(byte n) {
		double quadratic = n * technologyQuadraticWeight / 100d;
		double cubic = n * technologyCubicWeight / 100d;
		return (int) floor(n * technologyBaseCost  + quadratic * quadratic + cubic * cubic * cubic);
	}

	/**
	 * Impulse {@link PropulsionSystem}s are summed to a total impulse thrust that counteracts
	 * a ship's inertia. This settings controls how much thrust is needed per unit
	 * of weight/mass to get one unit of impulse speed. A lower settings means less
	 * thrust is needed to get same speed so less {@link PropulsionSystem} components need to
	 * be build and supplied.
	 */
	@Range(min = 1, max = 10) @Default(2)
	@Group("spacetravel")
	public byte impulseThrustPerShipWeight;

	/**
	 * Same as {@link #impulseThrustPerWeight} just that orbital {@link PropulsionSystem}s are
	 * special versions of impulse {@link PropulsionSystem} made to stabilise a position in
	 * orbit. They are cheaper then impulse {@link PropulsionSystem} but are special purpose
	 * components that cannot be used as impulse {@link PropulsionSystem}.
	 */
	@Range(min = 1, max = 10) @Default(2)
	@Group("spacetravel")
	public byte orbitalThrustPerShipWeight;

	/**
	 * In principle similar to {@link #impulseThrustPerWeight} just that wrap
	 * {@link PropulsionSystem} do not care about weight but size (number of components) in a
	 * ship as this determines the volume of space that needs to be wrapped.
	 *
	 * A higher value makes the game progress slower as more wrap {@link PropulsionSystem} is
	 * required to reach distant systems.
	 */
	@Range(min = 1, max = 10) @Default(1)
	@Group("spacetravel")
	public byte wrapThrustPerShipSize;

	@Range(min = 5, max = 20) @Default(10)
	@Group("spacetravel")
	public byte maximumImpulseSpeed;

	@Range(min = 5, max = 20) @Default(10)
	@Group("spacetravel")
	public byte maximumWrapSpeed;

	/**
	 * Wrap 1-n travels 1-n parsec per turn. This constant sets how many turns it
	 * takes at {@link #maximumWrapSpeed} to travel the maximum distance in the game
	 * is (e.g. from the left top corner to the bottom right corner).
	 *
	 * This factor controls conversion between game {@link Coordinate}s and 1
	 * parsec.
	 *
	 * In reality stellar density it's about 0.15 stars per cubic parsec.
	 *
	 * The average separation between stars is about 0.554 * (number
	 * density)^(-1/3), calculated from Mean inter-particle distance, and that is
	 * thus 1.04 parsecs or 3.4 light years.
	 */
	@Range(min = 1, max= 20) @Default(10)
	@Group("spacetravel")
	public byte longestTravelTurns;

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
	@Percent @Default(5)
	@Group("diplomacy")
	public byte reputationDrain;

	/**
	 * Number of points each {@link Player} may spend to select {@link Trait}s.
	 */
	@Range(max = 100) @Default(20)
	@Group("race")
	public byte evolotionPointContingent;

	/**
	 * Maximum number of individual {@link Trait}s each {@link Player} may select
	 */
	@Range(max = 25) @Default(10)
	@Group("race")
	public byte maxTraitsSelection;

	/**
	 * The {@link Squad#morale} bonus the defending party gets in case of an attack.
	 */
	@Percent @Default(25)
	@Group("combat")
	public byte defenderMoraleBonus;
}
