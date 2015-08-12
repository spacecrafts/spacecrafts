package se.jbee.game.scs.gfx;

/**
 * A utility class to construct GFX objects.
 * 
 * This is only to make code more readable and documenting the contract or
 * expectation of each of the {@link Object}s.
 */
public final class Objects implements Object {

	//int[] colors = new int[] { 0x006600, 0x82633F, 0xFF5014 };
	
	public static int[] background(int x, int y, int w, int h, int no) {
		return new int[] { BACKGROUND, x,y,w,h, no };
	}
	
	public static int[] border(int x, int y, int w, int h) {
		return new int[] { BORDER, x,y,w,h };
	}
	
	public static int[] focusBox(int x, int y, int w, int h) {
		return new int[] { FOCUS_BOX, x,y,w,h };
	}
	
	public static int[] focusLine(int x1, int y1, int x2, int y2) {
		return new int[] { FOCUS_LINE, x1,y1,x2,y2 };
	}
	
	public static int[] star(int x, int y, int r, int c, int nrand) {
		return new int[] { STAR, x,y,r,c,nrand };
	}
	
	public static int[] starArc(int x, int y, int r, int c, int nrand) {
		return new int[] { STAR_ARC, x,y,r,c,nrand };
	}	

	public static int[] planet(int x, int y, int r, int c, int nrand) {
		return new int[] { PLANET, x,y,r,c,nrand };
	}	
	
	// TODO text has to define the bounding box and the alignment within so that the rendering can use text measure to move text to the correct position if this is desired.
	// there should be different placement methods: absolute (simply pick x,y) box (use the box x,y,w,h and alsignment)
	public static int[] text(int x, int y, int type, int size, int color, int ntext) {
		return new int[] { TEXT, x,y, type, size, color, ntext };
	}
}
