package se.jbee.turnmaster.data;

import java.util.NoSuchElementException;

public interface ConstantPer<K extends Any.Entity> extends Collection<ConstantPer.Value<K>> {

    int Undefined = Integer.MIN_VALUE;

    static boolean isUndefined(int value) {
        return value == Undefined;
    }

    int get(K key) throws NoSuchElementException;

    boolean contains(K key);

    /**
     * @return true, if all defined keys have a zero value
     */
    default boolean isZero() {
        return !first(e -> e.value() != 0).isSome();
    }

    void forEach(Consumer<K> f);

    void forEachKey(java.util.function.Consumer<K> f);

    interface Consumer<K> {

        void accept(K key, int value);
    }

    record Value<K>(
        K key,
        int value
    ) {}
}
