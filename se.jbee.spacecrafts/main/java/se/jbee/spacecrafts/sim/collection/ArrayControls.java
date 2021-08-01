package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Any.Code;
import se.jbee.spacecrafts.sim.Any.Control;
import se.jbee.spacecrafts.sim.Any.ControlOption;
import se.jbee.spacecrafts.sim.Any.Controls;

import java.util.NoSuchElementException;

final class ArrayControls<T> implements Controls<T> {

    private final Q<Control> keys;
    private final ControlOption[] values;

    ArrayControls(Q<Control> keys) {
        this.keys = keys;
        this.values = new ControlOption[keys.size()];
        reset();
    }

    @Override
    public void reset() {
        keys.forEach(key -> values[keys.findIndex(key)] = key.initial());
    }

    @Override
    public ControlOption get(Code key) throws NoSuchElementException {
        return get(keys.first(k -> k.header().code().equals(key)));
    }

    @Override
    public ControlOption get(Control key) {
        int index = keys.findIndex(key);
        if (index < 0)
            throw new NoSuchElementException("no such control: " + key);
        return values[index];
    }

    @Override
    public void set(Control key, ControlOption value) {
        int index = keys.findIndex(key);
        if (index < 0)
            throw new IllegalStateException("no such control: " + key);
        values[index] = value;
    }
}
