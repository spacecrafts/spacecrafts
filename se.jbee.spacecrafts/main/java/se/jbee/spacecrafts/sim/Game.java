package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.*;
import se.jbee.spacecrafts.sim.engine.Any.Entity;

import java.util.function.Supplier;

public record Game(
        Engine.Runtime runtime,
        Entities entities
        // Flux<Processing.Event> delayed
) {

    public record Settings() {}

    public record Entities(
            Pools pools,

            Index<Any.Control> controls,
            Index<Any.ControlGroup<?>> controlGroups,

            Range<Any.Indicator> indicators,
            Range<Any.Property> properties,
            Range<Resourcing.Resource> resources,
            Range<Resourcing.Influence> influences,
            Index<Any.Classification> classifications,
            Index<Any.Domain> domains,
            Index<Resourcing.Phenomenon> phenomena,
            Index<Resourcing.Substance> substances,

            Index<Crafting.Component> components,
            Register<Crafting.Craft> crafts,
            Register<Crafting.Deck> decks,

            Register<Conquering.Colony> colonies,
            Register<Conquering.LunarBase> bases,
            Register<Conquering.OrbitalStation> orbitals,
            Register<Conquering.Spaceship> spaceships,
            Register<Conquering.SpaceStation> stations,

            Register<Conquering.Galaxy> galaxies,
            Register<Conquering.SolarSystem> systems,
            Register<Conquering.Plant> planets,
            Register<Conquering.Moon> moons,

            Register<Governing.Fraction> fractions,
            Index<Governing.Trait> traits,
            Index<Governing.Sphere> spheres,
            Register<Governing.Leader> leaders,

            Register<Trading.Trade> trades,
            Register<Trading.Deal> deals
    ) {
        public Entities {
            pools.alias(Any.Indicator.class, this::indicators);
        }
    }

    public interface Pools {

        <T extends Entity> void alias(Class<T> type, Supplier<? extends Pool<T>> by);

        <T extends Entity> Pool<T> entities(Class<T> type);
    }

}
