package se.jbee.game.uni.state;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

	public static void load(File source, State target) throws IOException {
		try (BufferedReader in = new BufferedReader(new FileReader(source))) {
			String[] columns = in.readLine().split("\\s+");
			int[] comps = new int[columns.length];
			Entity[] components = new Entity[columns.length];
			for (int i = 0; i < columns.length; i++) {
				int type = parseInt(columns[i]);
				comps[i] = type;
				components[i] = target.component(type);
			}
			int c = in.read();
			int i = 0;
			int type = -1;
			Entity e = null;
			int[] buf = new int[512];
			int bufpos = 0;
			while (c >= 0) {
				switch (c) {
				case '\n': i = 0; type = -1; c = in.read(); break;
				case '\t':
				case ' ' : if (type >= 0) { i++; } do { c = in.read(); } while (c == ' ' || c == '\t'); break; 
				case '-' : c = in.read(); break;
				case '[' : // list
				case '{' : // set
					c = in.read();
					while (c == ' ' || c == '\t') { c = in.read(); } 
					bufpos = 0;
					do {
						int num = 0;
						do {
							num *= 10;
							num += c - '0';
							c = in.read();
						} while (c >= '0' && c <= '9');
						buf[bufpos++] = num;
						while (c == ' ' || c == '\t') { c = in.read(); }
					} while (c != ']' && c != '}');
					c = in.read();
					e.put(comps[i], copyOf(buf, bufpos));
					break;
				case '"' : // text
					bufpos = 0;
					c = in.read();
					do {
						buf[bufpos++] = c;
						c = in.read();
					} while (c != '"');
					c = in.read();
					e.put(comps[i], copyOf(buf, bufpos));
					break;
				default  : // number
					int num = 0;
					do {
						num *= 10;
						num += (c - '0');
						c = in.read();
					} while (c >= '0' && c <= '9');
					if (type == -1) {
						type = num;
						e = target.defEntity(type);
					} else {
						e.put(comps[i], num);
					}
				}
			}
		}
	}
}
