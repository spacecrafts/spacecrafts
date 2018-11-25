package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.EntityType;

public abstract class VariedRefs<T extends Entity> implements Refs<T> {

	@Component(1)
	private Ref<? extends T>[] refs;

	public VariedRefs(Ref<? extends T>[] refs) {
		this.refs = refs;
	}

	@Override
	public final int size() {
		return refs.length;
	}

	@Override
	public final int serial(int n) {
		return refs[n].serial();
	}

	@Override
	public final Class<? extends T> entityType(int n) {
		return refs[n].entityType();
	}

	@Override
	public final String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[');
		Class<?> lastRefType = null;
		for (int i = 0; i < size(); i++) {
			Class<? extends T> refType = entityType(i);
			if (refType != lastRefType) {
				b.append(' ').append(refType.getAnnotation(EntityType.class).value());
				lastRefType = refType;
			}
			b.append(':').append(serial(i));
		}
		b.append(']');
		return b.toString();
	}

}
