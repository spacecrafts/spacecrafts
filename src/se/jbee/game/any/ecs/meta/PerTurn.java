package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Informal for fields holding values that are meant "per turn". 
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PerTurn {

}
