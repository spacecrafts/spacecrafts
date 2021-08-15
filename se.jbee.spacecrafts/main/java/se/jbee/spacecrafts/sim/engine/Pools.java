package se.jbee.spacecrafts.sim.engine;

public interface Pools {

    static Pools newDefault(Engine.Runtime runtime) {
        return new ConcurrentPools(runtime);
    }

    interface Factory {
        Pools newPools(Engine.Runtime runtime);
    }

    <T extends Any.Creation> Register<T> register(Class<T> of);

    <T extends Any.Definition> Index<T> index(Class<T> of);

    <T extends Any.Quality> Range<T> range(Class<T> of);

}
