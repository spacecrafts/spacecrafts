package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.collection.Flux;
import se.jbee.spacecrafts.sim.collection.Pool;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

public class BitMaskFlux<T extends Entity> implements Flux<T> {

    private final Pool<T> entities;
    private long[] words;
    private int size = 0;
    private int min = -1;
    private int max = -1;

    BitMaskFlux(Pool<T> entities) {
        this.entities = entities;
        this.words = new long[(entities.span() / 64) + 1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Consumer<? super T> f) {
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            if (word != 0L) {
                int from = Long.numberOfTrailingZeros(word);
                int to = 64 - Long.numberOfLeadingZeros(word);
                long mask = 1L << from;
                for (int j = from; j < to; j++) {
                    if ((word & mask) != 0) f.accept(entities.get(i * 64 + j));
                    mask = mask << 1;
                }
            }
        }
    }

    @Override
    public T first(Predicate<? super T> test) {
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            if (word != 0L) {
                int from = Long.numberOfTrailingZeros(word);
                int to = 64 - Long.numberOfLeadingZeros(word);
                long mask = 1L << from;
                for (int j = from; j < to; j++) {
                    if ((word & mask) != 0) {
                        T e = entities.get(i * 64 + j);
                        if (test.test(e)) return e;
                    }
                    mask = mask << 1;
                }
            }
        }
        return null;
    }

    @Override
    public boolean contains(Predicate<? super T> test) {
        return first(test) != null;
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
        long mask = 1L << (index % 64);
        if ((before | mask) != before) {
            words[wordIndex] |= mask;
            size++;
            min = Math.min(min, index);
            max = Math.max(max, index);
        }
    }

    @Override
    public void add(Flux<T> added) {
        if (added instanceof BitMaskFlux bmf) {

        } else {
            added.forEach(this::add);
        }
    }

    @Override
    public void remove(T e) {

    }

    @Override
    public void remove(Flux<T> removed) {
        if (removed instanceof BitMaskFlux bmf) {

        } else {
            removed.forEach(this::remove);
        }
    }

    @Override
    public void clear() {
        fill(words, 0L);
    }

    private int index(T e) {
        return e.header().serial();
    }
}
