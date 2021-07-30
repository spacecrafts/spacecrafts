package se.jbee.game.any.gfx;

public final class Point {

	public final int x;
	public final int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return x+":"+y;
	}
}
