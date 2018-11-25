package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.meta.Component;

/**
 * A {@link ComponentType} is a s simple or complex field type. Implementations
 * do not have to be registered as the fields in the implementations are
 * {@link Component}s again that are serialised and de-serialised by looking at
 * the type that should be created.
 *
 * Any {@link ComponentType} must be immutable in the sense that an
 * {@link Entity} with such field can be duplicated by doing a shallow clone of
 * it.
 */
public interface ComponentType {
	// marker for types that are wrappers on simple components like a reference
}
