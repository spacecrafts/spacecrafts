package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.meta.Entity;

/**
 * A {@link Property} is the entity that describes a field in an
 * {@link EntityType}. In contrast to {@link Instance}s and {@link Constant}s it
 * does not make sense to store {@link Property}s in game {@link State} files as
 * the information is about the functional parts. If there is a change the user
 * should get the newest information as it has to be considered more correct and
 * informative.
 */
@Entity("property")
public final class Property extends EntityType {


}
