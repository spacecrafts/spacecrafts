package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Indicator;

/**
 * {@link Tags} are like a dynamic enum set. {@link Any.Classification}s can be
 * used for a dynamic groupings.
 */
public interface Tags extends TagPer<Indicator> {

    static Tags newDefault(Range<Indicator> of) {
        return new BitsTags(of);
    }

    @FunctionalInterface
    interface Factory {

        Tags newTags(Range<Indicator> of);
    }

}
