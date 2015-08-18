package se.jbee.game.scs.screen;

import java.awt.Rectangle;

import se.jbee.game.common.gfx.Dimension;

/**
 * How the game divides the screen area into views.  
 */
public final class View {

	// All measures as fractions of the overall width.
	//       _____________________________________________
	// 1/32 |           2/3             |______1/3________| 1/32
	// 1/32 |___________________________|               ¦ |
	//      |                           |        T      ¦ |
	//      |                           |               ¦ |
	//      |                           |_______________¦_| 
	//      |                           |_________________| 1/32 
	//      |                           |               ¦ |
	//      |             C             |        M      ¦ |
	//      |                           |               ¦ |
	//      |                           |_______________¦_| 
	//      |                           |_________________| 1/32
	//      |                           |               ¦ |
	//      |                           |        B      ¦ |
	//      |___________________________|               ¦ |
	// 1/32 |                           |_______________¦_| 
	// 1/32 |___________________________|_________________| 1/32
	//                                                 1/32
	// C = Center
	// T = Top
	// M = Middle
	// B = Bottom
	// T, M, B are equally height and show context related mini-screens.
	
	/**
	 * A grid of 32x32 (max) is placed in the middle of the C view. 
	 */
	public static int cellDiameter(Dimension screen) {
		// height - 2x spacing (left+right) divided by 32 cells (maximum)
		return (screen.height-(screen.width/8))/32;		
	}
	
	public static Rectangle centerView(Dimension screen) {
		int w = screen.width;
		return new Rectangle(0, w/16, w*2/3, screen.height-(w/8));
	}
	
	public static Rectangle topView(Dimension screen) {
		int w = screen.width;
		return new Rectangle(w*2/3, w/32, w/3-w/32, (screen.height-w/8)/3);
	}
	
	public static Rectangle middleView(Dimension screen) {
		int w = screen.width;
		int h = (screen.height-w/8)/3;
		return new Rectangle(w*2/3, w/16+h, w/3-w/32, h);
	}

	public static Rectangle bottomView(Dimension screen) {
		int w = screen.width;
		int h = (screen.height-w/8)/3;
		return new Rectangle(w*2/3, 3*w/32+h*2, w/3-w/32, h);
	}

}
