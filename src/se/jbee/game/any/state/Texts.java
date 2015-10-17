package se.jbee.game.any.state;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
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
 * 3. code (number/serial)
 *
 * 1 is expressed by a upper case letter,
 * 2 is expressed by a lower case letter,
 * 3 is expressed by a integer code (a serial number in a certain context)
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

	public void index(Class<?> path, String filename) {
		try {
			index(new File(path.getResource(filename).toURI()));
		} catch (URISyntaxException e) {
			// should not occur - otherwise ignore
		}
	}
	
	public void index(File texts) {
		try (BufferedReader in = new BufferedReader(new FileReader(texts))) {
			String line = in.readLine();
			while (line != null) {
				if (!line.startsWith("#")) {
					int eq = line.indexOf('=');
					if (eq > 0) {
						int sac = parse(line.substring(0, eq));
						String text = line.substring(eq+1);
						StringBuilder block = new StringBuilder();
						if (text.startsWith("/")) {
							line = in.readLine();
							while (line != null && !line.startsWith("/")) {
								block.append(line).append('\n');
								line = in.readLine();
							}
							block.setLength(block.length()-1);
							text = block.toString();
						}
						index(sac, text);
					}
				}
				line = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index(int sac, String text) {
		int off = offset(sac);
		int code = c(sac);
		String[] series = texts[off];
		if (series == null)
			series = new String[code+1];
		if (series.length <= code) {
			series = Arrays.copyOf(series, code+1);
		}
		texts[off] = series;
		series[code] = text;
	}

	public String lookup(int sac) {
		String[] series = texts[offset(sac)];
		int n = c(sac);
		if (series != null && n < series.length && series[n] != null) {
			return series[n];
		}
		return "?"+print(sac)+"?";
	}

	private int offset(int sac) {
		int e = s(sac);
		int a = a(sac);
		return 26*(e-'A')+(a-'a');
	}

	private static int a(int sac) {
		return (sac >> 16) & 0xFF;
	}

	// code handling

	public static int encode(char s, char a, int c) {
		return (s << 24) | (a << 16) | c;
	}

	public static int[] decode(int sac) {
		return new int[] { s(sac), a(sac), c(sac) };
	}

	private static int c(int sac) {
		return sac & 0xFFFF;
	}

	private static int s(int sac) {
		return sac >> 24;
	}

	public static int parse(String sac) {
		String[] sx = sac.split("\\.");
		String code = sx[2];
		char c0 = code.charAt(0);
		int c = c0 >= '0' && c0 <= '9' ? parseInt(code) : c0;
		return encode(sx[0].charAt(0), sx[1].charAt(0), c);
	}

	public static String print(int sac) {
		return Character.valueOf((char) s(sac)) +"."+ Character.valueOf((char) a(sac)) +"."+ String.valueOf(c(sac));
	}
}