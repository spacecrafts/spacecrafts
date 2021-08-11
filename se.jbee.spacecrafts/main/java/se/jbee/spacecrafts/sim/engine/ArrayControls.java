package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any.Control;
import se.jbee.spacecrafts.sim.Any.ControlOption;
import se.jbee.spacecrafts.sim.Any.Controls;
import se.jbee.spacecrafts.sim.state.Stasis;

import java.util.NoSuchElementException;

final class ArrayControls<T> implements Controls<T> {

    private final Stasis<Control> keys;
    private final ControlOption[] values;

    ArrayControls(Stasis<Control> keys) {
        this.keys = keys;
        this.values = new ControlOption[keys.size()];
        reset();
    }

    @Override
    public void reset() {
        //TODO
    }

    @Override
    public ControlOption get(Control key) {
        int index = -1; //TODO
        if (index < 0)
            throw new NoSuchElementException("no such control: " + key);
        return values[index];
    }

    @Override
    public void set(Control key, ControlOption value) {
        int index = -1; //TODO
        if (index < 0)
            throw new IllegalStateException("no such control: " + key);
        values[index] = value;
    }
}
