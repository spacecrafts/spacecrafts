package se.jbee.game.common.gfx;

import java.awt.Graphics2D;

/**
 * Objects and "pictures" placed in the back of the stage to set the scene. 
 */
public interface Backdrop {

	void paint(Styles styles, Graphics2D gfx, int x, int y, int w, int h, int...rand);
}
