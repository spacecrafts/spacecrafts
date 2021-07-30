package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Composition;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Positive;

@EntityType("offer")
public final class Offer extends Composition {

	public Player.Ref toPlayer;
	public Colony.Ref providingColonies;
	public Refs<Colony> receivingColonies;
	@NonNegative
	public int numberOfTurns;
	@Positive
	public int quantity;
	@NonNegative
	public int price;
}
