package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * Tracks the progress of a the construction of a single {@link Module}.
 *
 * {@link Construction}s in a {@link Colony} can be halted and continued freely at any point.
 * {@link Construction}s on a {@link Spacestation} or {@link Spacecraft} cost points to shift.
 */
@Entity("construction")
@Deprecated // construction process is tracked at the Frame and Device level directly as one also can "extend" empty cells later on.
public final class Construction extends Manifestation {

	Module.Ref constructed;
	Frame.Ref<?> buildAt;
	int pointsCompleted;
}
