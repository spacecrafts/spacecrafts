package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to annotate {@link Enum} constants with a code so that load/store can
 * map these automatically.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Code {

		char value();
}
