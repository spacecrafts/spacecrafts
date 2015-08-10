package se.jbee.game.state;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	//TODO a general routine to load data files
	// the basic idea is that the first line describes the "columns" in terms of the types of the components
	// than each line provides the values of these components
	// columns are divided by space
	// values are given as: 1 2 3 (3 columns)
	// or: 1 [2 3] {4 5} "ab"
	// and - (for: does not have the component)
	
	public static void load(File source, State target) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(source));
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
