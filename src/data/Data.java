package data;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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

	public static void load(String path, State target) throws URISyntaxException, IOException {
		URL url = State.class.getResource(path);
		if (url == null) {
			// error - missing folder
		} else {
			File dir;
			dir = new File(url.toURI());
			for (File f : dir.listFiles()) {
				if (f.isFile() && f.getName().endsWith(".data") && !f.getName().contains("test")) {
					load(f, target);
				}
			}
		}
	}

	public static void load(File source, State target) throws IOException {
		System.out.println("loading data: "+source.getName());
		try (BufferedReader in = new BufferedReader(new FileReader(source))) {
			readRows(in, target, readColumnHeaders(in, target));
		}
	}

	/**
	 * Yes, this is messy - but it parses data files fast.
	 * In this case this is more important. 
	 */
	private static void readRows(BufferedReader in, State target, int[] columnComps) throws IOException {
		int c = in.read();
		int col = 0;
		boolean newline = true;
		Entity e = null;
		int[] seq = new int[512];
		int bufpos = 0;
		boolean set = false;
		boolean sorted = false;
		while (c >= 0) {
			switch (c) {
			case '\n': col = 0; newline=true; c = in.read(); break;
			case '\t':
			case ' ' : if (!newline) { col++; } do { c = in.read(); } while (isWhitespace(c)); break;
			case '-' : c = in.read(); break; // the whitespace does the column forwarding
			case '{' : // set
				set = true;
				sorted = true;
			case '[' : // list
				c = in.read();
				while (isWhitespace(c)) { c = in.read(); }
				if (c == ']' || c == '}')
					break;
				bufpos = 0;
				do {
					if (c =='\'') { // char
						seq[bufpos++] = in.read();
						in.read(); // '
						c = in.read();
					} else { // number
						int num = 0;
						int base = 10;
						if (c == '#') {
							base = 16;
							c = in.read();
						}
						do {
							num *= base;
							num += intValue(c);
							c = in.read();
							if (c == '.') {
								c = in.read();
							}
						} while (isHexDigit(c, base == 16));
						seq[bufpos++] = num;
					}
					while (isWhitespace(c)) { c = in.read(); }
				} while (c != ']' && c != '}');
				c = in.read();
				int[] listOrSet = copyOf(seq, bufpos);
				if (set && !sorted) {
					sort(listOrSet);
				}
				e.set(columnComps[col], listOrSet);
				set = false;
				break;
			case '"' : // text
				bufpos = 0;
				c = in.read();
				do {
					seq[bufpos++] = c;
					c = in.read();
				} while (c != '"');
				c = in.read();
				e.set(columnComps[col], copyOf(seq, bufpos));
				break;
			case '\'': // char
				c = in.read();
				e.set(columnComps[col], c);
				in.read(); // '
				c = in.read();
				break;
			default  : // number
				int num = 0;
				int base = 10;
				if (c == '#') {
					base = 16;
					c = in.read();
				}
				do {
					num *= base;
					num += intValue(c);
					c = in.read();
					if (c == '.') {
						c = in.read();
					}
				} while (isHexDigit(c, base == 16));
				if (columnComps[col] == Component.COMP) {
					e = target.defEntity(num);
				} else {
					e.set(columnComps[col], num);
				}
			}
			newline=false;
		}
	}

	private static int intValue(int c) {
		return c > '9' ? c - 'A'+10 : c - '0';
	}

	private static int[] readColumnHeaders(BufferedReader in, State target)	throws IOException {
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
		return comps;
	}

	private static boolean isWhitespace(int c) {
		return c == ' ' || c == '\t' || c == ',' || c == ';' || c == ':';
	}

	private static boolean isHexDigit(int c, boolean hex) {
		return isDigit(c) || hex && c >= 'A' && c <= 'F';
	}
	
	private static boolean isDigit(int c) {
		return c >= '0' && c <= '9';
	}
}
