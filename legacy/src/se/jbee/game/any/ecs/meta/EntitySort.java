package se.jbee.game.any.ecs.meta;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface EntitySort {

	Sort value();
	
	enum Sort { 
		
		/**
		 * Constants are fixed sets of basic building blocks in the game created from game data files.
		 * Once created instances are "immutable" and shared.
		 */
		CONSTANT, 
		
		SYSTEM, 
		
		DEVICE
	}
}
