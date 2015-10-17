package data;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import se.jbee.game.any.state.Component;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;

/**
 * This is a simple extension to the {@link State} container that allows to read
 * entities from text files. Each row describes an entity. Columns are separated
 * by whitespace. The first row however defines the columns given by the
 * component types. These are expected to exist in the target.
 *
 * A file could look like this:
 *
 * <pre>
 *  0  43 44 45
 *  42 1  "a" [1 2]
 *  42 2	"bc" {3}
 *  42 3 - [4 555 66]
 * </pre>
 */
public final class Data {

	public static void load(Class<?> path, String filename, State target) throws IOException, URISyntaxException {
		load(new File(path.getResource(filename).toURI()), target);
	}
	
	public static void load(File source, State target) throws IOException {
		try (BufferedReader in = new BufferedReader(new FileReader(source))) {
			String[] columns = in.readLine().split("\\s+");
			int[] comps = new int[columns.length];
			Entity[] components = new Entity[columns.length];
			for (int col = 0; col < columns.length; col++) {
				String column = columns[col];
				if (isDigit(column.charAt(0))) {
					int type = parseInt(column);
					comps[col] = type;
					components[col] = target.component(type);
				} else {
					Entity c = target.component(column);
					components[col] = c;
					comps[col] = c.num(Component.CODE);
				}
			}
			int c = in.read();
			int col = 0;
			boolean newline = true;
			Entity e = null;
			int[] buf = new int[512];
			int bufpos = 0;
			while (c >= 0) {
				switch (c) {
				case '\n': col = 0; newline=true; c = in.read(); break;
				case '\t':
				case ' ' : if (!newline) { col++; } do { c = in.read(); } while (c == ' ' || c == '\t'); break;
				case '-' : c = in.read(); break;
				case '[' : // list
				case '{' : // set
					c = in.read();
					while (c == ' ' || c == '\t') { c = in.read(); }
					if (c == ']' || c == '}')
						break;
					bufpos = 0;
					do {
						int num = 0;
						do {
							num *= 10;
							num += c - '0';
							c = in.read();
						} while (isDigit(c));
						buf[bufpos++] = num;
						while (c == ' ' || c == '\t') { c = in.read(); }
					} while (c != ']' && c != '}');
					c = in.read();
					e.put(comps[col], copyOf(buf, bufpos));
					break;
				case '"' : // text
					bufpos = 0;
					c = in.read();
					do {
						buf[bufpos++] = c;
						c = in.read();
					} while (c != '"');
					c = in.read();
					e.put(comps[col], copyOf(buf, bufpos));
					break;
				case '\'': // char
					c = in.read();
					e.put(comps[col], c);
					in.read(); // '
					c = in.read();
					break;
				default  : // number
					int num = 0;
					do {
						num *= 10;
						num += (c - '0');
						c = in.read();
					} while (isDigit(c));
					if (comps[col] == Component.COMP) {
						e = target.defEntity(num);
					} else {
						e.put(comps[col], num);
					}
				}
				newline=false;
			}
		}
	}

	private static boolean isDigit(int c) {
		return c >= '0' && c <= '9';
	}
}
