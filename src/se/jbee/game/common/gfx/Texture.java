package se.jbee.game.common.gfx;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Texture {

	BufferedImage create(Styles styles);
}
