package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Option;

/**
 * Instead of hard coding possible game setup GUIs for e.g. the game speed and their effects on the
 * {@link Settings} this can be a configuration from file that describes the choice and the effects.
 *
 * {@link Preference}s are presented grouped by their {@link #groupCode()}.
 */
public final class Preference extends Option {

}
