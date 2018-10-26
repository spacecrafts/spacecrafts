package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.meta.NonNegative;

public abstract class Event extends Manifestation {

	/**
	 * The {@link Game#turn} the event happened.
	 */
	@NonNegative
	public int occuredInTurn;
	/**
	 * The {@link Player} that was affected
	 */
	public Player.Ref affectedPlayer;
}
