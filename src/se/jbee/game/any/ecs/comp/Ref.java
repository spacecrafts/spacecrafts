package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.Entity;

public interface Ref<T extends Entity> extends ComponentType {

	Class<T> entityType();

	int serial();
}
