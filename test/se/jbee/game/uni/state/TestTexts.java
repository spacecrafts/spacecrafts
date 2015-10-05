package se.jbee.game.uni.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTexts {

	@Test
	public void codeNotationSeparatesPartsByDots() {
		assertEquals("T.a.4", Texts.print(Texts.encode('T', 'a', 4)));
	}

	@Test
	public void encodingIsLooseless() {
		int code = Texts.encode('T', 'c', 3);
		assertEquals(code, Texts.parse(Texts.print(code)));
		assertArrayEquals(new int[] { 'T', 'c', 3 }, Texts.decode(code));
	}
}
