package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Essence;
import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.comp.Coordinate;
import se.jbee.game.scs.ecs.constant.ClassOfStars;

@EntityType("solarsystem")
public final class SolarSystem extends Essence {

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
	ClassOfStars.Ref starFamily;
	Refs<Planet> localPlanets;
	Refs<Fleet> localFleets;
}
