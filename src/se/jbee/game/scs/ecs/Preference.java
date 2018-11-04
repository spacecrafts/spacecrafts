package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Preselection;

/**
 * Instead of hard coding possible game setup GUIs for e.g. the game speed and their effects on the
 * {@link Settings} this can be a configuration from file that describes the choice and the effects.
 *
 * {@link Preference}s are presented grouped by their {@link #groupCode()} and use {@link #groupName()}.
 */
public final class Preference extends Preselection {

	// a preset is a sequence of commands...
	static class Command { // this is like the commands the UI then uses to modify game state
		// which entity
		// which constant
		// which component
		// value to set
	}
	// these really are static commands - the only difference is that commands use the serial to refer to entities
	// maybe presets are special as they just refer to Settings, maybe View and Game which all just have one entity in a game
	// so they could use serial in the sense that its known it must be serial 0
}
