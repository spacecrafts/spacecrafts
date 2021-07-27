package se.jbee.game.any.ecs;

/**
 * A set of options that belong to a common space but do not overlap. They have
 * no particular order or sequence.
 *
 * @see Spectrum
 */
public abstract class Classification extends Constant {

	public char code;

	@Override
	public final char groupCode() {
		return '0';
	}

	@Override
	public final char itemCode() {
		return code;
	}
}
