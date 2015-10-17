package se.jbee.game.any.gfx;

import java.awt.Graphics2D;
import java.util.List;

public interface Obj {

	void draw(Graphics2D gfx, Styles styles, List<int[]> data);
}
