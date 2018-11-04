package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * A {@link Prototype} is the top-most {@link Layout}.
 *
 * It is a building plan for a {@link Spacecraft}, {@link Spacestation} or
 * {@link Colony}.
 *
 * The simplest {@link Prototype} contains a single {@link Module}.
 *
 * {@link Module} later can be re-composed to {@link Prototype}s.
 */
@Entity("prototype")
public final class Prototype extends Layout {

	public static final class Ref extends ShortRef<Prototype> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Prototype> entityType() {
			return Prototype.class;
		}
	}

	/**
	 * A potential parent link in case this {@link Prototype} once was derived from another one.
	 */
	public Prototype.Ref basedUpon;
	public Refs<Module> modules;

}
