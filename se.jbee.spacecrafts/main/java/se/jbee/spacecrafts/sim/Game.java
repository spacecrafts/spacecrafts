package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.engine.*;

public record Game(
        Engine.Runtime runtime,
        Turn turn,
        Objects objects
        // Flux<Processing.Event> delayed
) {

    public record Settings() {}

    public record Objects(
            Pools pools,

            Range<Any.Indicator> indicators,
            Index<Any.Classification> classifications,
            Range<Any.Property> properties,
            Index<Any.Domain> domains,

            Range<Resourcing.Resource> resources,
            Range<Resourcing.Influence> influences,
            Index<Resourcing.Phenomenon> phenomena,
            Index<Resourcing.Substance> substances,

            Index<Crafting.Component> components,
            Register<Crafting.Craft> crafts,
            Register<Crafting.Deck> decks,

            Register<Conquering.Colony> colonies,
            Register<Conquering.LunarOutpost> outposts,
            Register<Conquering.OrbitalStation> orbitals,
            Register<Conquering.Spaceship> spaceships,
            Register<Conquering.SpaceStation> stations,
            Register<Conquering.Fleet> fleets,
            Register<Conquering.MercenaryUnit> mercenaries,

            Register<Conquering.Galaxy> galaxies,
            Register<Conquering.SolarSystem> systems,
            Register<Conquering.Planet> planets,
            Register<Conquering.Moon> moons,

            Register<Governing.Fraction> fractions,
            Index<Governing.Trait> traits,
            Index<Governing.Sphere> spheres,
            Register<Governing.Leader> leaders,

            Register<Trading.Trade> trades,
            Register<Trading.Bid> bids,
            Register<Trading.Deal> deals,
            Register<Trading.Sale> sales,
            Register<Trading.Hire> hires,
            Register<Trading.Mission> missions
    ) {

        public Objects(Engine.Runtime runtime) {
            this(runtime.newPools().newPools(runtime));
        }

        private Objects(Pools pools) {
            this(pools, pools.range(Any.Indicator.class),
                    pools.index(Any.Classification.class),
                    pools.range(Any.Property.class),
                    pools.index(Any.Domain.class),
                    pools.range(Resourcing.Resource.class),
                    pools.range(Resourcing.Influence.class),
                    pools.index(Resourcing.Phenomenon.class),
                    pools.index(Resourcing.Substance.class),
                    pools.index(Crafting.Component.class),
                    pools.register(Crafting.Craft.class),
                    pools.register(Crafting.Deck.class),
                    pools.register(Conquering.Colony.class),
                    pools.register(Conquering.LunarOutpost.class),
                    pools.register(Conquering.OrbitalStation.class),
                    pools.register(Conquering.Spaceship.class),
                    pools.register(Conquering.SpaceStation.class),
                    pools.register(Conquering.Fleet.class),
                    pools.register(Conquering.MercenaryUnit.class),
                    pools.register(Conquering.Galaxy.class),
                    pools.register(Conquering.SolarSystem.class),
                    pools.register(Conquering.Planet.class),
                    pools.register(Conquering.Moon.class),
                    pools.register(Governing.Fraction.class),
                    pools.index(Governing.Trait.class),
                    pools.index(Governing.Sphere.class),
                    pools.register(Governing.Leader.class),
                    pools.register(Trading.Trade.class),
                    pools.register(Trading.Bid.class),
                    pools.register(Trading.Deal.class),
                    pools.register(Trading.Sale.class),
                    pools.register(Trading.Hire.class),
                    pools.register(Trading.Mission.class));
        }
    }

    public Numbers newNumbers() {
        return runtime.newNumbers().newNumbers(objects.properties);
    }

    public <T extends Any.Entity> Flux<T> newFlux(Class<T> of) {
        return runtime.newFlux().newFlux(objects.pools.pool(of));
    }

    public <T extends Any.Definition> Top<T> newTop(Class<T> of) {
        return newTop(4, objects().pools.pool(of).size());
    }

    public <T> Top<T> newTop(int initialCapacity, int maxCapacity) {
        return runtime().newTop().newTop(initialCapacity, maxCapacity);
    }
}
