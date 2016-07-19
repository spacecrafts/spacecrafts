package se.jbee.game.any.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import se.jbee.game.any.gfx.Texts;
import data.Data;

public class TestTexts {

	@Test
	public void codeNotationSeparatesPartsByDots() {
		assertEquals("T.a.4", Texts.keyToString(Texts.textKey('T', 'a', 4)));
	}

	@Test
	public void encodingIsLooseless() {
		int code = Texts.textKey('T', 'c', 3);
		assertEquals(code, Texts.parseKey(Texts.keyToString(code)));
		assertArrayEquals(new int[] { 'T', 'c', 3 }, Texts.textKeyComponents(code));
	}

	@Test
	public void textsLoadFromFiles() throws URISyntaxException {
		Texts texts = new Texts();
		texts.index(Data.class, "test.lines");

		assertEquals("Tech 1 Name", texts.lookup(Texts.textKey('T', 'n', 1)));
		assertEquals("Tech 1 Description", texts.lookup(Texts.textKey('T', 'd', 1)));
		assertEquals("Tech 2 Name", texts.lookup(Texts.textKey('T', 'n', 2)));
		assertEquals("Tech 2 Description", texts.lookup(Texts.textKey('T', 'd', 2)));
		assertEquals("something else here\nand here...", texts.lookup(Texts.textKey('T', 'd', 3)));
		assertEquals("Tech 4 Name", texts.lookup(Texts.textKey('T', 'n', 4)));
		assertEquals("Code by letter", texts.lookup(Texts.textKey('T', 'n', 'x')));
	}
}
