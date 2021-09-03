package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Indicator;

final class BitsTags extends FixedBitsTagPer<Indicator> implements Tags {

    BitsTags(Range<Indicator> of) {
        super(of);
    }

}
