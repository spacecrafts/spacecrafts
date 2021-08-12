package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.engine.Any.Property;

import java.util.NoSuchElementException;

public interface Numbers extends Collection<Numbers.Value> {

    record Value(
            Property key,
            int value
    ) {}

    static Numbers newDefault(Range<Property> of) {
        return new ArrayNumbers(of);
    }

    interface Factory {
        Numbers newNumbers(Range<Property> of);
    }

    /*
    API
     */

    int get(Property key) throws NoSuchElementException;

    void set(Property key, int value);

    void add(Property key, int delta);

    void zero(Numbers zeros);

    void add(Numbers added);

    void sub(Numbers subtracted);

    void cap(Numbers at);

    void clear();

    void forEach(ValueConsumer f);

    interface ValueConsumer {
        void accept(Property key, int value);
    }
}
