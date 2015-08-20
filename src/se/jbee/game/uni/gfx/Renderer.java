package se.jbee.game.uni.gfx;


import java.awt.Graphics2D;

public interface Renderer {

	void render(Stage scene, Dimension screen, Styles styles, Graphics2D gfx);
}
