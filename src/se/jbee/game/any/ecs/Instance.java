package se.jbee.game.any.ecs;

/**
 * An {@link Entity} that is one of an unknown number of of unconstrained
 * manifestations that can be equal or different in their attributes. Therefore
 * they are only identified by a {@link Serial} that is unique for each concrete
 * {@link Instance} type.
 *
 * {@link Instance}s are non-{@link Constant}s.
 */
public abstract class Instance extends Entity {

}
