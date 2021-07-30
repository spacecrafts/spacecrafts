package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Informal for fields that hold values that are meant "per cell" 
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PerCell {

}
