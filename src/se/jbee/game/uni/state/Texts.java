package se.jbee.game.uni.state;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * {@linkplain Texts} are game data that is independent of the game state itself.
 * Different users might even want different texts for the same game.
 * So the game state refers to the text only by the means of keys.
 *
 * These keys use a special coding.
 * The basic idea is to constrain ourself to a fix three level structure.
 *
 * 1. series (kind)
 * 2. attribute (name)
 * 3. number (serial)
 *
 * 1 is expressed by a upper case letter,
 * 2 is expressed by a lower case letter,
 * 3 is expressed by a integer serial number (short range)
 *
 * <pre>
 * T.a.1
 * T.n.3
 * </pre>
 *
 * This could stand for: <b>n</b>ame of <b>T</b>echnology no 3
 *
 *
 * The three parts are composed into a single int using the 4 bytes as
 *
 * <pre>
 * 1233
 * </pre>
 *
 * A map or dictionary using these keys is modeled as a fixed size array in the
 * assumption that a large portion of the available s-a combinations will be
 * used.
 */
public final class Texts {

	private final String[][] texts = new String[26*26][];

	public void index(File texts) {
		try (BufferedReader in = new BufferedReader(new FileReader(texts))) {
			String line = in.readLine();
			while (line != null) {
				if (!line.startsWith("//")) {
					int eq = line.indexOf('=');
					int san = parse(line.substring(0, eq));
					String text = line.substring(eq+1);
					StringBuilder mtext = new StringBuilder();
					if (text.startsWith("{")) {
						line = in.readLine();
						while (line != null && !line.startsWith("}")) {
							mtext.append(line).append('\n');
							line = in.readLine();
						}
						mtext.setLength(mtext.length()-1);
						text = mtext.toString();
					}
					index(san, text);
				}
				line = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index(int san, String text) {
		int off = offset(san);
		int n = n(san);
		String[] series = texts[off];
		if (series == null)
			series = new String[n+1];
		if (series.length <= n) {
			series = Arrays.copyOf(series, n+1);
		}
		texts[off] = series;
		series[n] = text;
	}

	public String lookup(int san) {
		String[] series = texts[offset(san)];
		int n = n(san);
		if (series != null && n < series.length && series[n] != null) {
			return series[n];
		}
		return "?"+print(san)+"?";
	}

	private int offset(int san) {
		int e = s(san);
		int a = a(san);
		return 26*(e-'A')+(a-'a');
	}

	private static int a(int san) {
		return (san >> 16) & 0xFF;
	}

	// code handling

	public static int encode(char s, char a, int n) {
		return (s << 24) | (a << 16) | n;
	}

	public static int[] decode(int san) {
		return new int[] { s(san), a(san), n(san) };
	}

	private static int n(int san) {
		return san & 0xFFFF;
	}

	private static int s(int san) {
		return san >> 24;
	}

	public static int parse(String san) {
		String[] sx = san.split("\\.");
		return encode(sx[0].charAt(0), sx[1].charAt(0), parseInt(sx[2]));
	}

	public static String print(int san) {
		return Character.valueOf((char) s(san)) +"."+ Character.valueOf((char) a(san)) +"."+ String.valueOf(n(san));
	}
}