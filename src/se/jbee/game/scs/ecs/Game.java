package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Essence;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;

@EntityType("game")
public final class Game extends Essence {

	@NonNegative
	public int turn;

	public Settings.Ref settings;
	public Refs<Player> players;
	public Refs<Galaxy> galaxies;

}
