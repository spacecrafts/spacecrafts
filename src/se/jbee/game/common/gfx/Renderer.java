package se.jbee.game.common.gfx;

import java.awt.Dimension;
import java.awt.Graphics2D;

import se.jbee.game.common.process.Stage;

public interface Renderer {

	void render(Stage scene, Dimension screen, Palette palette, Graphics2D gfx);
}