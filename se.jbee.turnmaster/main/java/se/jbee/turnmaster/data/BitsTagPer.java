package se.jbee.turnmaster.data;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.System.arraycopy;

abstract class BitsTagPer<K extends Any.Entity> implements TagPer<K> {

    protected final Pool<K> of;

    protected BitsTagPer(Pool<K> of) {this.of = of;}

    @Override
    public boolean has(K key) {
        int index = index(key);
        int wordIndex = wordIndex(index);
        return wordIndex < words() && (word(wordIndex) & wordMask(index)) != 0;
    }

    abstract int words();

    abstract long word(int wordIndex);

    static <T extends Any.Entity> int index(T e) {
        return e.header().serial();
    }

    static int wordIndex(int index) {
        return index / 64;
    }

    static long wordMask(int index) {
        return 1L << (index % 64);
    }
}

abstract class FixedBitsTagPer<K extends Any.Definition> extends BitsTagPer<K> {

    private final long[] words;

    FixedBitsTagPer(Index<K> of) {
        super(of);
        this.words = new long[wordIndex(of.span() + 1) + 1];
    }

    @Override
    final int words() {
        return words.length;
    }

    @Override
    final long word(int wordIndex) {
        return words[wordIndex];
    }

    @Override
    public void set(K key, boolean value) {
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
    public void forEach(Consumer<? super K> f) {
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
    public void zero(TagPer<K> zeros) {
        if (zeros instanceof FixedBitsTagPer<K> ft) {
            arraycopy(ft.words, 0, words, 0, words.length);
        } else {
            clear();
            zeros.forEach(e -> set(e, true));
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
    public Maybe<K> first(Predicate<? super K> test) {
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
}

abstract class DynamicBitsTagPer<K extends Any.Creation> extends BitsTagPer<K> {

    DynamicBitsTagPer(Register<K> of) {super(of);}
}
