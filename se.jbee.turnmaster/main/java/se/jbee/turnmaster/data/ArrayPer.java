package se.jbee.turnmaster.data;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayPer<K extends Any.Entity, V> implements Per<K, V> {

    private final Pool<K> keys;
    private Object[] values;
    private int size = 0;

    public ArrayPer(Pool<K> keys, int initialSize) {
        this.keys = keys;
        this.values = new Object[Math.min(initialSize, keys.span() + 1)];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Consumer<? super Value<K, V>> f) {
        for (int i = 0; i < values.length; i++)
            if (values[i] != null) f.accept(getEntry(i));
    }

    private Value<K, V> getEntry(int serial) {
        return new Value<>(keys.get(serial), getUnchecked(serial));
    }

    @SuppressWarnings("unchecked")
    private V getUnchecked(int serial) {
        return serial >= values.length
               ? null
               : (V) values[serial];
    }

    @Override
    public Maybe<Value<K, V>> first(Predicate<? super Value<K, V>> test) {
        for (int i = 0; i < values.length; i++)
            if (values[i] != null && test.test(getEntry(i)))
                return Maybe.some(getEntry(i));
        return Maybe.nothing();
    }

    @Override
    public Maybe<V> get(K key) {
        var e = getUnchecked(key.header().serial());
        return e == null
               ? Maybe.nothing()
               : Maybe.some(e);
    }

    @Override
    public Maybe<V> set(K key, V value) {
        if (value == null) throw new NullPointerException();
        int index = key.header().serial();
        if (index >= values.length)
            values = Arrays.copyOf(values, newCapacity(index));
        var e = getUnchecked(index);
        values[index] = value;
        if (e != null) return Maybe.some(e);
        size++;
        return Maybe.nothing();
    }

    private int newCapacity(int index) {
        return index + 1;
    }

    @Override
    public Maybe<V> remove(K key) {
        int index = key.header().serial();
        var e = getUnchecked(index);
        values[index] = null;
        if (e != null) return Maybe.some(e);
        size--;
        return Maybe.nothing();
    }

    @Override
    public void update(K key, UnaryOperator<V> f) {
        int index = key.header().serial();
        var e = getUnchecked(index);
        if (e != null) {
            V newVal = f.apply(e);
            if (newVal == null) throw new NullPointerException();
            values[index] = newVal;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(values, null);
        size = 0;
    }
}
