package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.comp.Name;

/**
 * {@link Constant}s are entities loaded from game files initially. In contrast
 * to the {@link EntityType#serial} that only is valid within the context of a
 * game {@link State} the codes provided by constants are usable in a static
 * context and are used to identify a particular entity game {@link State}
 * independent.
 *
 * {@link Constant}s are also special in the way that a game {@link State}
 * loaded is completed with further {@link Constant} data even when loaded with
 * constants to make sure that all constants supported by the code running are
 * known in the game. If they are not referenced they might not be stored.
 */
public abstract class Constant extends EntityType {

	public Name name;

	public CharSequence description() {
		//TODO a description is loaded on demand from a text file with the same name as the constants name
		return null;
	}

	public abstract char groupCode();
	public abstract char itemCode();
}
