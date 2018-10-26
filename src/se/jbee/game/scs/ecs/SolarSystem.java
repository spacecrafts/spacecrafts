package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.comp.Coordinate;

@Entity("solarsystem")
public final class SolarSystem extends Fabrication {

	public static final class Ref extends ShortRef<SolarSystem> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<SolarSystem> entityType() {
			return SolarSystem.class;
		}
	}
	@NonNegative
	public int starMass;
	public Coordinate location;
	public RGB starHue;
	StarFamily.Ref starFamily;
	Refs<Planet> localPlanets;
	Refs<Fleet> localFleets;
}
