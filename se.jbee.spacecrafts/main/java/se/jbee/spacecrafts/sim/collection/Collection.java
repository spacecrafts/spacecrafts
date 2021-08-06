package se.jbee.spacecrafts.sim.collection;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection<T> {
    int size();

    void forEach(Consumer<? super T> f);

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(Predicate<? super T> test);
}
