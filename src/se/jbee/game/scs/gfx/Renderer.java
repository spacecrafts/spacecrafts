package se.jbee.game.scs.gfx;

import java.awt.Dimension;
import java.awt.Graphics2D;

import se.jbee.game.scs.process.Scene;

public interface Renderer {

	void render(Scene scene, Dimension screen, Graphics2D gfx);
}