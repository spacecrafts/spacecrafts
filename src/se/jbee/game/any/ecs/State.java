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
import se.jbee.game.any.ecs.meta.Entity;

public final class State {

	private static final Map<String, EntityTypeInfo<?>> entityTypesByName = new HashMap<>();

	public static <T extends EntityType> void register(Class<T> type) {
		if (Modifier.isAbstract(type.getModifiers())) {
			throw new IllegalArgumentException(
					"Entity type is abstract. Register only concrete entity types:" + type.getSimpleName());
		}
		if (!isFinal(type.getModifiers())) {
			throw new IllegalArgumentException(
					"Entity type not final. Register only concrete entity types: " + type.getSimpleName());
		}
		entityTypesByName.putIfAbsent(type.getAnnotation(Entity.class).value(),
				new EntityTypeInfo<>((short) entityTypesByName.size(), type));
	}

	private static final class EntityTypeInfo<T extends EntityType> {

		final short refCode;
		final String name;
		final Class<T> type;
		//TODO add function that read/write the components of such an entity using reflection

		EntityTypeInfo(short code, Class<T> type) {
			this.refCode = code;
			this.type = type;
			this.name = type.getAnnotation(Entity.class).value();
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
				writeChars(dest, typeInstances.type.name);
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

	private static final class Entities<E extends EntityType> {

		EntityTypeInfo<E> type;
		int size;
		Object[] entities;

		@SuppressWarnings("unchecked")
		E get(int serial) {
			return serial < 0 || serial >= size ? null : (E) entities[serial];
		}

	}

	private ConcurrentMap<Class<?>, Entities<?>> instancesByType = new ConcurrentHashMap<>();

	public <E extends EntityType> E entity(Ref<E> ref) {
		return entitiesFor(ref).get(ref.serial());
	}

	@SuppressWarnings("unchecked")
	private <E extends EntityType> Entities<E> entitiesFor(Ref<E> ref) {
		return (Entities<E>) (instancesByType.computeIfAbsent(ref.entityType(), t -> new Entities<>()));
	}

}
