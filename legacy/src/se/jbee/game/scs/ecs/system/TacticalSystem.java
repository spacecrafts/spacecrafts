package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Positive;

/**
 * {@link TacticalSystem}s are {@link System}s used in battle.
 */
public abstract class TacticalSystem extends System {

	public static abstract class Ref<T extends TacticalSystem> extends System.Ref<T> {

		public Ref(byte serial) {
			super(serial);
		}
	}

	/**
	 * The number of ticks it takes to use the component from start of battle round.
	 *
	 * This is the main factor that determines the order in which banks are used
	 * during battle.
	 *
	 * Each round in a battle progresses in ticks (figurative short time periods).
	 * When the {@link #applicationDelay} number of ticks have passed the component
	 * becomes usable. Usually it is used at that moment. It can postponed as well.
	 * In that case the {@link #actuationDelay} applies.
	 *
	 * The order for components usable with the same tick on different ships is
	 * determined by highest manoeuvrability first.
	 */
	@Positive
	public byte applicationDelay;

	/**
	 * The number of ticks it takes to make the component ready when it was
	 * postponed.
	 *
	 * Example: A weapon did not fire since the target used a cloaking device. At
	 * the end of each tick postponed components can be actuated again which starts
	 * the {@link #actuationDelay}.
	 *
	 * At a delay of 1 the component becomes usable in the next round.
	 *
	 * This is also the delay used between multiple usages during a round if the
	 * component support this.
	 */
	@Positive
	public byte actuationDelay;
}
