package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Property;

public interface Numbers extends Constants, NumberPer<Property> {

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
    void zero(Constants zeros);

    void zero(Constants zeros, Collection<Property> filter);

    void add(Constants added);

    void sub(Constants subtracted);

    /**
     * Caps all properties in this instance at the maximum set in cap. Any value
     * smaller than the maximum is kept "as is".
     * <p>
     * This does keep all properties not set in at "as is".
     *
     * @param at maximum values to apply.
     */
    void cap(Constants at);

    @Override
    Numbers clear();

}
