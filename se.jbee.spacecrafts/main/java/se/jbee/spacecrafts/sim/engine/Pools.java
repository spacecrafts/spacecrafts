package se.jbee.spacecrafts.sim.engine;

/**
 * {@link Pool} management.
 * <p>
 * Provides a single instance for each type of {@link Register}, {@link Index}
 * or {@link Range}.
 */
public interface Pools {

    static Pools newDefault(Engine.Runtime runtime) {
        return new ConcurrentPools(runtime);
    }

    interface Factory {
        Pools newPools(Engine.Runtime runtime);
    }

    <T extends Any.Creation> Register<T> register(Class<T> of);

    <T extends Any.Definition> Index<T> index(Class<T> of);

    <T extends Any.Grade> Range<T> range(Class<T> of);

}
