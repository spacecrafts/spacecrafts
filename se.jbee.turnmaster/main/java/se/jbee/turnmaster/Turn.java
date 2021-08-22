package se.jbee.turnmaster;

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

    public boolean after(int turn) {
        return current > turn;
    }

    public boolean before(int turn) {
        return current < turn;
    }
}
