package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Resourcing;
import se.jbee.spacecrafts.sim.Resourcing.Effect;
import se.jbee.turnmaster.data.Any.Property;
import se.jbee.turnmaster.data.Q;

public interface Processes {

    static Resourcing.Process inherent(Property of, int n) {
        return new Resourcing.Process(null, Q.empty(), Q.empty(),
            Q.single(new Effect(n, of, true)));
    }
}
