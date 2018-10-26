package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * Tracks the progress of a the construction of a single {@link Segment}.
 *
 * {@link Construction}s in a {@link Colony} can be halted and continued freely at any point.
 * {@link Construction}s on a {@link Spacestation} or {@link Spacecraft} cost points to shift.
 */
@Entity("construction")
public final class Construction extends Manifestation {

	Segment.Ref constructed;
	Assembly.Ref location;
	int pointsCompleted;
}
