package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.EntityType;
import se.jbee.game.any.ecs.meta.Component;

public final class Refs<T extends EntityType> implements ComponentType {

	@Component(0)
	public volatile short[] entityIds;

	public int size() {
		return entityIds.length;
	}
}
