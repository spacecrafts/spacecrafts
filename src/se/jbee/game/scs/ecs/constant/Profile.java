package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.Leader;

/**
 * A {@link Profile} is like a template for {@link Leader}s.
 */
@EntityType("&profile")
public final class Profile extends Preselection {

	//TODO what attracts the leader
	//TODO what effect does the leader have
	//TODO where does the leader want to work (colony, ship, station)
	//TODO how well does the leader work with other leader? should one platform support multiple leaders?)
}
