package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE})
public @interface Range {

	int min() default 0;
	int max() default Integer.MAX_VALUE ;
}
