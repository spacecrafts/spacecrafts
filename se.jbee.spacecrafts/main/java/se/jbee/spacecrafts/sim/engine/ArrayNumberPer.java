package se.jbee.spacecrafts.sim.engine;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;
import static se.jbee.spacecrafts.sim.engine.NumberPer.isNaN;

abstract class ArrayNumberPer<K extends Any.Entity> implements NumberPer<K> {

    protected final Pool<K> keys;

    ArrayNumberPer(Pool<K> keys) {
        this.keys = keys;
    }

    @Override
    public final int get(K key) {
        int value = at(index(key));
        if (value < 0) throw new NoSuchElementException(key.toString());
        return value;
    }

    @Override
    public final Maybe<Value<K>> first(Predicate<? super Value<K>> test) {
        for (int i = 0; i < length(); i++)
            if (!isNaN(at(i))) {
                var e = new Value<>(keys.get(i), at(i));
                if (test.test(e)) return Maybe.some(e);
            }
        return Maybe.nothing();
    }

    @Override
    public final void forEach(java.util.function.Consumer<? super Value<K>> f) {
        forEach(((key, value) -> f.accept(new Value<>(key, value))));
    }

    @Override
    public final void forEach(Consumer<K> f) {
        for (int i = 0; i < length(); i++)
            if (!isNaN(at(i))) f.accept(keys.get(i), at(i));
    }

    @Override
    public final int size() {
        int c = 0;
        for (int i = 0; i < length(); i++) if (!isNaN(at(i))) c++;
        return c;
    }

    static int index(Any.Entity key) {
        return key.header().serial();
    }

    abstract int at(int index);

    abstract int length();
}

class ArrayFixedNumbersPer<K extends Any.Definition> extends ArrayNumberPer<K> {

    protected final int[] values;

    ArrayFixedNumbersPer(Index<K> keys) {
        super(keys);
        this.values = new int[keys.span() + 1];
    }

    @Override
    int at(int index) {
        return values[index];
    }

    @Override
    int length() {
        return values.length;
    }

    @Override
    public void set(K key, int value) {
        values[index(key)] = value;
    }

    @Override
    public void add(K key, int delta) {
        int index = index(key);
        if (values[index] < 0) {
            values[index] = delta;
        } else {
            values[index] += delta;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(values, NaN);
    }

}

final class ArrayDynamicNumbersPer<K extends Any.Creation> extends ArrayNumberPer<K> {

    private int[] values;

    ArrayDynamicNumbersPer(Register<K> keys) {
        super(keys);
        this.values = new int[0];
    }

    @Override
    int at(int index) {
        return values[index];
    }

    @Override
    int length() {
        return values.length;
    }

    @Override
    public void set(K key, int value) {
        int index = index(key);
        ensureExists(index);
        values[index] = value;
    }

    @Override
    public void add(K key, int delta) {
        int index = index(key);
        ensureExists(index);
        if (values[index] < 0) {
            values[index] = delta;
        } else {
            values[index] += delta;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(values, NaN);
    }

    private void ensureExists(int index) {
        if (index >= values.length) values = copyOf(values, index + 1);
    }
}
