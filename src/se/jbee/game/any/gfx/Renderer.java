package se.jbee.game.any.gfx;


import java.awt.Graphics2D;

public interface Renderer {

	void render(Stage stage, Dimension screen, Resources styles, Graphics2D gfx);
}
