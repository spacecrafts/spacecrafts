package se.jbee.game.uni.state;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class TestName {

	@Test
	public void sequenceOfPseudoRandomNumbersGeneratesDifferentNamesAtAnAcceptableRatio() {
		Set<String> names = new LinkedHashSet<String>();
		Rnd rnd = new Rnd(79L);
		int n = 10000;
		for (int i = 0; i < n; i++) {
			names.add(Name.name(9, rnd.nextLong()));
		}
		// at least every ten names tried are new on average (up to n of 10.000; this cannot be increased much further as the maximum of possible names is just about a few thousand)
		System.out.println(names.size());
		System.out.println(names);
		assertTrue(names.size()*10 > n);
	}
	
}
