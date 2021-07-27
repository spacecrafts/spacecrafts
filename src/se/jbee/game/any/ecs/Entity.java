package se.jbee.game.any.ecs;

import java.lang.reflect.Field;

import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;

/**
 * The base class for all entities in the Entity Component System.
 *
 * {@link Component}s are fields in {@link Entity} classes annotated with the
 * {@link Component} annotation.
 */
public abstract class Entity {

	/**
	 * Each {@link Entity} has a {@link #serial} that is unique for the
	 * {@link EntityType} in a global {@link State} context.
	 */
	@Component(0) @NonNegative
	public short serial;

	@Override
	public final String toString() {
		StringBuilder b = new StringBuilder();
		toString(b, getClass());
		return b.toString();
	}

	private void toString(StringBuilder b, Class<?> type) {
		Class<?> supertype = type.getSuperclass();
		if (Entity.class.isAssignableFrom(supertype)) {
			toString(b, supertype);
		}
		for (Field f : type.getDeclaredFields()) {
			if (f.isAnnotationPresent(Component.class)) {
				Component comp = f.getAnnotation(Component.class);
				String name = comp.alias().isEmpty() ? f.getName() : comp.alias();
				b.append(comp.value()).append(' ').append(name).append('=');
				try {
					b.append(f.get(this).toString());
				} catch (Exception e) {
					b.append('?');
				}
				b.append('\n');
			}
		}
	}

}
