package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Essence;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("galaxy")
public final class Galaxy extends Essence {

	public Refs<SolarSystem> solarSystems;
}
