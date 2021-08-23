package se.jbee.turnmaster.data;

import java.util.function.UnaryOperator;

public interface Per<K extends Any.Entity, V> extends Collection<Per.Value<K, V>> {

    static <K extends Any.Entity, V> Per<K, V> newDefault(Pool<K> of, int initialSize) {
        return new ArrayPer<>(of, initialSize);
    }

    interface Factory {

        <K extends Any.Entity, V> Per<K, V> newPer(Pool<K> of, int initialSize);

        default <K extends Any.Creation, V> Per<K, V> newPer(Register<K> keys, int initialSize) {
            return newPer((Pool<K>) keys, initialSize);
        }

        default <K extends Any.Definition, V> Per<K, V> newPer(Index<K> keys, int initialSize) {
            return newPer((Pool<K>) keys, initialSize);
        }

        default <K extends Any.Grade, V> Per<K, V> newPer(Range<K> keys, int initialSize) {
            return newPer((Pool<K>) keys, initialSize);
        }
    }

    Maybe<V> get(K key) throws NullPointerException;

    Maybe<V> set(K key, V value) throws NullPointerException;

    Maybe<V> remove(K key);

    void update(K key, UnaryOperator<V> f) throws NullPointerException;

    void clear();

    record Value<K, V>(
        K key,
        V value
    ) {}

}
