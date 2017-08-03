package se.jbee.game.scs.gfx;

import static se.jbee.game.scs.gfx.Gfx.Align.NW;

import java.awt.Rectangle;

import se.jbee.game.any.gfx.Drawable;
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
public final class Draw implements Gfx {

	//int[] colors = new int[] { 0x006600, 0x82633F, 0xFF5014 };

	public static Text text(int key, int left, int top, FontStyle font, int size, Hue color) {
		return text(key, left,top,font,size,color, NW, -1, -1);
	}
	
	public static Text text(int key, int left, int top, FontStyle font, int size, Hue color, Align align, int right, int bottom) {
		return new Text(left,top, right, bottom, font, size, color, align, key, "");
	}
	
	public static Text label(int left, int top, FontStyle font, int size, Hue color, int[] text) {
		return label(left, top, font, size, color, new String(text, 0, text.length));
	}
	
	public static Text label(int left, int top, FontStyle font, int size, Hue color, String text) {
		return label(left,top,font,size,color, NW, -1, -1, text);
	}

	//TODO stretch (making size so that the text takes X/Y bounds given)
	public static Text label(int left, int top, FontStyle font, int size, Hue color, Align align, int right, int bottom, int[] text) {
		return label(left, top, font, size, color, align, right, bottom, new String(text, 0, text.length));
	}
	
	public static Text label(int left, int top, FontStyle font, int size, Hue color, Align align, int right, int bottom, String text) {
		return new Text(left, top, right, bottom, font, size, color, align, 0, text);
	}

	public static Drawable button(int x, int y, int d, Hue piecolor, Hue textcolor) {
		return new Button(x, y, d, piecolor);
	}

	public static Drawable icon(int type, int x, int y, int d, Hue color) { //TODO add color-effect
		return new Icon(type,x,y,d,color);
	}

	public static Drawable techwheel(int xc, int yc, int d, Hue color) {
		return new Techwheel(xc,yc,d,color);
	}

	public static Drawable ring(int xc, int yc, int d, int thickness, Hue fg) {
		return new Ring(xc,yc,d, thickness, fg);
	}

	public static Drawable background(int x, int y, int w, int h, int no, long seed) {
		return new Background(x,y,w,h, no, seed);
	}

	public static Drawable border(Rectangle area) {
		return border(area.x, area.y, area.width, area.height);
	}

	public static Drawable border(int x, int y, int w, int h) {
		return new Rect( x,y,w,h );
	}

	public static Drawable focusBox(int x, int y, int w, int h) {
		return new Rect(x,y,w,h );
	}

	public static Drawable path(int type, Hue color, int stroke, int x1, int y1, int x2, int y2) {
		return new Path(type, color, stroke, new int[] { x1, y1, x2, y2 });
	}

	public static Drawable path(int type, Hue color, int stroke, int x1, int y1, int x2, int y2, int x3, int y3) {
		return new Path(type, color, stroke, new int[] { x1, y1, x2, y2, x3, y3 });
	}

	public static Drawable timeLine(int x1, int y1, int x2, int y2) {
		return path(PATH_EDGY, Hue.TEXT_NORMAL, 1, x1,y1,x2,y2);
	}

	public static Drawable focusLine(int x1, int y1, int x2, int y2) {
		return path(PATH_EDGY, Hue.TEXT_HIGHLIGHT, 1, x1,y1,x2,y2);
	}

	public static Drawable star(int x0, int y0, int dia, int rgba) {
		return new Star(x0,y0,dia, rgba, 0L, false);
	}

	public static Drawable partialStar(int x0, int y0, int dia, int rgba, long seed) {
		return new Star(x0,y0, dia, rgba, seed, true);
	}

	public static Drawable planet(int x0, int y0, int dia, int type, int rgba) {
		return new Planet(x0,y0,dia,type,rgba, false);
	}

	public static Drawable planetCut(int x, int y, int dia, int c, int rgba) {
		return new Planet(x,y,dia,c,rgba, true);
	}

}
