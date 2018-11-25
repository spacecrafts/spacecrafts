package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;

public final class ShortRefs<T extends Entity> extends UniRefs<T>{

	@Component(1)
	private volatile short[] serials;

	public ShortRefs(Class<T> entityType, short[] serials) {
		super(entityType);
		this.serials = serials;
	}

	@Override
	public int size() {
		return serials.length;
	}

	@Override
	public int serial(int n) {
		return serials[n];
	}

}
