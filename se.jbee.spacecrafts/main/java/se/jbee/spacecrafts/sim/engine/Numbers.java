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

    @FunctionalInterface
    interface Factory {
        Numbers newNumbers(Range<Property> of);
    }

    /*
    API
     */

    int get(Property key) throws NoSuchElementException;

    void set(Property key, int value);

    void add(Property key, int delta);

    /**
     * Sets all properties in this instance with the properties set in zeros.
     * <p>
     * This does keep all properties not set in zeros "as is".
     *
     * @param zeros zero values to apply
     */
    void zero(Numbers zeros);

    void add(Numbers added);

    void sub(Numbers subtracted);

    /**
     * Caps all properties in this instance at the maximum set in cap. Any value
     * smaller than the maximum is kept "as is".
     * <p>
     * This does keep all properties not set in at "as is".
     *
     * @param at maximum values to apply.
     */
    void cap(Numbers at);

    /**
     * Unsets all {@link Property} values in this instance
     */
    void clear();

    void forEach(Consumer f);

    interface Consumer {
        void accept(Property key, int value);
    }
}
