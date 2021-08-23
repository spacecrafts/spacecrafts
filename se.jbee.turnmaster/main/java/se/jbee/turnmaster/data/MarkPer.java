package se.jbee.turnmaster.data;

public interface MarkPer<K extends Any.Entity> extends Collection<K> {

    @FunctionalInterface
    interface Factory {

        <T extends Any.Definition> Marks newMarks(Index<T> of);
    }

    /*
    API
     */

    boolean has(K key);

    void set(K key, boolean value);

    void clear();

    void zero(MarkPer<K> zeros);

    default void set(K key) {
        set(key, true);
    }

    default void unset(K key) {
        set(key, false);
    }

    @Override
    default boolean contains(K key) {
        return has(key);
    }
}
