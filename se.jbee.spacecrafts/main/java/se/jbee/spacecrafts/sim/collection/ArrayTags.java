package se.jbee.spacecrafts.sim.collection;

import se.jbee.spacecrafts.sim.Resourcing.Tag;
import se.jbee.spacecrafts.sim.Resourcing.Tags;

import java.util.function.Consumer;

import static java.lang.System.arraycopy;
import static java.util.Arrays.fill;

final class ArrayTags implements Tags {

    private final Index<Tag> keys;
    private final long[] words;

    public ArrayTags(Index<Tag> keys) {
        this.keys = keys;
        this.words = new long[keys.span()];
    }

    @Override
    public boolean has(Tag key) {
        int index = index(key);
        int wordIndex = index / 64;
        return ((words[wordIndex] & (1L << (index % 64))) != 0);
    }

    @Override
    public void set(Tag key, boolean value) {
        int index = index(key);
        int wordIndex = index / 64;
        words[wordIndex] |= (1L << (index % 64));
    }

    @Override
    public void zero(Tags zeros) {
        if (zeros instanceof ArrayTags ft) {
            arraycopy(ft.words, 0, words, 0, words.length);
        } else {
            clear();
            zeros.forEach(tag -> set(tag, true));
        }
    }

    @Override
    public void clear() {
        fill(words, 0L);
    }

    @Override
    public void forEach(Consumer<? super Tag> f) {
        for (int w = 0; w < words.length; w++) {
            long word = words[w];
            long mask = 1L;
            for (int i = 0; i < 64; i++) {
                if ((word & mask) != 0) f.accept(keys.get(w * 64 + i));
                mask = mask << 1;
            }
        }
    }

    private static int index(Tag key) {
        return key.header().serial();
    }
}
