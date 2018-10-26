package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Aggregating;
import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.comp.Locations;

/**
 * A {@link Segment} describes the layout of a self-contained unit of a
 * {@link Spacecraft}, a {@link Spacestation} or {@link Colony}.
 *
 * It is not necessarily self-contained in the sense that it functions on its
 * own but that it is a unit that can be composed to a {@link Tier} and a full
 * {@link Prototype}.
 *
 * It is a part of a full {@link Prototype}.
 *
 * Within a {@link Colony} a {@link Segment} is called "district". For
 * {@link Spacecraft}s and {@link Spacestation}s it is called "sector".
 */
@Entity("segment")
public final class Segment extends StructuralUnit implements Aggregating { //a.k.a. Module

	public static final class Ref extends ShortRef<Segment> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Segment> entityType() {
			return Segment.class;
		}
	}

	/**
	 * Each {@link Segment} can be made of a particular frame {@link Material} that
	 * determines the base costs of the structure. This cost is calculated by the
	 * number of {@link Equipment} cells within the {@link Segment}.
	 */
	public Material._Material frameMaterial;
	public Refs<Equipment> equipments;
	public Locations locations;

	@Override
	public void aggretate(State state) {
		int n = equipments.size();
		Material material = state.entity(frameMaterial);
		int materialCosts = material.constructionCosts.at(n);
		totalWeight = n * material.weightPerCell;
		totalStructure = n * material.structurePerCell;
	}

}
