package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Aggregating;
import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Range;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Technology;
import se.jbee.game.scs.ecs.constant.Trait;
import se.jbee.game.scs.ecs.system.System;

@Entity("race")
public final class Race extends Fabrication implements Aggregating {

	public static final class Ref extends ByteRef<Race> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Race> entityType() {
			return Race.class;
		}
	}

	public Refs<Trait> traits;
	public Refs<Ability> initialAbilities;
	public Refs<System> initialEquipments;
	public Refs<Technology> initialTechnologies;

	@Range(max = 100) @Aggregated
	public byte totalEvolutionPointsSpend;

	@Override
	public void updateAggregated(State state) {
		for (int i = 0; i < traits.size(); i++) {
			Trait trait = state.entity(traits, i);
			totalEvolutionPointsSpend += trait.evolutionPoints;
		}

	}
}
