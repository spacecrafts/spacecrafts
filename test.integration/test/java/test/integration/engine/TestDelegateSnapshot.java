package test.integration.engine;

import org.junit.jupiter.api.Test;
import se.jbee.spacecrafts.sim.engine.Any;
import se.jbee.spacecrafts.sim.engine.Q;
import se.jbee.spacecrafts.sim.engine.Snapshot;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static test.integration.utils.Assertions.assertForEach;

class TestDelegateSnapshot {

    record X(int value) implements Any.Computed {}

    private final Snapshot<X> snapshot = Snapshot.newDefault(Q.<X>newDefault(3).concat(
            new X(1),
            new X(2)));

    @Test
    void update_0() {
        assertThrows(NullPointerException.class, () -> snapshot.update(null));
    }

    @Test
    void update() {
        var updated = Q.<X>newDefault(2).concat(new X(4), new X(5), new X(6));
        snapshot.update(updated);
        assertEquals(3, snapshot.size());
        assertForEach(asList(new X(4), new X(5), new X(6)), snapshot::forEach);
    }

    @Test
    void size_0() {
        snapshot.update(Q.newDefault(0));
        assertEquals(0, snapshot.size());
    }

    @Test
    void size() {
        snapshot.update(Q.<X>newDefault(2).concat(new X(7)));
        assertEquals(1, snapshot.size());
    }

    @Test
    void forEach_0() {
        snapshot.update(Q.newDefault(0));
        snapshot.forEach(e -> fail("Should not be called for empty"));
    }

    @Test
    void forEach() {
        snapshot.update(Q.<X>newDefault(2).concat(new X(7)));
        assertForEach(List.of(new X(7)), snapshot::forEach);
    }
}
