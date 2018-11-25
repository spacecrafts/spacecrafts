package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Range;
import se.jbee.game.scs.ecs.constant.Habitability;
import se.jbee.game.scs.ecs.constant.Richness;

@EntityType("planet")
public final class Planet extends Base {

	public static final class Ref extends Base.Ref<Planet> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Planet> entityType() {
			return Planet.class;
		}
	}
	public RGB hue;
	@Range(min = 1, max = 10)
	public int solarSystemPosition;
	public Refs<Moon> orbitingMoons;
	public Orbit.Ref orbit;
	public Habitability.Ref habitability;
	public Richness.Ref richness;
	public Colony.Ref colony;

}
