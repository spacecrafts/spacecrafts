package se.jbee.game.state;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * The design assumes that most entities do not have lots of components.
 * Components are in no particular order, a lookup has to scan.
 * Removed components are just zeroed for later reuse of the index.
 * When a entity has to grow it doubles in size. 
 */
public final class Entity implements Component {

	private static final int[] EMPTY_LIST = new int[0];
	
	private int[]   cs;   // components
	private int[][] vs;   // values
	
	private int size = 0; // how much properties are used
	
	private Entity() {
		// used for load
	}
	
	public Entity(int id, int type) {
		init(id, type);
	}

	private void init(int id, int type) {
		cs    = new int[4];
		vs    = new int[4][];
		cs[0] = ID;
		vs[0] = new int[] { id };
		cs[1] = TYPE;
		vs[1] = new int[] { type < 0 ? id : type };
		size  = 2;
	}
	
	/**
	 * This is intentionally just visible within the package as it should only be used by {@link State}! 
	 */
	int at(int index) {
		return vs[index][0];
	}	
	
	/*
	 * IO
	 */

	public static Entity load(DataInputStream in) throws IOException {
		Entity e = new Entity();
		e.size = in.readInt();
		e.cs = new int[e.size];
		e.vs = new int[e.size][];
		for (int i = 0; i < e.size; i++) {
			e.cs[i] = in.readInt();
			int len = in.readInt();
			if (len > 0) {
				int[] val = new int[len];
				for (int j = 0; j < len; j++) {
					val[j] = in.readInt();
				}
				e.vs[i] = val;
			} else {
				e.vs[i] = EMPTY_LIST;
			}
		}
		return e;
	}
	
	public void save(DataOutputStream out) throws IOException {
		int c = 0;
		for (int i = 0; i < size; i++) {
			if (cs[i] >= 0)
				c++;
		}
		out.writeInt(c);
		for (int i = 0; i < size; i++) {
			if (cs[i] >= 0) {
				out.writeInt(cs[i]);
				int[] val = vs[i];
				out.writeInt(val.length);
				if (val.length > 0) {
					for (int j = 0; j < val.length; j++) {
						out.writeInt(val[j]);
					}
				}
			}
		}
	}
	
	/*
	 * all
	 */
	
	public void clear() {
		init(vs[0][0], vs[1][0]);
	}
	
	/*
	 * map (of primitives)
	 */
	
	public int num(int comp) {
		int i = indexOf(comp);
		if (i < 0)
			return 0;
		int[] val = vs[i];
		return val.length == 0 ? 0 : val[0];
	}
	
	public int id() {
		return num(ID);
	}
	
	public int type() {
		return num(TYPE);
	}
	
	public Entity put(int comp, int num) {
		return put(comp, new int[] { num });
	}
	
	public Entity put(int comp, int[] listOrSetOrTxt) {
		int i = indexOf(comp);
		if (i < 0) {
			i = indexOf(-1); // reuse some?
		}
		if (i >= 0) {
			cs[i] = comp;
			vs[i] = listOrSetOrTxt;
			return this;
		}
		int len = cs.length;
		if (size >= len) {
			cs = copyOf(cs, len*2);
			vs = copyOf(vs, len*2);
		}
		cs[size] = comp;
		vs[size] = listOrSetOrTxt;
		size++;
		return this;
	}
	
	public void erase(int comp) {
		int i = indexOf(comp);
		if (i >= 0) {
			cs[i] = -1;
			vs[i] = EMPTY_LIST;
		}
	}	
	
	/*
	 * list
	 */
	
	/**
	 * this does not copy intentionally. do not mutate values received from an entity! 
	 */
	public int[] list(int comp) {
		int i = indexOf(comp);
		return i < 0 ? EMPTY_LIST : vs[i];
	}
	
	public String text(int comp) {
		int[] text = vs[indexOf(comp)];
		return new String(text, 0, text.length);
	}
	
	public String name() {
		return text(NAME);
	}
	
	public void append(int comp, int e) {
		int i = indexOf(comp);
		if (i < 0) {
			put(comp, new int[] { e });
			return;
		}
		int[] list = vs[i];
		int[] list2 = copyOf(list, list.length+1);
		list2[list.length] = e;
		vs[i] = list2;
	}
	
