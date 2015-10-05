package se.jbee.game.uni.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestI18nCode {

	@Test
	public void notationSeparatesPartsByDots() {
		assertEquals("T.a.4", I18nCode.print(I18nCode.encode('T', 'a', 4)));
	}

	@Test
	public void encodingIsLoseless() {
		int code = I18nCode.encode('T', 'c', 3);
		assertEquals(code, I18nCode.parse(I18nCode.print(code)));
		assertArrayEquals(new int[] { 'T', 'c', 3 }, I18nCode.decode(code));
	}
}
