package se.jbee.game.scs.gfx;

import java.awt.Rectangle;

/**
 * A utility class to construct GFX objects.
 * 
 * This is only to make code more readable and documenting the contract or
 * expectation of each of the {@link Gfx}s.
 */
public final class Objects implements Gfx {

	//int[] colors = new int[] { 0x006600, 0x82633F, 0xFF5014 };
	
	public static int[] icon(int type, int x, int y, int d, int color) {
		return new int[] { OBJ_ICON, type,x,y,d,color };
	}
	
	public static int[] background(int x, int y, int w, int h, int no) {
		return new int[] { OBJ_BACKGROUND, x,y,w,h, no };
	}
	
	public static int[] border(Rectangle area) {
		return border(area.x, area.y, area.width, area.height);
	}
	
	public static int[] border(int x, int y, int w, int h) {
		return new int[] { OBJ_BORDER, x,y,w,h };
	}
	
	public static int[] focusBox(int x, int y, int w, int h) {
		return new int[] { OBJ_FOCUS_BOX, x,y,w,h };
	}
	
	public static int[] focusLine(int x1, int y1, int x2, int y2) {
		return new int[] { OBJ_FOCUS_LINE, x1,y1,x2,y2 };
	}
	
	public static int[] star(int x, int y, int d, int c, int nrand) {
		return new int[] { OBJ_STAR, x,y,d,c, nrand };
	}
	
	public static int[] starClip(int x, int y, int d, int c, int nrand) {
		return new int[] { OBJ_STAR_CLIP, x,y,d,c,nrand };
	}	

	public static int[] planet(int x, int y, int d, int c, int nrand) {
		return new int[] { OBJ_PLANET, x,y,d,c,nrand };
	}
	
	public static int[] planetClip(int x, int y, int d, int c, int nrand) {
		return new int[] { OBJ_PLANET_CLIP, x,y,d,c,nrand };
	}		
	
	// TODO text has to define the bounding box and the alignment within so that the rendering can use text measure to move text to the correct position if this is desired.
	// there should be different placement methods: absolute (simply pick x,y) box (use the box x,y,w,h and alsignment)
	public static int[] text(int x, int y, int font, int size, int color, int ntext) {
		return new int[] { OBJ_TEXT, x,y, font, size, color, ntext };
	}

}
