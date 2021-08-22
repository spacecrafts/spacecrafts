package se.jbee.turnmaster;

/**
 * {@link Pool} management.
 * <p>
 * Provides a single instance for each type from {@link Register}, {@link Index}
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

    @SuppressWarnings("unchecked")
    default <T extends Any.Entity> Pool<T> pool(Class<T> of) {
        if (Any.Creation.class.isAssignableFrom(of))
            return (Pool<T>) register((Class<? extends Any.Creation>) of);
        if (Any.Grade.class.isAssignableFrom(of))
            return (Pool<T>) range((Class<? extends Any.Grade>) of);
        if (Any.Definition.class.isAssignableFrom(of))
            return (Pool<T>) index((Class<? extends Any.Definition>) of);
        throw new UnsupportedOperationException();
    }
}
