package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.Entity;

public interface Refs<T extends Entity> extends ComponentType {

	int size();

	int serial(int n);

	Class<? extends T> entityType(int n);

}
