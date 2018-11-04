package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.Player;
import se.jbee.game.scs.ecs.Race;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Resource;
import se.jbee.game.scs.ecs.constant.Trait;
import se.jbee.game.scs.ecs.device.Supplier;

/**
 * A {@link SupplySystem} is a component that yields some economic {@link Resource}s
 * (but might also consume others).
 */
@Entity("supplysystem")
public final class SupplySystem extends System {

	public static final class Ref extends System.Ref<SupplySystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<SupplySystem> entityType() {
			return SupplySystem.class;
		}
	}

	@Override
	public Area area() {
		return Area.SUPPLY;
	}

	public boolean staffRequired;

	/**
	 * Some facilities usually cannot be automated, e.g. an academy that creates
	 * wisdom requires a manual process.
	 *
	 * However, some {@link Trait}s might give {@link Ability}s that allow to ignore
	 * such restrictions as the race is based on AI so it can automate wisdom as
	 * well.
	 */
	public boolean automationPossible;
	/**
	 * {@link Player}s with the {@link Ability} of automation can chose to automate
	 * the {@link SupplySystem} which increases the output but might come with a
	 * {@link #cultureLossByAutomation}.
	 */
	public boolean automated;

	/**
	 * The production points added by each worker. When automated workers are
	 * considered to work "in a production line", hence have 2 neighbours each.
	 */
	@NonNegative
	public byte workerProductionPoints;
	/**
	 * The maximum number of workers that can work in a {@link Supplier} of size
	 * one.
	 *
	 * Workers are not assigned to a particular slot but all worker
	 * {@link Supplier}s together have a maximum that caps the maximum number of
	 * possible workers. Population that is not assigned to any {@link Supplier} are
	 * "unemployed".
	 */
	@NonNegative
	public byte workerCapacity;
	/**
	 * The base points of production provided as soon as at least one worker is
	 * working in it. If multiple worker facilities are build workers are virtually
	 * distributed so that maximum number of facilities has at least one worker.
	 */
	@NonNegative
	public byte productionPoints;
	@NonNegative
	public byte housingProvided;
	@NonNegative
	public byte foodGain;
	@NonNegative
	public byte rareMaterialGain;
	@NonNegative
	public byte knowledgeGain;
	@NonNegative
	public byte wisdomGain;
	@NonNegative
	public byte energyGain;
	@NonNegative
	public byte cultureGain;
	/**
	 * Depending on the {@link Trait}s of the {@link Player}s {@link Race}
	 * automation might cause a loss of culture. E.g. a robotic race does not lose
	 * culture. The whole concept of culture might not apply.
	 */
	@NonNegative
	public byte cultureLossByAutomation;

}
