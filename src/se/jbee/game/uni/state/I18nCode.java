package se.jbee.game.uni.state;

import static java.lang.Integer.parseInt;

/**
 * A special key-code used for internationalization. Ore more generally
 * externalization of texts in the game so that the text is not part of the game
 * state but a code that is referring to or representing the actual text.
 *
 * The basic idea is to constrain ourself to a fix three level structure.
 *
 * 1. entity (type/kind)
 * 2. attribute (name)
 * 3. serial (no)
 *
 * 1 is expressed by a upper case letter,
 * 2 is expressed by a lower case letter,
 * 3 is expressed by a integer serial number (short range)
 * <pre>
 * T.a.1
 * T.b.3
 * </pre>
 *
 * The three parts are composed into a single int using the 4 bytes as
 * <pre>
 *    1233
 * </pre>
 */
public final class I18nCode {

	public static int encode(char e, char a, int n) {
		return (e << 24) | (a << 16) | n;
	}

	public static int[] decode(int ean) {
		return new int[] { ean >> 24, (ean >> 16) & 0xFF, ean & 0xFFFF };
	}

	public static int parse(String ean) {
		String[] sx = ean.split("\\.");
		return encode(sx[0].charAt(0), sx[1].charAt(0), parseInt(sx[2]));
	}

	public static String print(int ean) {
		return Character.valueOf((char) (ean >> 24)) +"."+ Character.valueOf((char) ((ean >> 16)&0xFF)) +"."+ String.valueOf(ean & 0xFFFF);
	}
}
