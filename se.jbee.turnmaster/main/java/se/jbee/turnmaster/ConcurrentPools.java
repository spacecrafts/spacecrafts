package se.jbee.turnmaster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class ConcurrentPools implements Pools {

    private final Engine.Runtime runtime;

    private final Map<Class<?>, Range<?>> ranges = new ConcurrentHashMap<>();
    private final Map<Class<?>, Index<?>> indexes = new ConcurrentHashMap<>();
    private final Map<Class<?>, Register<?>> registers = new ConcurrentHashMap<>();

    ConcurrentPools(Engine.Runtime runtime) {this.runtime = runtime;}

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Any.Creation> Register<T> register(Class<T> of) {
        return (Register<T>) registers.computeIfAbsent(of,
            key -> runtime.newRegister().newRegister(of, 8));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Any.Definition> Index<T> index(Class<T> of) {
        return (Index<T>) indexes.computeIfAbsent(of,
            key -> runtime.newIndex().newIndex(of, 8));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Any.Grade> Range<T> range(Class<T> of) {
        return (Range<T>) ranges.computeIfAbsent(of,
            key -> runtime.newRange().newRange(of, 8));
    }

}
