package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;

/**
 * {@link Spacecraft}s and {@link Spacestation}s can have many tiers called
 * decks each having one or more {@link Segment}s.
 *
 * A {@link Colony} can have surface level and underground levels. Some planets
 * suit better then others to dig out underground levels. Some technologies
 * might increase the speed and depth one can dig. Underground levels allow to
 * hide strength and retreat on attacks.
 */
@Entity("tier")
public final class Tier extends StructuralUnit {

	public Refs<Segment> segments;

}
