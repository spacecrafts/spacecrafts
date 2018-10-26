package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * A {@link Facility} is a component that yields some economic {@link Resource}
 * (but might also consume others).
 */
@Entity("facility")
public class Facility extends Equipment {

	public static final class _Facility extends Equipment.Ref<Facility> {
	
		public _Facility(short serial) {
			super(serial);
		}
		@Override
		public Class<Facility> entityType() {
			return Facility.class;
		}
	}
	public boolean staffRequired;

	public boolean automationPossible;
	/**
	 * {@link Player}s with the {@link Ability} of automation can chose to automate
	 * the {@link Facility} which increases the output but might come with a
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
	 * The maximum number of workers that can work in one facility unit. Workers are
	 * not assigned to a particular facility but all worker facilities together have
	 * a maximum that caps the maximum number of possible workers. Population that
	 * is not assigned to any facility are "unemployed".
	 */
	@NonNegative
	public byte workerCapacity;
	/**
	 * The base points of production provided as soon as at least one worker is
	 * working in it. If multiple worker facilities are build workers are virtually
	 * distributed so that maximum number of facilities has at least one worker.
	 */
	@NonNegative
	public byte facilityProductionPoints;
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
	@NonNegative
	public byte cultureLossByAutomation;
}
