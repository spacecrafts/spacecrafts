package se.jbee.game.uni.gfx;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Texture {

	BufferedImage create(Styles styles);
}
