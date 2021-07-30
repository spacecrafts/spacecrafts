package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks settings that represent player preferences that a player might change
 * during the game.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Preference {
	//marker
}
