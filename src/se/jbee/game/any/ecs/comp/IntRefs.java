package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.Entity;
import se.jbee.game.any.ecs.meta.Component;

public final class IntRefs <T extends Entity> extends UniRefs<T>{

	@Component(1)
	private volatile int[] serials;

	public IntRefs(Class<T> entityType, int[] serials) {
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
