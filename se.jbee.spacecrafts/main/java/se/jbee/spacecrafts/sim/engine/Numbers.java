package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Resourcing;

import java.util.NoSuchElementException;

public interface Numbers {

    static Numbers newDefault(Range<Resourcing.Property> of) {
        return new ArrayNumbers(of);
    }

    int get(Resourcing.Property key) throws NoSuchElementException;

    void set(Resourcing.Property key, int value);

    void add(Resourcing.Property key, int delta);

    void zero(Numbers zeros);

    void add(Numbers added);

    void sub(Numbers subtracted);

    void cap(Numbers at);

    void clear();

    void forEach(NumberConsumer f);

    interface NumberConsumer {
        void accept(Resourcing.Property key, int value);
    }
}
