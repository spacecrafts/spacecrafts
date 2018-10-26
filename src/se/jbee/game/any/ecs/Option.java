package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.comp.Name;

/**
 * A {@link Classification} that is a set of options.
 */
public abstract class Option extends Constant {

	public char groupCode;
	public char itemCode;

	@Override
	public final char groupCode() {
		return groupCode;
	}

	@Override
	public final char itemCode() {
		return itemCode;
	}

	public Name groupTitle() {
		//TODO loaded from text files using the name of the Entity as file name and the group char as key
		return null;
	}
}
