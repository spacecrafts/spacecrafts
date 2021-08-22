package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Property;

import static java.lang.Math.min;
import static se.jbee.turnmaster.data.NumberPer.isUndefined;

final class ArrayNumbers extends ArrayFixedNumbersPer<Property> implements Numbers {

    public ArrayNumbers(Range<Property> keys) {
        super(keys);
    }

    @Override
    public Numbers clear() {
        super.clear();
        return this;
    }

    @Override
    public void zero(Numbers zeros) {
        if (zeros instanceof ArrayNumbers anz) {
            for (int i = 0; i < values.length; i++)
                if (!isUndefined(anz.values[i])) values[i] = anz.values[i];
        } else {
            zeros.forEach(this::set);
        }
    }

    @Override
    public void cap(Numbers at) {
        if (at instanceof ArrayNumbers anc) {
            for (int i = 0; i < values.length; i++)
                if (!isUndefined(anc.values[i]))
                    values[i] = min(values[i], anc.values[i]);
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
        if (added instanceof ArrayNumbers ana) {
            for (int i = 0; i < values.length; i++)
                if (!isUndefined(ana.values[i])) values[i] += ana.values[i];
        } else {
            added.forEach(this::add1);
        }
    }

    private void add1(Property key, int delta) {
        add(key, delta);
    }

    @Override
    public void sub(Numbers subtracted) {
        if (subtracted instanceof ArrayNumbers ans) {
            for (int i = 0; i < values.length; i++)
                if (!isUndefined(ans.values[i])) values[i] -= ans.values[i];
        } else {
            subtracted.forEach(this::sub1);
        }
    }

    private void sub1(Property key, int value) {
        values[index(key)] -= value;
    }

}
