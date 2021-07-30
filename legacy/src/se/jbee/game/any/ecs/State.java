package se.jbee.game.any.ecs;

import static java.lang.reflect.Modifier.isFinal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import se.jbee.game.any.ecs.comp.Ref;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;

public final class State {

	private static final Map<String, EntityTypeInfo<?>> entityTypesByName = new HashMap<>();

	public static <T extends Entity> void register(Class<T> type) {
		if (Modifier.isAbstract(type.getModifiers())) {
			throw new IllegalArgumentException(
					"Entity type is abstract. Register only concrete entity types:" + type.getSimpleName());
		}
		if (!isFinal(type.getModifiers())) {
			throw new IllegalArgumentException(
					"Entity type not final. Register only concrete entity types: " + type.getSimpleName());
		}
		entityTypesByName.putIfAbsent(type.getAnnotation(EntityType.class).value(),
				new EntityTypeInfo<>((short) entityTypesByName.size(), type));
	}

	private static final class EntityTypeInfo<T extends Entity> {

		/**
		 * The code used to refer to a specific type in a {@link State} context.
		 *
		 * This is like a extra level of indirection building a lookup table for the
		 * type {@link #id}.
		 *
		 * When loading games this lookup table is contained in the file. As mapping
		 * might have changed a special loading lookup table is build when recreating
		 * the game {@link State}. On next {@link State} save the current code (this
		 * code) and table is used.
		 */
		final short refCode;
		/**
		 * The unique name for the type of {@link Entity} this represents.
		 *
		 * This name is used in persistent data (e.g. files) to refer to this type so
		 * that code (behaviour) and data (information) are independent from another.
		 */
		final String id;
		final Class<T> type;
		//TODO add function that read/write the components of such an entity using reflection

		EntityTypeInfo(short code, Class<T> type) {
			this.refCode = code;
			this.type = type;
			this.id = type.getAnnotation(EntityType.class).value();
		}

	}

	public static State load(DataInputStream src) {
		// a state file is simply a list of lists of different types of entities
		// each list has a header:
		// refCode, (used within a file to refer to the type)
		// name, (used to find the same type when loading)
		// number of entities (of that type)
		// ... (followed by the entity data; data is mapped to class, not other way around)
		return null;
	}

	public void save(DataOutputStream dest) throws IOException {
		dest.writeShort(instancesByType.size());
		//TODO build refCode mapping
		for (Entities<?> typeInstances : instancesByType.values()) {
			if (typeInstances.size > 0) { //TODO do not save constants? => settings, default yes
				dest.writeShort(typeInstances.type.refCode);
				writeChars(dest, typeInstances.type.id);
				dest.write(typeInstances.size);
				for (Object e : typeInstances.entities) {

				}
			}
		}
	}

	private static void writeChars(DataOutputStream dest, CharSequence chars) throws IOException {
		int len = chars.length();
		dest.writeShort(len);
		for (int i = 0; i < len; i++) {
			dest.writeChar(chars.charAt(i));
		}
	}

	private static final class Entities<E extends Entity> {

		EntityTypeInfo<E> type;
		int size;
		Object[] entities;

		@SuppressWarnings("unchecked")
		E get(int serial) {
			return serial < 0 || serial >= size ? null : (E) entities[serial];
		}

	}

	private ConcurrentMap<Class<?>, Entities<?>> instancesByType = new ConcurrentHashMap<>();

	public <E extends Entity> E entity(Ref<E> ref) {
		return entitiesFor(ref.entityType()).get(ref.serial());
	}

	public <E extends Entity> E entity(Refs<E> refs, int n) {
		return entitiesFor(refs.entityType(n)).get(refs.serial(n));
	}

	@SuppressWarnings("unchecked")
	private <E extends Entity> Entities<E> entitiesFor(Class<E> entityType) {
		return (Entities<E>) (instancesByType.computeIfAbsent(entityType, t -> new Entities<>()));
	}

}
