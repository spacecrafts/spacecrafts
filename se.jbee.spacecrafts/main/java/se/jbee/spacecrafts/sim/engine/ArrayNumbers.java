package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.engine.Any.Property;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Math.min;
import static java.lang.System.arraycopy;

final class ArrayNumbers implements Numbers {

    private final Range<Property> keys;
    private final int[] values;

    public ArrayNumbers(Range<Property> keys) {
        this.keys = keys;
        this.values = new int[keys.span() + 1];
    }

    @Override
    public int get(Property key) {
        int value = values[index(key)];
        if (value < 0) throw new NoSuchElementException(key.toString());
        return value;
    }

    @Override
    public void set(Property key, int value) {
        values[index(key)] = value;
    }

    @Override
    public void add(Property key, int delta) {
        int index = index(key);
        if (values[index] < 0) {
            values[index] = delta;
        } else {
            values[index] += delta;
        }
    }

    @Override
    public void zero(Numbers zeros) {
        if (zeros instanceof ArrayNumbers fn) {
            arraycopy(fn.values, 0, values, 0, values.length);
        } else {
            zeros.forEach(this::set);
        }
    }

    @Override
    public void cap(Numbers at) {
        if (at instanceof ArrayNumbers fn) {
            for (int i = 0; i < values.length; i++)
                if (fn.values[i] >= 0) values[i] = min(values[i], fn.values[i]);
        } else {
            at.forEach(this::cap1);
        }
    }

    private void cap1(Property key, int value) {
        var index = index(key);
        values[index] = min(values[index], value);
    }

    @Override
    public void add(Numbers added) {
        if (added instanceof ArrayNumbers fn) {
            for (int i = 0; i < values.length; i++)
                if (fn.values[i] >= 0) values[i] += fn.values[i];
        } else {
            added.forEach(this::add1);
        }
    }

    private void add1(Property key, int delta) {
        add(key, delta);
    }

    @Override
    public void sub(Numbers subtracted) {
        if (subtracted instanceof ArrayNumbers fn) {
            for (int i = 0; i < values.length; i++)
                if (fn.values[i] >= 0) values[i] -= fn.values[i];
        } else {
            subtracted.forEach(this::sub1);
        }
    }

    private void sub1(Property key, int value) {
        values[index(key)] -= value;
    }

    @Override
    public void clear() {
        Arrays.fill(values, -1);
    }

    @Override
    public void forEach(ValueConsumer f) {
        for (int i = 0; i < values.length; i++)
            if (values[i] >= 0) f.accept(keys.get(i), values[i]);
    }

    @Override
    public void forEach(Consumer<? super Value> f) {
        forEach(((key, value) -> f.accept(new Value(key, value))));
    }

    @Override
    public Maybe<Value> first(Predicate<? super Value> test) {
        for (int i = 0; i < values.length; i++)
            if (values[i] >= 0) {
                var e = new Value(keys.get(i), values[i]);
                if (test.test(e)) return Maybe.some(e);
            }
        return Maybe.nothing();
    }

    @Override
    public int size() {
        int c = 0;
        for (int value : values) if (value >= 0) c++;
        return c;
    }

    private static int index(Property key) {
        return key.header().serial();
    }
}
