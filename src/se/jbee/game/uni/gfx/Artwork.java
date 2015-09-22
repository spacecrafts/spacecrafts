package se.jbee.game.uni.gfx;

import java.awt.Graphics2D;

/**
 * "Pictures" placed in the back of the stage as eye-candy.
 */
public interface Artwork {

	void paint(Graphics2D gfx, Styles styles, int x, int y, int w, int h, int...rand);
}
