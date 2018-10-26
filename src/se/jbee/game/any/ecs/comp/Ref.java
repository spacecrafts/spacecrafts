package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.EntityType;

public interface Ref<T extends EntityType> extends ComponentType {

	Class<T> entityType();

	int serial();
}
