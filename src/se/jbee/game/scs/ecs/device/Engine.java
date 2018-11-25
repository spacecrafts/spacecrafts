package se.jbee.game.scs.ecs.device;

import static java.lang.Math.floorDiv;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.system.PropulsionSystem;

@EntityType("engine")
public final class Engine extends Device<PropulsionSystem, PropulsionSystem.Ref> {

	public static final class Ref extends Device.Ref<Engine> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Engine> entityType() {
			return Engine.class;
		}
	}

	@NonNegative @Aggregated
	public int totalOrbitalThrust;

	@NonNegative @Aggregated
	public int totalImpulseThrust;

	/**
	 *
	 * Note: wrap 1 means 1 parsec per turn, wrap 9 means 9 per turn
	 */
	@NonNegative @Aggregated
	public int totalWrapThrust;

	@NonNegative @Aggregated
	public int effectiveOrbitalThrust;
	@NonNegative @Aggregated
	public int effectiveImpulseThrust;
	@NonNegative @Aggregated
	public int effectiveWrapThrust;

	@Override
	protected void aggregate(State state, PropulsionSystem system) {
		int totalVirtualCells = totalCells + system.scaleBonus.at(totalCells);
		totalOrbitalThrust = floorDiv(system.orbitalThrust * totalVirtualCells  * cellEfficiency, 100);
		totalImpulseThrust = floorDiv(system.impulseThrust * totalVirtualCells * cellEfficiency, 100);
		totalWrapThrust = floorDiv(system.wrapThrust * totalVirtualCells * cellEfficiency, 100);
		int virtualCells = effectiveCells + system.scaleBonus.at(effectiveCells);
		effectiveOrbitalThrust = floorDiv(system.orbitalThrust * virtualCells  * cellEfficiency, 100);
		effectiveImpulseThrust = floorDiv(system.impulseThrust * virtualCells * cellEfficiency, 100);
		effectiveWrapThrust = floorDiv(system.wrapThrust * virtualCells * cellEfficiency, 100);
	}

}
