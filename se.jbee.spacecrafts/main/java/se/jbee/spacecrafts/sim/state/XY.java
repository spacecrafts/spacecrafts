package se.jbee.spacecrafts.sim.state;

public interface XY<T> {

    record Location(
            int x,
            int y
    ) {}

    /**
     * @return top left corner of the used rectangle - these are minimum used x
     * and y positions
     */
    Location min();

    /**
     * @return bottom right corner of the used rectangle - these are the maximum
     * used x and y positions
     */
    Location max();

    /**
     * @return number of cells between {@link #min()} and {@link #max()} on
     * x-axis
     */
    default int width() {
        return max().x - min().x;
    }

    /**
     * @return number of cells between {@link #min()} and {@link #max()} on
     * y-axis
     */
    default int height() {
        return max().y - min().y;
    }

    T at(Location xy);

    T put(Location xy, T e);

    // clustering process
    // 1. find all different types of Components used
    // 2. for each component find the clusters
}

