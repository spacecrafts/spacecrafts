package se.jbee.game.any.state;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class TestRnd {

	@Test
	public void relativeIndependenceOfSequencesCreatedFromSeedsGeneratedByAnotherSequence() {
		int n = 500; // it also holds for 2000 but test get slow at that point, however the starting points are reasonable independent
		Rnd rnd = new Rnd(1L);
		Set<Long> seeds = new LinkedHashSet<Long>();
		for (int i = 0; i < n; i++) {
			seeds.add(rnd.nextLong());
		}
		Set<Long> all = new LinkedHashSet<Long>();
		for (Long seed : seeds) {
			Rnd rnd2 = new Rnd(seed);
			for (int i = 0; i < n; i++) {
				long val = rnd2.nextLong();
				all.add(val);
			}
		}
		assertEquals(n*n, all.size());
	}
}
