package se.jbee.game.scs.gfx;

import java.awt.Color;

public final class Colors {

	public static final int
		HIGHLIGHT_TEXT = 1,
		NORMAL_TEXT = 2;
	

	private static final Color[] COLORS = { new Color(0x8899FF), Color.WHITE, new Color(0x8899FF) }; 
	
	public static Color fromType(int type) {
		return type < 0 || type >= COLORS.length ? COLORS[0] : COLORS[type];
	}
}
