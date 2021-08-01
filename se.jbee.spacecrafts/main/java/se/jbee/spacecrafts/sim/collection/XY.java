package se.jbee.spacecrafts.sim.collection;

public interface XY<T> {

    int width();

    int height();

    T at(int x, int y);

    T put(int x, int y, T e);

    // clustering process
    // 1. find all different types of Components used
    // 2. for each component find the clusters
}

