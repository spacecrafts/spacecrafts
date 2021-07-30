package se.jbee.spacecrafts.sim;

public interface Index<T extends Any.Identifiable> extends Any.Computed {

    T get(int id);

    void update(T e);

    void clear(T e);

    void reindex();
}
