package se.jbee.game.any.state;

import static java.util.Arrays.copyOf;
import static se.jbee.game.any.state.Entity.codePoints;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;

/**
 * The "system" of an {@link Entity}-{@link Component}-System.
 *
 * A state object is a container for generic state, what is more or less a list
 * of entities, each having a couple of components.
 *
 * During a game entities are only introduced to a state universe but never
 * removed. An entity might be cleared though just leaving its ID component
 * intact to have all other data GCed.
 */
public final class State implements Component {

	private static final int INITIAL_SIZE = 128;

	private Entity[] es = new Entity[INITIAL_SIZE];

	private int size = 0;

	public static State base() {
		State g = new State();
		g.es[COMP] = new Entity(COMP, COMP).put(CODE, COMP).put(NAME, codePoints("COMP")).put(COMP, new int[1]);
		g.size = 1;
		// make sure TYPE, ID and NAME are available as they are used already (each entity has an ID and TYPE, these have a NAME)
		g.defComponent(TYPE).put(NAME, codePoints("TYPE"));
		g.defComponent(ID).put(NAME, codePoints("ID"));
		g.defComponent(NAME).put(NAME, codePoints("NAME"));
		g.defComponent(CODE).put(NAME, codePoints("CODE"));
		return g;
	}

	private State() {
		// used directly during load
	}

	public State defComponents(Class<? extends Component> components) {
		for (Field f : components.getDeclaredFields()) {
			try {
				int type = f.getInt(null);
				if (!hasComponent(type)) {
					defComponent(type).put(NAME, codePoints(f.getName()));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return this;
	}

	/**
	 * Is a "component entity" (all of those are usually defined at the
	 * beginning but when loading a old save with an updated version of the game
	 * code some might be introduced later on).
	 */
	public Entity defComponent(int type) {
		Entity e = defEntity(COMP);
		e.put(CODE, type);
		int[] TYPE2ID = es[COMP].list(COMP);
		if (TYPE2ID.length <= type) {
			TYPE2ID = copyOf(TYPE2ID, type+1);
			es[COMP].put(COMP, TYPE2ID);
		}
		TYPE2ID[type] = e.num(ID);
		return e;
	}

	public Entity defEntity(int type) {
		Entity e = new Entity(size, type);
		if (size >= es.length) {
			es = copyOf(es, es.length + INITIAL_SIZE);
		}
		es[size] = e;
		size++;
		return e;
	}

	public Entity single(int type) {
		for (int i = 0; i < size; i++) {
			if (es[i].at(TYPE) == type)
				return entity(i);
		}
		throw new NoSuchElementException("There is no entity of type: "+type);
	}

	public int[] all(int type) {
		int[] all = new int[8];
		int c = 0;
		for (int i = 0; i < size; i++) {
			if (es[i].at(TYPE) == type) {
				if (c >= all.length) {
					all = copyOf(all, all.length*2);
				}
				all[c++] = i;
			}
		}
		return copyOf(all, c);
	}

	public boolean hasEntity(int id) {
		return id < size && id >= 0;
	}

	public boolean hasComponent(int type) {
		int[] mapping = es[0].list(0);
		return type == 0 || type < mapping.length && mapping[type] > 0;
	}

	public Entity component(int type) {
		if (type == 0)
			return es[0];
		int id = es[0].list(0)[type];
		if (id == 0)
			throw new NoSuchElementException("Unknown component type: "+type);
		return entity(id);
	}

	public Entity component(String name) {
		if (name.equals(entity(COMP).name())) {
			return entity(COMP);
		}
		int[] typeMapping = es[0].list(0);
		for (int i = 0; i < typeMapping.length; i++) {
			if (typeMapping[i] != 0) {
				Entity e = es[typeMapping[i]];
				if (name.equals(e.name())) {
					return e;
				}
			}
		}
		throw new NoSuchElementException("Unknown component named: "+name);
	}

	public Entity entity(int id) {
		if (id > size)
			throw new NoSuchElementException("This is a programming error!");
		return es[id];
	}

	public Entity entity(int type, int code) {
		for (int i = 0; i < size; i++) {
			if (es[i].at(TYPE) == type && es[i].at(CODE) == code)
				return entity(i);
		}
		throw new NoSuchElementException("There is no entity of type and code: "+type+" ,"+code);
	}

	public Entity[] entities(int...ids) {
		Entity[] res = new Entity[ids.length];
		for (int i = 0; i < ids.length; i++) {
			res[i] = entity(ids[i]);
		}
		return res;
	}

	public void save(File file) throws IOException {
		//TODO as soon as multiple threads change state save may only occur in-between complete change-sets have been applied
		// the simplest would be a sync on this object but this would prevent AIs and Human interaction to happen fully parallel.
		// while the only problem is the save. Therefore some atomic might do better as only save/change has to be synct.
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				throw new IOException("Could not create path: "+file.getParentFile());
			}
		}
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
			out.writeInt(size);
			for (int i = 0; i < size; i++) {
				es[i].save(out);
			}
		}
	}

	public static State load(File file) throws IOException {
		State g = new State();
		try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
			g.size = in.readInt();
			g.es = new Entity[g.size+(g.size%128)];
			for (int i = 0; i < g.size; i++) {
				g.es[i] = Entity.load(in);
			}
		}
		return g;
	}

	/**
	 * Loads the first entity with the given type.
	 *
	 * This allows to peek into a file just for some specific data as the general game state.
	 */
	public static Entity load(File file, int type) throws IOException {
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file), 64))) {
			int size = in.readInt();
			for (int i = 0; i < size; i++) {
				in.mark(64);
				int ez = in.readInt();
				for (int j = 0; j < ez; j++) {
					int c = in.readInt();
					int l = in.readInt();
					if (c == TYPE) {
						int ct = in.readInt();
						if (ct == type) {
							in.reset();
							return Entity.load(in);
						}
					} else {
						in.skipBytes(l*4);
					}
				}
			}
		}
		throw new NoSuchElementException("No entity of type `"+type+"` contained in file: "+file);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < size; i++) {
			b.append(es[i].toString()).append('\n');
		}
		return b.toString();
	}

	public int size() {
		return size;
	}

}
