package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Algorithm;
import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.collection.Index;
import se.jbee.spacecrafts.sim.collection.Pool;
import se.jbee.spacecrafts.sim.collection.Range;

import java.util.function.Supplier;

public record Game(
        Entities entities
        // Flux<Processing.Event> delayed
) {

    public record Settings() {}

    public record Entities(
            Pools pools,
            Index<Algorithm<?>> algorithms,

            Index<Any.Control> controls,
            Index<Any.ControlGroup<?>> controlGroups,

            Index<Resourcing.Tag> tags,
            Range<Resourcing.TagGroup> tagGroups,
            Range<Resourcing.Property> properties,
            Range<Resourcing.PropertyGroup> propertyGroups,
            Range<Resourcing.Resource> resources,
            Range<Resourcing.Influence> influences,
            Index<Resourcing.Substance> substances,

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
            Pool<Governing.Leader> leaders,

            Pool<Trading.Trade> trades,
            Pool<Trading.Deal> deals
    ) {
        public Entities {
            pools.alias(Resourcing.Tag.class, this::tags);
        }
    }

    public interface Pools {

        <T extends Entity> void alias(Class<T> type, Supplier<? extends Pool<T>> by);

        <T extends Entity> Pool<T> entities(Class<T> type);
    }

}
