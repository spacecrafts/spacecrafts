package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.collection.Flux;
import se.jbee.spacecrafts.sim.collection.Pool;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

public class BitMaskFlux<T extends Entity> implements Flux<T> {

    private long[] words;
    private int size = 0;
    private int min = -1;
    private int max = -1;

    BitMaskFlux(Pool<T> entities) {
        this.words = new long[(entities.span() / 64) + 1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Consumer<? super T> f) {

    }

    @Override
    public boolean contains(Predicate<? super T> test) {


        return false;
    }

    @Override
    public boolean contains(T e) {
        int index = index(e);
        int wordIndex = index / 64;
        if (wordIndex >= words.length) return false;
        return (words[wordIndex] & (1L << (index % 64))) != 0;
    }

    @Override
    public void add(T e) {
        int index = index(e);
        int wordIndex = index / 64;
        if (wordIndex >= words.length) words = copyOf(words, wordIndex + 1);
        long before = words[wordIndex];
        words[wordIndex] |= 1L << (index % 64);
        if (before != words[wordIndex]) {
            size++;
            min = Math.min(min, index);
            max = Math.max(max, index);
        }
    }

    @Override
    public void remove(T e) {

    }

    @Override
    public void clear() {
        fill(words, 0L);
    }

    private int index(T e) {
        return e.header().serial();
    }
}
