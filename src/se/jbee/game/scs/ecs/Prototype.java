package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * A {@link Prototype} is the top-most {@link StructuralUnit}.
 *
 * It is a building plan for a {@link Spacecraft}, {@link Spacestation} or
 * {@link Colony}. This plan has one or more {@link Tier}s each having one or
 * more {@link Segment}s.
 *
 * The simplest {@link Prototype} has a single {@link Tier} containing a single
 * {@link Segment}.
 *
 * {@link Segment} later can be re-composed to new {@link Tier}s and
 * {@link Prototype}s.
 */
@Entity("prototype")
public final class Prototype extends StructuralUnit {

	public static final class Ref extends ShortRef<Prototype> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Prototype> entityType() {
			return Prototype.class;
		}
	}

	public Refs<Tier> tiers;

}
