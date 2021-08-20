package se.jbee.spacecrafts.sim.engine;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection<T> {

    int size();

    void forEach(Consumer<? super T> f);

    default void forEach(Consumer<? super T> f, Predicate<? super T> filter) {
        forEach(e -> {if (filter.test(e)) f.accept(e);});
    }

    Maybe<T> first(Predicate<? super T> test);

    default Maybe<T> first() {
        return first(e -> true);
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean contains(Predicate<? super T> test) {
        return first(test).isSome();
    }

    default boolean contains(T value) {
        return contains(e -> e.equals(value));
    }
}
