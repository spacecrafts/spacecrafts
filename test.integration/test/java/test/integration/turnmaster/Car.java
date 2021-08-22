package test.integration.turnmaster;

import se.jbee.turnmaster.Any;

record Car(Any.Created header) implements Any.Creation {

    Car(int serial) {
        this(new Any.Created(serial, Any.Text.EMPTY));
    }
}
