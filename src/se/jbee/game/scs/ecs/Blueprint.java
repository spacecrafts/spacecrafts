package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;

/**
 * A {@link Blueprint} is the top-most {@link Layout}.
 *
 * It is a building plan for a {@link Spacecraft}, {@link Spacestation} or
 * {@link Colony}.
 *
 * The simplest {@link Blueprint} contains a single {@link Module}.
 *
 * {@link Module} later can be re-composed to {@link Blueprint}s.
 */
@EntityType("blueprint")
public final class Blueprint extends Layout {

	public static final class Ref extends ShortRef<Blueprint> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Blueprint> entityType() {
			return Blueprint.class;
		}
	}

	/**
	 * A potential parent link in case this {@link Blueprint} once was derived from another one.
	 */
	public Blueprint.Ref basedUpon;
	public Refs<Module> modules;

}
