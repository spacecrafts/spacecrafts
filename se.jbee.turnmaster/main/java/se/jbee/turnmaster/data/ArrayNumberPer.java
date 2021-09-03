package se.jbee.turnmaster.data;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;

abstract class ArrayNumberPer<K extends Any.Entity> implements NumberPer<K> {

    protected final Pool<K> keys;

    ArrayNumberPer(Pool<K> keys) {
        this.keys = keys;
    }

    @Override
    public final int get(K key) {
        int value = at(index(key));
        if (ConstantPer.isUndefined(value))
            throw new NoSuchElementException(key.toString());
        return value;
    }

    @Override
    public boolean contains(K key) {
        return !ConstantPer.isUndefined(at(index(key)));
    }

    @Override
    public final Maybe<ConstantPer.Value<K>> first(Predicate<? super ConstantPer.Value<K>> test) {
        for (int i = 0; i < length(); i++)
            if (!ConstantPer.isUndefined(at(i))) {
                var e = new ConstantPer.Value<>(keys.get(i), at(i));
                if (test.test(e)) return Maybe.some(e);
            }
        return Maybe.nothing();
    }

    @Override
    public void forEachKey(java.util.function.Consumer<K> f) {
        for (int i = 0; i < length(); i++)
            if (!ConstantPer.isUndefined(at(i))) f.accept(keys.get(i));
    }

    @Override
    public final void forEach(java.util.function.Consumer<? super ConstantPer.Value<K>> f) {
        forEach(
            ((key, value) -> f.accept(new ConstantPer.Value<>(key, value))));
    }

    @Override
    public final void forEach(Consumer<K> f) {
        for (int i = 0; i < length(); i++)
            if (!ConstantPer.isUndefined(at(i))) f.accept(keys.get(i), at(i));
    }

    @Override
    public final int size() {
        int c = 0;
        for (int i = 0; i < length(); i++)
            if (!ConstantPer.isUndefined(at(i))) c++;
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
        if (delta == 0) return;
        int index = index(key);
        if (ConstantPer.isUndefined(values[index])) {
            values[index] = delta;
        } else values[index] += delta;
    }

    @Override
    public NumberPer<K> clear() {
        Arrays.fill(values, ConstantPer.Undefined);
        return this;
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
        if (delta == 0) return;
        int index = index(key);
        ensureExists(index);
        if (ConstantPer.isUndefined(values[index])) {
            values[index] = delta;
        } else values[index] += delta;
    }

    @Override
    public NumberPer<K> clear() {
        Arrays.fill(values, ConstantPer.Undefined);
        return this;
    }

    private void ensureExists(int index) {
        if (index >= values.length) values = copyOf(values, index + 1);
    }
}
