package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import se.jbee.game.any.ecs.Aggregating;

/**
 * Used to mark fields that hold values computed from other values reachable
 * from fields of the same entity in a way that they could be considered as part
 * of the entities data even though they might be values of sub-entities.
 * Usually such values summarise a property of a list of similar or different
 * elements.
 *
 * {@link Aggregated} annotated fields are not stored but recomputed on load to
 * auto-correct potential code defects in aggregation routines.
 *
 * A class with {@link Aggregated} annotated fields must implement the
 * {@link Aggregating} interface.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Aggregated {

	String info() default "";
}
