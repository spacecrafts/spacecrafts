package se.jbee.turnmaster;

import java.util.random.RandomGenerator;

/**
 * A port of Blackman and Vigna's xoroshiro128+ generator;
 * <p>
 * Extracted from
 * <a href="https://raw.githubusercontent.com/SquidPony/SquidLib/master/squidlib-util/src/main/java/squidpony/squidmath/XoRoRNG.java">XoRoRNG.java</a>
 * <p>
 * Original authors of xoroshiro128+ and java implementation of xoroshiro128+:
 *
 * @author Sebastiano Vigna
 * @author David Blackman
 * @author Tommy Ettinger
 */
public final class RNG implements RandomGenerator {

    private long state0, state1;

    public RNG() {
        this(System.currentTimeMillis());
    }

    public RNG(long seed) {
        long state = seed + 0x9E3779B97F4A7C15L;
        long z = state;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        state0 = z ^ (z >>> 31);

        state += 0x9E3779B97F4A7C15L;
        z = state;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        state1 = z ^ (z >>> 31);
    }

    public int next(int bits) {
        final long s0 = state0;
        long s1 = state1;
        final int result = (int) (s0 + s1) >>> (32 - bits);
        s1 ^= s0;
        state0 = (s0 << 55 | s0 >>> 9) ^ s1 ^ (s1 << 14); // a, b
        state1 = (s1 << 36 | s1 >>> 28); // c
        return result;
    }

    @Override
    public long nextLong() {
        final long s0 = state0;
        long s1 = state1;
        final long result = s0 + s1;

        s1 ^= s0;
        state0 = (s0 << 55 | s0 >>> 9) ^ s1 ^ (s1 << 14); // a, b
        state1 = (s1 << 36 | s1 >>> 28); // c
        return result;
    }

    /**
     * Can return any int, positive or negative, of any size permissible in a
     * 32-bit signed integer.
     *
     * @return any int, all 32 bits are random
     */
    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    /**
     * Exclusive on the outer bound; the inner bound is 0. The bound should not
     * be negative;
     *
     * @param bound the outer exclusive bound; should be positive
     * @return a random int between 0 (inclusive) and bound (exclusive)
     */
    @Override
    public int nextInt(final int bound) {
        return (int) ((bound * (nextLong() >>> 33)) >> 31);
    }

    /**
     * Inclusive lower, exclusive upper.
     *
     * @param inner the inner bound, inclusive, can be positive or negative
     * @param outer the outer bound, exclusive, should be positive, should
     *              usually be greater than inner
     * @return a random int that may be equal to inner and will otherwise be
     * between inner and outer
     */
    @Override
    public int nextInt(final int inner, final int outer) {
        return inner + nextInt(outer - inner);
    }

    /**
     * Exclusive on the outer bound; the inner bound is 0. The bound may be
     * negative, which will produce a non-positive result.
     *
     * @param bound the outer exclusive bound; may be positive or negative
     * @return a random long between 0 (inclusive) and bound (exclusive)
     */
    @Override
    public long nextLong(long bound) {
        long rand = nextLong();
        final long randLow = rand & 0xFFFFFFFFL;
        final long boundLow = bound & 0xFFFFFFFFL;
        rand >>>= 32;
        bound >>= 32;
        final long t = rand * boundLow + (randLow * boundLow >>> 32);
        return rand * bound + (t >> 32) +
               (randLow * bound + (t & 0xFFFFFFFFL) >> 32);
    }

    /**
     * Inclusive inner, exclusive outer; both inner and outer can be positive or
     * negative.
     *
     * @param inner the inner bound, inclusive, can be positive or negative
     * @param outer the outer bound, exclusive, can be positive or negative and
     *              may be greater than or less than inner
     * @return a random long that may be equal to inner and will otherwise be
     * between inner and outer
     */
    @Override
    public long nextLong(final long inner, final long outer) {
        return inner + nextLong(outer - inner);
    }

    @Override
    public boolean nextBoolean() {
        return nextLong() < 0L;
    }
}
