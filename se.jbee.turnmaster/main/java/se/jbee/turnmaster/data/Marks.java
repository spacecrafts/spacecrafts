package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Indicator;

/**
 * {@link Marks} are like a dynamic enum set. {@link Any.Classification}s can be
 * used for a dynamic groupings.
 */
public interface Marks extends MarkPer<Any.Indicator> {

    static Marks newDefault(Range<Indicator> of) {
        return new BitsMarks(of);
    }

    @FunctionalInterface
    interface Factory {

        Marks newMarks(Range<Indicator> of);
    }

}
