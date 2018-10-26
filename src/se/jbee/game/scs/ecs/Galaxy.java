package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("galaxy")
public final class Galaxy extends Fabrication {

	public Refs<SolarSystem> solarSystems;
}
