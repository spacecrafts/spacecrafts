package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fiction;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("galaxy")
public final class Galaxy extends Fiction {

	public Refs<SolarSystem> solarSystems;
}
