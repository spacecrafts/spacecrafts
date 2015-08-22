package se.jbee.game.scs.ai;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import se.jbee.game.uni.gfx.Rnd;

public class TestName {

	@Test
	public void sequenceOfPseudoRandomNumbersGeneratesDifferentNamesAtAnAcceptableRatio() {
		Set<String> names = new LinkedHashSet<String>();
		Rnd rnd = new Rnd(39L);
		int n = 10000;
		for (int i = 0; i < n; i++) {
			names.add(Name.name(0, rnd.nextLong()));
		}
		// at least every ten names tried are new on average (up to n of 10.000; this cannot be increased much further as the maximum of possible names is just about a few thousand)
		System.out.println(names.size());
		assertTrue(names.size()*10 > n);
	}
}
