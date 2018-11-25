package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import se.jbee.game.any.ecs.Entity;

/**
 * Marks classes that represent entities and attaches a game unique name to them
 * that is used in persisted state and to map it back to classes.
 *
 * All persistent state fields of an {@link Entity} class are annotated with
 * {@link Component} to give a unique code for each field within the entity that does
 * not change.
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EntityType {

	/**
	 * @return the globally unique key that identifies the type of {@link Entity}.
	 */
	String value();
}
