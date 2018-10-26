package se.jbee.game.any.ecs;

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
