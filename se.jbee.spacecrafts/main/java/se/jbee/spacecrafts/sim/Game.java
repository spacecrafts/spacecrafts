package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.Algorithm;
import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.state.Index;
import se.jbee.spacecrafts.sim.state.Pool;
import se.jbee.spacecrafts.sim.state.Range;
import se.jbee.spacecrafts.sim.state.Register;

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

            Range<Resourcing.Indicator> indicators,
            Range<Resourcing.Property> properties,
            Range<Resourcing.Resource> resources,
            Range<Resourcing.Influence> influences,
            Index<Resourcing.Classification> classifications,
            Index<Resourcing.Domain> domains,
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
            pools.alias(Resourcing.Indicator.class, this::indicators);
        }
    }

    public interface Pools {

        <T extends Entity> void alias(Class<T> type, Supplier<? extends Pool<T>> by);

        <T extends Entity> Pool<T> entities(Class<T> type);
    }

}
