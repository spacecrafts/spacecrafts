package se.jbee.game.scs.gfx;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.GfxObj;
import se.jbee.game.any.gfx.obj.Text;
import se.jbee.game.scs.gfx.obj.Background;
import se.jbee.game.scs.gfx.obj.Button;
import se.jbee.game.scs.gfx.obj.Icon;
import se.jbee.game.scs.gfx.obj.Path;
import se.jbee.game.scs.gfx.obj.Planet;
import se.jbee.game.scs.gfx.obj.Rect;
import se.jbee.game.scs.gfx.obj.Ring;
import se.jbee.game.scs.gfx.obj.Star;
import se.jbee.game.scs.gfx.obj.Techwheel;

/**
 * A utility class to construct GFX objects.
 *
 * This is only to make code more readable and documenting the contract or
 * expectation of each of the {@link Gfx}s.
 *
 * There are two rules:
 * - index 0 holds the object type
 * - index 1 holds the number of successive data elements that belong to the object
 */
public final class GfxObjs implements Gfx {

	//int[] colors = new int[] { 0x006600, 0x82633F, 0xFF5014 };

	public static GfxObj text(int key, int left, int top, int font, int size, int color) {
		return text(key, left,top,font,size,color, ALIGN_NW, -1, -1);
	}
	
	public static GfxObj text(int key, int left, int top, int font, int size, int color, int align, int right, int bottom) {
		return new Text(left,top, right, bottom, font, size, color, align, key, "");
	}
	
	public static GfxObj fixtext(int left, int top, int font, int size, int color, int[] text) {
		return fixtext(left, top, font, size, color, new String(text, 0, text.length));
	}
	
	public static GfxObj fixtext(int left, int top, int font, int size, int color, String text) {
		return fixtext(left,top,font,size,color, ALIGN_NW, -1, -1, text);
	}

	//TODO stretch (making size so that the text takes X/Y bounds given)
	public static Text fixtext(int left, int top, int font, int size, int color, int align, int right, int bottom, int[] text) {
		return fixtext(left, top, font, size, color, align, right, bottom, new String(text, 0, text.length));
	}
	
	public static Text fixtext(int left, int top, int font, int size, int color, int align, int right, int bottom, String text) {
		return new Text(left, top, right, bottom, font, size, color, align, 0, text);
	}

	public static GfxObj button(int x, int y, int d, int piecolor, int textcolor) {
		return new Button(x, y, d, piecolor);
	}

	public static GfxObj icon(int type, int x, int y, int d, int color) { //TODO add color-effect
		return new Icon(type,x,y,d,color);
	}

	public static GfxObj techwheel(int xc, int yc, int d, int color) {
		return new Techwheel(xc,yc,d,color);
	}

	public static GfxObj ring(int xc, int yc, int d, int thickness, int fg) {
		return new Ring(xc,yc,d, thickness, fg);
	}

	public static GfxObj background(int x, int y, int w, int h, int no, long seed) {
		return new Background(x,y,w,h, no, seed);
	}

	public static GfxObj border(Rectangle area) {
		return border(area.x, area.y, area.width, area.height);
	}

	public static GfxObj border(int x, int y, int w, int h) {
		return new Rect( x,y,w,h );
	}

	public static GfxObj focusBox(int x, int y, int w, int h) {
		return new Rect(x,y,w,h );
	}

	public static GfxObj path(int type, int color, int stroke, int x1, int y1, int x2, int y2) {
		return new Path(type, color, stroke, new int[] { x1, y1, x2, y2 });
	}

	public static GfxObj path(int type, int color, int stroke, int x1, int y1, int x2, int y2, int x3, int y3) {
		return new Path(type, color, stroke, new int[] { x1, y1, x2, y2, x3, y3 });
	}

	public static GfxObj timeLine(int x1, int y1, int x2, int y2) {
		return path(PATH_EDGY, COLOR_TEXT_NORMAL, 1, x1,y1,x2,y2);
	}

	public static GfxObj focusLine(int x1, int y1, int x2, int y2) {
		return path(PATH_EDGY, COLOR_TEXT_HIGHLIGHT, 1, x1,y1,x2,y2);
	}

	public static GfxObj star(int x, int y, int d, int rgba) {
		return new Star(x,y,d, rgba, 0L, false);
	}

	public static GfxObj starCut(int x, int y, int d, int rgba, long seed) {
		return new Star(x,y, d, rgba, seed, true);
	}

	public static GfxObj planet(int x, int y, int d, int type, int rgba) {
		return new Planet(x,y,d,type,rgba, false);
	}

	public static GfxObj planetCut(int x, int y, int d, int c, int rgba) {
		return new Planet(x,y,d,c,rgba, true);
	}

}
