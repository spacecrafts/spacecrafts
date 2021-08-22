package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Indicator;

final class BitsMarks extends FixedBitsMarkPer<Indicator> implements Marks {

    BitsMarks(Range<Indicator> of) {
        super(of);
    }

}
