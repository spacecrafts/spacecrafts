package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Range;
import se.jbee.game.scs.ecs.constant.Habitability;
import se.jbee.game.scs.ecs.constant.Richness;

@Entity("planet")
public final class Planet extends Host {

	public static final class _Planet extends Host.Ref<Planet> {
	
		public _Planet(short serial) {
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
	public Orbit._Orbit orbit;
	public Habitability._Habitability habitability;
	public Richness.Ref richness;
	public Colony.Ref colony;

}
