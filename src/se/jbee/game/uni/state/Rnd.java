package se.jbee.game.uni.state;

import static java.lang.Math.abs;


/**
 * A simple xorshift128+ pseudo random number generator.
 * 
 * This is useful to make sure the game will always produce the same pseudo
 * random sequence for the same seed independent of the java runtime used.
 * Thereby only the seed is needed to recreate some of the randomly but fix
 * appearing game content.
 */
public final class Rnd {

	private long low, high;
	
	public Rnd(long seed) {
		low=seed;
	} 
	
	public long nextLong(){
	    long x = low;
	    long y = high;
	    low = y;
	    x ^= x << 23; // a
	    x ^= x >>> 17; // b
	    x ^= y ^ (y >>> 26); // c
	    high = x;
	    return x + y;
	}
	
	public long nextLong(long max) {
		return nextLong(0L, max);
	}
	
	public long nextLong(long min, long max) {
		long n = max - min + 1;
		// shift away sign bit to only have positive results
		return min + ((nextLong() << 1 >>> 1) % n);
	}
	
	public int nextInt() {
		return (int) (nextLong() >>> 17) & 0b0111_1111_1111_1111;
	}
	
	public int nextInt(int max) {
		return nextInt(0, max);
	}
		
	public int nextInt(int min, int max) {
		return (int) nextLong(min, max);
	}

	public boolean nextChance(int percent) {
		return abs(nextLong()) < Long.MAX_VALUE / 100L * percent;
	}
}
