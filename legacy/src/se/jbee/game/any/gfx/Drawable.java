package se.jbee.game.any.gfx;

import java.awt.Graphics2D;

/**
 * An object that can be drawn on the {@link Stage}. 
 */
public interface Drawable {

	void draw(Graphics2D gfx, Resources resources);
}
