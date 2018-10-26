package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * A {@link Project} links a set of {@link Colony}s, {@link Spacecraft}s or
 * {@link Spacestation}s to a common goal.
 *
 * This might include construction of participating members or them reaching a
 * target location.
 */
@Entity("project")
public final class Project extends Composition { // a.k.a Plan

	public static final class Ref extends ShortRef<Project> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Project> entityType() {
			return Project.class;
		}
	}

	@NonNegative
	public int turnStarted;
	@NonNegative
	public int turnCompleted;
	public Refs<Colony> participatingColonies;
	public Refs<Spacestation> participatingStations;
	public Refs<Spacecraft> participatingShips;

	public boolean isCompleted() {
		return turnCompleted > turnStarted;
	}
}