	public void append(int comp, int[] tail) {
		int i = indexOf(comp);
		if (i < 0) {
			put(comp, tail);
			return;
		}
		int[] list = vs[i];
		if (list.length == 0) {
			vs[i] = tail;
			return;
		}
		int[] list2 = copyOf(list, list.length+tail.length);
		arraycopy(tail, 0, list2, list.length, tail.length);
		this.vs[i] = list2;
	}
	
	public void prepend(int comp, int e) {
		int i = indexOf(comp);
		if (i < 0) {
			put(comp, new int[] { e });
			return;
		}
		int[] list = vs[i];
		int[] list2 = new int[list.length+1];
		arraycopy(list, 0, list2, 1, list.length);
		list2[0] = e;
		vs[i] = list2;
	}
	
	public void prepend(int comp, int[] head) {
		int i = indexOf(comp);
		if (i < 0) {
			put(comp, head);
			return;
		}
		int[] list = vs[i];
		if (list.length == 0) {
			vs[i] = head;
			return;
		}
		int[] list2 = copyOf(head, list.length+head.length);
		arraycopy(list, 0, list2, head.length, list.length);
		this.vs[i] = list2;
	}
	
	/*
	 * set
	 */

	public void insert(int comp, int[] members) {
		append(comp, members);
		int[] set = vs[indexOf(comp)];
		sort(set);
	}
	
	/**
	 * works also for lists 
	 */
	public void remove(int comp, int[] members) {
		if (members.length == 0)
			return;
		int i = indexOf(comp);
		if (i < 0) {
			return;
		}
		int[] listOrSet = vs[i];
		if (listOrSet.length == 0)
			return;
		int w = 0;
		for (int j = 0; j < listOrSet.length; j++) {
			if (contains(members, listOrSet[j])) {
				listOrSet[w++] = listOrSet[j];
			}
		}
		vs[i] = copyOf(listOrSet, w);
	}		

	private static boolean contains(int[] elements, int member) {
		for (int i = 0; i < elements.length; i++)
			if (elements[i] == member)
				return true;
		return false;
	}

	public int contains(int comp, int member) {
		int i = indexOf(comp);
		if (i < 0)
			return -1;
		return Arrays.binarySearch(vs[i], member);
	}
	
	/*
	 * bitset
	 */
	
	public void set(int comp, int...flags) {
		for (int flag : flags) {
			set(comp, flag);
		}
	}

	public void set(int comp, int flag) {
		int i = indexOf(comp);
		if (i < 0) {
			put(comp, 1 << flag);
		} else {
			vs[i][0] |= 1 << flag;
		}
	}
	
	public void unset(int comp, int...flags) {
		for (int flag : flags) {
			unset(comp, flag);
		}
	}
	
	public void unset(int comp, int flag) {
		int i = indexOf(comp);
		if (i >= 0) {
			vs[i][0] &= ~(1 << flag);
		}
	}
	
	public <E extends Enum<E>> void set(int comp, E flag) {
		set(comp, flag.ordinal());
	}
	
	public <E extends Enum<E>> void unset(int comp, E flag) {
		unset(comp, flag.ordinal());
	}	
	
	public <E extends Enum<E>> boolean isSet(int comp, E flag) {
		int i = indexOf(comp);
		int ord = flag.ordinal();
		return i >= 0 && vs[i].length > 0 && ((vs[i][0] >> ord) & 1) == 1;
	}
	
	/*
	 * utilities and commons
	 */
	
	public int indexOf(int comp) {
		for (int i = 0; i < size; i++) {
			if (cs[i] == comp)
				return i;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		int id = vs[0][0];
		int i = indexOf(NAME);
		StringBuilder b = new StringBuilder();
		for (int j = 0; j < size; j++) {
			if (j > 0) {
				b.append(',');
			}
			b.append(cs[j]).append('=');
			if (vs[j].length == 1) {
				b.append(vs[j][0]);
			} else {
				b.append("[*").append(vs[j].length).append(']');
			}
		}
		if (i < 0)
			return String.format("e%04d (%s)", id, b.toString());
		int[] name = vs[i];
		return String.format("e%04d %s (%s)", id, new String(name, 0, name.length), b.toString());
	}

	public int size() {
		return size;
	}

	public static int[] codePoints(String s) {
		int[] cps = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			cps[i] = s.charAt(i);
		}
		return cps;
	}

}
