package test.integration.engine;

import se.jbee.spacecrafts.sim.engine.Any;

record Car(Any.Created header) implements Any.Creation {

    Car(int serial) {
        this(new Any.Created(serial, Any.Text.EMPTY));
    }
}
