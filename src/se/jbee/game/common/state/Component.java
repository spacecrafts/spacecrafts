package se.jbee.game.common.state;

/**
 * Components are the keys used in an {@link Entity} therefore values have to be
 * unique.
 * 
 * The meaning of each key is modeled through an entity as well. The ID of an
 * entity associated with a component can be resolved from a special mapping the
 * {@link State} keeps track of for components. So the {@link #ID} component
 * must not be explained by the entity with ID {@value #ID}.
 * 
 * The {@link #TYPE} component is used to group entities of the same kind
 * together. All entities that explain/are components for example use the
 */
public interface Component {

	int COMPONENT = 0;
	int TYPE = 1;
	int ID   = 2;
	int NAME = 3;
	
}
