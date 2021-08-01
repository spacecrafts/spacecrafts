package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Resourcing.Numbers;
import se.jbee.spacecrafts.sim.Resourcing.Property;

import static java.lang.Math.min;
import static java.lang.System.arraycopy;

final class ArrayNumbers implements Numbers {

    private final Range<Property> keys;
    private final int[] values;

    public ArrayNumbers(Range<Property> keys) {
        this.keys = keys;
        this.values = new int[keys.span()];
    }

    @Override
    public int get(Property key) {
        return values[index(key)];
    }

    @Override
    public void set(Property key, int value) {
        values[index(key)] = value;
    }

    @Override
    public void inc(Property key, int delta) {
        values[index(key)] += delta;
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
            at.forEach(this::cap);
        }
    }

    private void cap(Property key, int value) {
        var index = index(key);
        values[index] = min(values[index], value);
    }

    @Override
    public void forEach(NumberConsumer f) {
        for (int i = 0; i < values.length; i++)
            if (values[i] >= 0) f.accept(keys.get(i), values[i]);
    }

    private static int index(Property key) {
        return key.header().serial();
    }
}
