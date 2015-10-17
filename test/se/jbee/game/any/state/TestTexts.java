package se.jbee.game.any.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import data.Data;

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

	@Test
	public void textsLoadFromFiles() throws URISyntaxException {
		Texts texts = new Texts();
		texts.index(Data.class, "test.texts");

		assertEquals("Tech 1 Name", texts.lookup(Texts.encode('T', 'n', 1)));
		assertEquals("Tech 1 Description", texts.lookup(Texts.encode('T', 'd', 1)));
		assertEquals("Tech 2 Name", texts.lookup(Texts.encode('T', 'n', 2)));
		assertEquals("Tech 2 Description", texts.lookup(Texts.encode('T', 'd', 2)));
		assertEquals("something else here\nand here...", texts.lookup(Texts.encode('T', 'd', 3)));
		assertEquals("Tech 4 Name", texts.lookup(Texts.encode('T', 'n', 4)));

	}
}
