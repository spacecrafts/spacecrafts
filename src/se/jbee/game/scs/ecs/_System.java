package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.State;

/**
 * The utility class that bootstraps the ECS of the spacecrafts game.
 */
public class _System {

	public static void init() {
		State.register(Ability.class);
		State.register(Atmosphere.class);
		State.register(Colony.class);
		State.register(Assembly.class);
		State.register(Construction.class);
		State.register(Tier.class);
		State.register(Prototype.class);
		State.register(Discovery.class);
		State.register(Fleet.class);
		State.register(Galaxy.class);
		State.register(Game.class);
		State.register(Habitability.class);
		State.register(Material.class);
		State.register(Moon.class);
		State.register(Offer.class);
		State.register(Orbit.class);
		State.register(Reputation.class);
		State.register(Planet.class);
		State.register(Player.class);
		State.register(Project.class);
		State.register(Race.class);
		State.register(ResearchField.class);
		State.register(Resource.class);
		State.register(Richness.class);
		State.register(Segment.class);
		State.register(Settings.class);
		State.register(Shield.class);
		State.register(ShipFamily.class);
		State.register(SolarSystem.class);
		State.register(Spacecraft.class);
		State.register(Spacestation.class);
		State.register(StarFamily.class);
		State.register(StellarZone.class);
		State.register(Surface.class);
		State.register(Technology.class);
		State.register(Weapon.class);
	}
}
