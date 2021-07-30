package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.Name;
import se.jbee.game.any.ecs.meta.EntityType;

/**
 * So far just an idea. An {@link Incident} is an event like discovery of
 * foreign agents, actions of espionage or sabotage.
 */
@EntityType("incident")
public final class Incident extends Event {

	/**
	 * {@link Incident}s always occur between two players, the
	 * {@link Event#affectedPlayer} and the {@link #otherAffectedPlayer}.
	 */
	public Player.Ref otherAffectedPlayer;
	/**
	 * Title generated from the origins of the incident.
	 */
	public Name becameKnownAs;
}
