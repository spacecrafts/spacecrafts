package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Planet;
import se.jbee.game.scs.ecs.Spacecraft;
import se.jbee.game.scs.ecs.Spacestation;

/**
 * A {@link StabilisationSystem} creates artificial gravitation on a {@link Spacecraft}
 * or {@link Spacestation} or a low-G or high-G {@link Planet} to remove low-G
 * or high-G production penalty.
 *
 * The benefit of planetary production is that it often does not need
 * {@link StabilisationSystem}s what reduced energy need and use of space.
 */
@Entity("stabilisationsystem")
public final class StabilisationSystem extends System {

	public static final class Ref extends System.Ref<StabilisationSystem> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<StabilisationSystem> entityType() {
			return StabilisationSystem.class;
		}
	}

	@Override
	public Area area() {
		return Area.SUPPORT;
	}
}
