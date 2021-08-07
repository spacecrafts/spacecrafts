package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any.Entity;
import se.jbee.spacecrafts.sim.state.Flux;
import se.jbee.spacecrafts.sim.state.Maybe;
import se.jbee.spacecrafts.sim.state.Pool;
import se.jbee.spacecrafts.sim.state.Stasis;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

final class BitMaskFlux<T extends Entity> implements Flux<T>, Stasis<T> {

    private final Pool<T> entities;
    private long[] words;
    private int size = 0;

    BitMaskFlux(Pool<T> entities) {
        this.entities = entities;
        this.words = new long[(wordIndex(entities.span() + 1)) + 1];
    }

    public BitMaskFlux(Pool<T> entities, long[] words, int size) {
        this.entities = entities;
        this.words = words;
        this.size = size;
    }

    @Override
    public Stasis<T> stasis() {
        return copy();
    }

    @Override
    public Flux<T> flux() {
        return copy();
    }

    private BitMaskFlux<T> copy() {
        return new BitMaskFlux<>(entities, copyOf(words, words.length), size);
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
    public Maybe<T> first(Predicate<? super T> test) {
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            if (word != 0L) {
                int from = Long.numberOfTrailingZeros(word);
                int to = 64 - Long.numberOfLeadingZeros(word);
                long mask = 1L << from;
                for (int j = from; j < to; j++) {
                    if ((word & mask) != 0) {
                        T e = entities.get(i * 64 + j);
                        if (test.test(e)) return Maybe.some(e);
                    }
                    mask = mask << 1;
                }
            }
        }
        return Maybe.nothing();
    }

    @Override
    public boolean contains(T e) {
        int index = index(e);
        int wordIndex = wordIndex(index);
        if (wordIndex >= words.length) return false;
        return (words[wordIndex] & (wordMask(index))) != 0;
    }

    @Override
    public void add(T e) {
        int index = index(e);
        int wordIndex = wordIndex(index);
        if (wordIndex >= words.length) words = copyOf(words, wordIndex + 1);
        long before = words[wordIndex];
        long mask = wordMask(index);
        if ((before | mask) != before) {
            words[wordIndex] |= mask;
            size++;
        }
    }

    @Override
    public void add(Flux<T> added) {
        if (added instanceof BitMaskFlux other) {
            if (other.words.length > words.length)
                words = copyOf(words, other.words.length);
            for (int i = 0; i < other.words.length; i++)
                words[i] |= other.words[i];
            size = countBits();
        } else {
            added.forEach(this::add);
        }
    }

    @Override
    public void remove(T e) {
        int index = index(e);
        int wordIndex = wordIndex(index);
        if (wordIndex >= words.length) return;
        long before = words[wordIndex];
        long mask = ~wordMask(index);
        if ((before & mask) != before) {
            words[wordIndex] &= mask;
            size--;
        }
    }

    @Override
    public void remove(Flux<T> removed) {
        if (removed instanceof BitMaskFlux other) {
            for (int i = 0; i < Math.min(words.length, other.words.length); i++)
                words[i] &= ~other.words[i];
            size = countBits();
        } else {
            removed.forEach(this::remove);
        }
    }

    @Override
    public void clear() {
        if (size > 0) fill(words, 0L);
    }

    private int index(T e) {
        return e.header().serial();
    }

    private int wordIndex(int index) {
        return index / 64;
    }

    private long wordMask(int index) {
        return 1L << (index % 64);
    }

    private int countBits() {
        int c = 0;
        for (long word : words)
            c += Long.bitCount(word);
        return c;
    }
}
