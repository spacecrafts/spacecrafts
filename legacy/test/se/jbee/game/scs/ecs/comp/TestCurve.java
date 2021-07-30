package se.jbee.game.scs.ecs.comp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCurve {

	@Test
	public void parseSpacedCurve() {
		Curve c = new Curve("{1:2 3:4 4:6}");

		assertEquals(3, c.points());
		assertEquals("{1:2 3:4 4:6}", c.toString());
	}

	@Test
	public void parseCommaSeparatedCurve() {
		Curve c = new Curve("{3:4,4:6}");

		assertEquals(2, c.points());
		assertEquals("{3:4 4:6}", c.toString());
	}

	@Test
	public void parseSinglePointCurve() {
		Curve c = new Curve("{10:20}");
		assertEquals(1, c.points());
		assertEquals("{10:20}", c.toString());
	}

	@Test
	public void parseEmptyCurve() {
		Curve c = new Curve("{}");
		assertEquals(0, c.points());
		assertEquals("{}", c.toString());
	}

	@Test
	public void curvePointAt() {
		assertCurveOutput(new Curve("{1:2 3:4 4:6}"), 0, 2, 4, 8, 14, 20);
	}

	@Test
	public void curvePointAtWithAbsoluteLimit() {
		assertCurveOutput(new Curve("{1:2 3:4 4:6 6:0}"), 0, 2, 4, 8, 14, 20, 20);
	}

	@Test
	public void curvePointAtWithDownCurve() {
		assertCurveOutput(new Curve("{1:2 3:4 4:6 6:-5}"), 0, 2, 4, 8, 14, 20, 15, 10, 5, 0, 0);
	}

	public void assertCurveOutput(Curve c, int... expected) {
		for (int i = 0; i < expected.length; i++) {
			assertEquals("point " + i, expected[i], c.at(i));
		}
	}

}
