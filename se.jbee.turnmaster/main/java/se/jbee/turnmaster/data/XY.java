package se.jbee.turnmaster.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface XY<T> {

    @FunctionalInterface
    interface Factory {

        <T> XY<T> newXY(Location offset, Dimension capacity);
    }

    record Location(
        int x,
        int y
    ) {

        public Location movedBy(int dx, int dy) {
            return new Location(x + dx, y + dy);
        }
    }

    record Dimension(
        int width,
        int height
    ) {}

    /**
     * @return top left corner from the used rectangle - these are minimum used
     * x and y positions
     */
    Location min();

    /**
     * @return bottom right corner from the used rectangle - these are the
     * maximum used x and y positions
     */
    Location max();

    void trimTo(Dimension capacity);

    default Dimension capacity() {
        return new Dimension(width(), height());
    }

    /**
     * @return number from cells between {@link #min()} and {@link #max()}
     * target x-axis
     */
    default int width() {
        return max().x - min().x;
    }

    /**
     * @return number from cells between {@link #min()} and {@link #max()}
     * target y-axis
     */
    default int height() {
        return max().y - min().y;
    }

    Maybe<T> at(Location xy);

    void forEach(Consumer<T> f);

    void forEachLocation(BiConsumer<Location, T> f);

    T put(Location xy, T e);

    default T clear(Location xy) {
        return put(xy, null);
    }

    // clustering process
    // 1. find all different types from Components used
    // 2. for each component find the clusters
}

