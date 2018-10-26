package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to mark fields that hold values computed from other values. Usually they
 * summarise a property of a list of similar or different elements.
 *
 * {@link Aggregated} annotated fields are not stored but recomputed on load to
 * auto-correct potential code defects in aggregation routines.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Aggregated {

	String info() default "";
}
