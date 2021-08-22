package se.jbee.turnmaster.data;

import se.jbee.turnmaster.data.Any.Entity;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

final class BitsFlux<T extends Entity> implements Flux<T>, Stasis<T> {

    private final Pool<T> of;
    private long[] words;
    private int size = 0;

    BitsFlux(Pool<T> of) {
        this.of = of;
        this.words = new long[(wordIndex(of.span() + 1)) + 1];
    }

    public BitsFlux(Pool<T> of, long[] words, int size) {
        this.of = of;
        this.words = words;
        this.size = size;
    }

    @Override
    public Stasis<T> inStasis() {
        return copy();
    }

    @Override
    public Flux<T> inFlux() {
        return copy();
    }

    private BitsFlux<T> copy() {
        return new BitsFlux<>(of, copyOf(words, words.length), size);
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
                    if ((word & mask) != 0) f.accept(of.get(i * 64 + j));
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
                        T e = of.get(i * 64 + j);
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
        return wordIndex < words.length && (words[wordIndex] & (wordMask(
            index))) != 0;
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
    public void add(Collection<T> added) {
        if (added instanceof BitsFlux other) {
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
    public void remove(Collection<T> removed) {
        if (removed instanceof BitsFlux other) {
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
        size = 0;
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
