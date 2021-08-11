package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Resourcing.Indicator;
import se.jbee.spacecrafts.sim.Resourcing.Marks;

import java.util.function.Consumer;

import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;

final class ArrayMarks implements Marks {

    private final Index<Indicator> keys;
    private final long[] words;

    public ArrayMarks(Index<Indicator> keys) {
        this.keys = keys;
        this.words = new long[keys.span() + 1];
    }

    @Override
    public boolean has(Indicator key) {
        int index = index(key);
        int wordIndex = index / 64;
        return ((words[wordIndex] & (1L << (index % 64))) != 0);
    }

    @Override
    public void set(Indicator key, boolean value) {
        int index = index(key);
        int wordIndex = index / 64;
        words[wordIndex] |= (1L << (index % 64));
    }

    @Override
    public void zero(Marks zeros) {
        if (zeros instanceof ArrayMarks ft) {
            arraycopy(ft.words, 0, words, 0, words.length);
        } else {
            clear();
            zeros.forEach(indicator -> set(indicator, true));
        }
    }

    @Override
    public void clear() {
        fill(words, 0L);
    }

    @Override
    public void forEach(Consumer<? super Indicator> f) {
        for (int w = 0; w < words.length; w++) {
            long word = words[w];
            long mask = 1L;
            for (int i = 0; i < 64; i++) {
                if ((word & mask) != 0) f.accept(keys.get(w * 64 + i));
                mask = mask << 1;
            }
        }
    }

    private static int index(Indicator key) {
        return key.header().serial();
    }
}
