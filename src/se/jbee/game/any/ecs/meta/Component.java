package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import se.jbee.game.any.ecs.Entity;

/**
 * Used to mark persisted state fields within an {@link EntityType} to give each
 * field a code that is unique and stable within the entity.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Component {

	/**
	 * @return The ID of the component within the context of its {@link Entity}
	 *         type that is identified by {@link EntityType}
	 */
	byte value();

	/**
	 * @return the alternative name used in game data files to identify the
	 *         component. Only fields that are set in such files should get it.
	 */
	String alias() default "";
}
