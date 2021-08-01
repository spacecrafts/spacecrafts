package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Algorithm;
import se.jbee.spacecrafts.sim.collection.Index;
import se.jbee.spacecrafts.sim.collection.Pool;
import se.jbee.spacecrafts.sim.collection.Range;

public record Game(Entities entities) {

    public record Settings() {}

    public record Entities(
            Index<Algorithm<?>> algorithms,

            Index<Any.Control> controls,
            Index<Any.ControlGroup<?>> controlGroups,

            Index<Resourcing.Tag> tags,
            Range<Resourcing.TagGroup> tagGroups,
            Range<Resourcing.Property> properties,
            Range<Resourcing.PropertyGroup> propertyGroups,
            Range<Resourcing.Resource> resources,
            Range<Resourcing.Influence> manifestations,

            Index<Crafting.Component> components,
            Pool<Crafting.Craft> crafts,
            Pool<Crafting.Deck> decks,

            Pool<Conquering.Colony> colonies,
            Pool<Conquering.LunarBase> bases,
            Pool<Conquering.OrbitalStation> orbitals,
            Pool<Conquering.Spaceship> spaceships,
            Pool<Conquering.SpaceStation> stations,

            Pool<Conquering.Galaxy> galaxies,
            Pool<Conquering.SolarSystem> systems,
            Pool<Conquering.Plant> planets,
            Pool<Conquering.Moon> moons,

            Pool<Governing.Fraction> fractions,
            Pool<Governing.Leader> leaders
    ) {}

}
