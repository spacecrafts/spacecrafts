package se.jbee.game.any.ecs;

import se.jbee.game.any.ecs.comp.Name;

/**
 * A {@link Constant} set of preselected options that have no common axis, order
 * or sequence but that is grouped into groups of options.
 */
public abstract class Preselection extends Constant {

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

	public Name groupName() {
		//TODO loaded from text files using the name of the Entity as file name and the group char as key
		return null;
	}
}
