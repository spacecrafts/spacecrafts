package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

@Entity("game")
public final class Game extends Fabrication {

	@NonNegative
	public int turn;

	public Settings.Ref settings;
	public Refs<Player> players;
	public Refs<Galaxy> galaxies;

}
