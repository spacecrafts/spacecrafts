package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.spacecrafts.sim.Governing.Governed;
import se.jbee.spacecrafts.sim.Trading.Offered;
import se.jbee.turnmaster.Any;
import se.jbee.turnmaster.Any.Composed;
import se.jbee.turnmaster.Any.Created;
import se.jbee.turnmaster.Any.Generated;
import se.jbee.turnmaster.Any.Text;
import se.jbee.turnmaster.Engine;
import se.jbee.turnmaster.RNG;
import se.jbee.turnmaster.Turn;
import se.jbee.turnmaster.data.Flux;
import se.jbee.turnmaster.data.Index;
import se.jbee.turnmaster.data.Marks;
import se.jbee.turnmaster.data.Numbers;
import se.jbee.turnmaster.data.Pools;
import se.jbee.turnmaster.data.Q;
import se.jbee.turnmaster.data.Range;
import se.jbee.turnmaster.data.Register;
import se.jbee.turnmaster.data.Top;
import se.jbee.turnmaster.data.XY;
import se.jbee.turnmaster.eval.Deduction;

public record Game(
    Engine.Runtime runtime,
    Turn turn,
    RNG rng,
    Objects objects
) implements Engine.Game {

    public interface Module extends Engine.Module<Game> {}

    public interface Decision extends se.jbee.turnmaster.eval.Decision<Game> {}

    public interface Byproduct<T> extends se.jbee.turnmaster.eval.Decision.Byproduct<Game, T> {}

    public interface Deducting extends se.jbee.turnmaster.eval.Analysis<Game, Deduction> {}

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
        Register<Crafting.Section> sections,

        Register<Conquering.Colony> colonies,
        Register<Conquering.LunarOutpost> outposts,
        Register<Conquering.OrbitalStation> orbitals,
        Register<Conquering.Spaceship> spaceships,
        Register<Conquering.SpaceStation> stations,
        Register<Conquering.Fleet> fleets,
        Register<Conquering.MercenaryUnit> mercenaries,

        Register<Exploring.Sector> sectors,
        Register<Exploring.SolarSystem> systems,
        Register<Exploring.Planet> planets,
        Register<Exploring.Moon> moons,

        Register<Fraction> fractions,
        Index<Governing.Trait> traits,
        Index<Governing.Sphere> spheres,
        Register<Governing.Leader> leaders,

        Register<Trading.Trade> trades,
        Register<Trading.Bid> bids,
        Register<Trading.Deal> deals,
        Register<Trading.Sale> sales,
        Register<Trading.Mission> missions,
        Register<Trading.Approach> approaches,
        Register<Trading.Hire> hires
    ) {

        public Objects(Engine.Runtime runtime) {
            this(runtime.newPools().newPools(runtime));
        }

        private Objects(Pools pools) {
            this(pools, pools.range(Any.Indicator.class),
                pools.index(Any.Classification.class),
                pools.range(Any.Property.class), pools.index(Any.Domain.class),
                pools.range(Resourcing.Resource.class),
                pools.range(Resourcing.Influence.class),
                pools.index(Resourcing.Phenomenon.class),
                pools.index(Resourcing.Substance.class),
                pools.index(Crafting.Component.class),
                pools.register(Crafting.Craft.class),
                pools.register(Crafting.Section.class),
                pools.register(Conquering.Colony.class),
                pools.register(Conquering.LunarOutpost.class),
                pools.register(Conquering.OrbitalStation.class),
                pools.register(Conquering.Spaceship.class),
                pools.register(Conquering.SpaceStation.class),
                pools.register(Conquering.Fleet.class),
                pools.register(Conquering.MercenaryUnit.class),
                pools.register(Exploring.Sector.class),
                pools.register(Exploring.SolarSystem.class),
                pools.register(Exploring.Planet.class),
                pools.register(Exploring.Moon.class),
                pools.register(Fraction.class),
                pools.index(Governing.Trait.class),
                pools.index(Governing.Sphere.class),
                pools.register(Governing.Leader.class),
                pools.register(Trading.Trade.class),
                pools.register(Trading.Bid.class),
                pools.register(Trading.Deal.class),
                pools.register(Trading.Sale.class),
                pools.register(Trading.Mission.class),
                pools.register(Trading.Approach.class),
                pools.register(Trading.Hire.class));
        }
    }

    public Governed newGoverned(int serial, CharSequence name, Fraction origin) {
        return new Governed(serial, new Text(name), origin);
    }

    public Created newCreated(int serial, CharSequence name) {
        return new Created(serial, new Text(name), turn.current());
    }

    public Generated newGenerated(int serial, CharSequence name) {
        return new Generated(serial, new Text(name), turn.current(),
            rng.nextLong());
    }

    public Composed newComposed(int serial) {
        return new Composed(serial, turn.current());
    }

    public Offered newOffered(int serial, Fraction by) {
        return new Offered(serial, by, turn.current());
    }

    public Numbers newNumbers() {
        return runtime.newNumbers().newNumbers(objects.properties).clear();
    }

    public Marks newMarks() {
        return runtime.newMarks().newMarks(objects.indicators);
    }

    public <T extends Any.Entity> Flux<T> newFlux(Class<T> of) {
        return runtime.newFlux().newFlux(objects.pools.pool(of));
    }

    public <T extends Any.Entity> Top<T> newTop(Class<T> of) {
        return newTop(4, objects().pools.pool(of).size());
    }

    public <T> Top<T> newTop(int initialCapacity, int maxCapacity) {
        return runtime().newTop().newTop(initialCapacity, maxCapacity);
    }

    public <T> Top<T> newTop(int initialCapacity) {
        return runtime().newTop().newTop(initialCapacity, -1);
    }

    public <T> Q<T> newQ(int initialCapacity) {
        return runtime.newQ().newQ(initialCapacity);
    }

    public <T> XY<T> newXY(XY.Location capacity) {
        return runtime.newXY().newXY(capacity);
    }
}
