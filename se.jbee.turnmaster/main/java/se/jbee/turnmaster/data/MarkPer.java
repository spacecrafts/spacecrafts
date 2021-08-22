package se.jbee.turnmaster.data;

public interface MarkPer<T extends Any.Entity> extends Collection<T> {

    @FunctionalInterface
    interface Factory {

        <T extends Any.Definition> Marks newMarks(Index<T> of);
    }

    /*
    API
     */

    boolean has(T key);

    void set(T key, boolean value);

    void clear();

    void zero(MarkPer<T> zeros);

    default void set(T key) {
        set(key, true);
    }

    default void unset(T key) {
        set(key, false);
    }

    @Override
    default boolean contains(T key) {
        return has(key);
    }
}
