package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.engine.Any.Indicator;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.System.arraycopy;

final class BitsMarks implements Marks {

    private Range<Indicator> of;
    private final long[] words;

    BitsMarks(Range<Indicator> of) {
        this.of = of;
        this.words = new long[wordIndex(of.span() + 1) + 1];
    }

    @Override
    public boolean has(Indicator key) {
        int index = index(key);
        int wordIndex = wordIndex(index);
        return wordIndex < words.length && (words[wordIndex] & wordMask(index)) != 0;
    }

    @Override
    public void set(Indicator key, boolean value) {
        int index = index(key);
        int wordIndex = wordIndex(index);
        if (value) {
            words[wordIndex] |= wordMask(index);
        } else {
            words[wordIndex] &= ~wordMask(index);
        }
    }

    @Override
    public void clear() {
        Arrays.fill(words, 0L);
    }

    @Override
    public void forEach(Consumer<? super Indicator> f) {
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
    public void zero(Marks zeros) {
        if (zeros instanceof BitsMarks ft) {
            arraycopy(ft.words, 0, words, 0, words.length);
        } else {
            clear();
            zeros.forEach(indicator -> set(indicator, true));
        }
    }

    @Override
    public int size() {
        int c = 0;
        for (long word : words)
            c += Long.bitCount(word);
        return c;
    }

    @Override
    public Maybe<Indicator> first(Predicate<? super Indicator> test) {
        for (int i = 0; i < words.length; i++) {
            long word = words[i];
            if (word != 0L) {
                int from = Long.numberOfTrailingZeros(word);
                int to = 64 - Long.numberOfLeadingZeros(word);
                long mask = 1L << from;
                for (int j = from; j < to; j++) {
                    if ((word & mask) != 0) {
                        var e = of.get(i * 64 + j);
                        if (test.test(e)) return Maybe.some(e);
                    }
                    mask = mask << 1;
                }
            }
        }
        return Maybe.nothing();
    }

    private int index(Indicator e) {
        return e.header().serial();
    }

    private int wordIndex(int index) {
        return index / 64;
    }

    private long wordMask(int index) {
        return 1L << (index % 64);
    }

}
