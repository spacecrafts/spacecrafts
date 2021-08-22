package se.jbee.turnmaster;

import se.jbee.turnmaster.Any.Property;

public interface Numbers extends NumberPer<Property> {

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

    @Override
    Numbers clear();
}
