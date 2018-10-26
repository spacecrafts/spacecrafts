package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.comp.Name;
import se.jbee.game.scs.ecs.Player;
import se.jbee.game.scs.ecs.Player.Ref;

/**
 * A {@link EntityType} that represents player defined compositions.
 */
public abstract class Composition extends Instance {

	public Name name;
	/**
	 * Flag to mark player made compositions that should be kept for later even when
	 * no longer used.
	 */
	public boolean keepUnused = false;

	/**
	 * The {@link Player} who made or currently controls the entity
	 */
	public Player.Ref byPlayer;
}
