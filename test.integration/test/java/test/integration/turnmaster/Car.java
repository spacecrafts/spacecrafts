package test.integration.turnmaster;

import se.jbee.turnmaster.data.Any;

record Car(Any.Created header) implements Any.Creation {

    Car(int serial) {
        this(new Any.Created(serial, Any.Text.EMPTY, 1));
    }
}
