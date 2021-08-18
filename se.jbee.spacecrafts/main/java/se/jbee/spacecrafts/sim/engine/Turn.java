package se.jbee.spacecrafts.sim.engine;

public final class Turn {

    private int current;

    public Turn(int initial) {
        this.current = initial;
    }

    public int current() {
        return current;
    }

    public void advance() {
        current++;
    }
}
