package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

/**
 * A {@link Stabiliser} creates artificial gravitation on a {@link Spacecraft}
 * or {@link Spacestation} or a low-G or high-G {@link Planet} to remove low-G
 * or high-G production penalty.
 *
 * The benefit of planetary production is that it often does not need
 * {@link Stabiliser}s what reduced energy need and use of space.
 */
@Entity("stabiliser")
public final class Stabiliser extends Equipment {

	public static final class Ref extends Equipment.Ref<Stabiliser> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Stabiliser> entityType() {
			return Stabiliser.class;
		}
	}
}
