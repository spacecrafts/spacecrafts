package se.jbee.game.common.gfx;

public final class Dimension {

	public final int width;
	public final int height;
	
	public Dimension(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public Dimension(java.awt.Dimension d) {
		this(d.width, d.height);
	}

	@Override
	public String toString() {
		return width+"x"+height;
	}
}
