package se.jbee.game.scs.gfx;

import static java.lang.Math.max;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.QuadCurve2D;

import se.jbee.game.uni.gfx.Styles;

/**
 * Rendering of all the icons used for {@link Gfx#OBJ_COMPONENT}. 
 */
public class Icon implements Gfx {

	public static void draw(Styles styles, Graphics2D gfx, int type, int x0, int y0, int d) {

		Stroke s = gfx.getStroke();

		gfx.setColor(styles.color(COLOR_SLOT_BORDER));
		gfx.drawRect(x0, y0, d, d);
		drawIcon(styles, gfx, type, x0, y0, d);
		drawIcon(styles, gfx, type, x0+d, y0+d, d);
		drawIcon(styles, gfx, type, x0+d, y0, d);
		drawIcon(styles, gfx, type, x0, y0+d, d);

		gfx.setStroke(s);
	}

	private static void drawIcon(Styles styles, Graphics2D gfx, int type, int x0, int y0, int d) {
		switch (type) {
		case ICON_SHIELD1: drawShield1(styles, gfx, x0, y0, d); break;
		case ICON_SHIELD2: drawShield2(styles, gfx, x0, y0, d); break;
		case ICON_SHIELD3: drawShield3(styles, gfx, x0, y0, d); break;
		case ICON_SHIELD4: drawShield4(styles, gfx, x0, y0, d); break;
		case ICON_SHIELD5: drawShield5(styles, gfx, x0, y0, d); break;
		case ICON_BEAM1  : drawBeam1(styles, gfx, x0, y0, d); break;
		case ICON_BEAM2  : drawBeam2(styles, gfx, x0, y0, d); break;
		case ICON_BEAM3  : drawBeam3(styles, gfx, x0, y0, d); break;
		case ICON_BEAM4  : drawBeam4(styles, gfx, x0, y0, d); break;
		case ICON_BEAM5  : drawBeam5(styles, gfx, x0, y0, d); break;
		case ICON_DRIVE1 : drawDrive1(styles, gfx, x0, y0, d); break;
		case ICON_DRIVE2 : drawDrive2(styles, gfx, x0, y0, d); break;
		case ICON_DRIVE3 : drawDrive3(styles, gfx, x0, y0, d); break;
		case ICON_DRIVE4 : drawDrive4(styles, gfx, x0, y0, d); break;
		case ICON_REACTOR1 : drawReactor1(styles, gfx, x0, y0, d); break;
		case ICON_REACTOR2 : drawReactor2(styles, gfx, x0, y0, d); break;
		case ICON_REACTOR3 : drawReactor3(styles, gfx, x0, y0, d); break;
		}
	}

	private static void drawReactor3(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_ENERGY));
		int k = d/2;
		int j = k/2;
		int l = k-(k%2)+1;
		gfx.fillOval(x0+k-j, y0+k-j, l,l);
		gfx.setStroke(new BasicStroke(Math.max(1f, d/10f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawPolygon(
				new int[]{x0+k, x0+k+k, x0+k+k, x0+k, x0, x0}, 
				new int[]{y0, y0+k-j, y0+k+j,y0+k+k,y0+k+j, y0+k-j}, 6);
	}
	
	private static void drawReactor2(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_ENERGY));
		int k = d/3;
		int c = (d-(k*3)); 
		x0 += c;
		y0 += c;
		int j = k*3/4;
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		gfx.drawPolygon(
				new int[]{x0+k, x0+k+k, x0+k+k+k,x0+k+k+k,x0+k+k,x0+k,x0,x0}, 
				new int[]{y0,y0,y0+k,y0+k+k,y0+k+k+k,y0+k+k+k,y0+k+k,y0+k}, 8);

		gfx.drawLine(x0+k, y0+k, x0+k+k, y0+k);
		gfx.drawLine(x0+k, y0+k+k, x0+k+k, y0+k+k);
		gfx.drawLine(x0+k, y0+k+k/2, x0+k+k, y0+k+k/2);
	}

	private static void drawReactor1(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_ENERGY));
		int k = d/3;
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawRoundRect(x0, y0, d, d, 2, 2);
		int l = d-k-k+1;
		gfx.fillRect(x0+k, y0+k, l, l);
	}

	private static void drawBeam1(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int m = d/2;
		int k = m/3;
		int x = x0+m;
		int y = y0+m;
		gfx.drawLine(x-k, y-k, x, y);
		gfx.drawLine(x+k, y-k, x, y);
		gfx.drawLine(x, y, x, y+k);
	}

	private static void drawBeam2(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m-r/2;
		int yc = y0+m-r/2;
		int x = xc-r;
		int y = yc-r+r/2;
		gfx.fillOval(x, y, r, r);
		gfx.fillOval(xc+r, y, r, r);
		gfx.fillOval(xc, y+r+r/2, r, r);
	}

	private static void drawBeam3(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
	}

	private static void drawBeam4(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int r = max(1, d/4);
		int m = r*2;
		int xc = x0+d/2+1;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
		gfx.fillOval(xc-r, yc, m, m);
	}
	
	private static void drawBeam5(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int k = d/4;
		int j = 2*k;
		int t = k*2/3;
		int r = max(1, k/3);
		gfx.setStroke(new BasicStroke(d/16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.fillOval(x0+t, y0+t, r, r);
		gfx.fillOval(x0+d-t-1, y0+t, r, r);
		gfx.fillOval(x0+t, y0+d-t-1, r, r);
		gfx.fillOval(x0+d-t-1, y0+d-t-1, r, r);
		
		r = max(1, k*2/5);
		t=d*4/13;
		gfx.fillOval(x0+t, y0+t, r, r);
		gfx.fillOval(x0+d-t-1, y0+t, r, r);
		gfx.fillOval(x0+t, y0+d-t-1, r, r);
		gfx.fillOval(x0+d-t-1, y0+d-t-1, r, r);

		r = k/2;
		t=d/2;
		gfx.fillOval(x0+t, y0+t, r, r);
		gfx.fillOval(x0+d-t-1, y0+t, r, r);
		gfx.fillOval(x0+t, y0+d-t-1, r, r);
		gfx.fillOval(x0+d-t-1, y0+d-t-1, r, r);
	
	}

	private static void drawDrive1(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_DRIVE));
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int m = d/2;
		int x = x0+m;
		int y = y0+m;
		m -= d/8;
		gfx.drawLine(x-m, y-m, x, y);
		gfx.drawLine(x-m, y+m, x, y);
		gfx.drawLine(x, y-m, x+m, y);
		gfx.drawLine(x, y+m, x+m, y);
	}

	private static void drawDrive2(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_DRIVE));
		int k = d/4;
		int m = 2*k;
		int x = x0+m;
		int y = y0+m;
		m -= d/8;
		gfx.fillPolygon(new int[] {x-m, x, x-m}, new int[] {y-m, y, y+m}, 3);
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawLine(x, y-m, x+m, y);
		gfx.drawLine(x, y+m, x+m, y);
	}

	private static void drawDrive3(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_DRIVE));
		int k = d/4;
		int m = 2*k;
		int x = x0+m;
		int y = y0+m;
		m -= d/8;
		gfx.fillPolygon(new int[] {x-m, x, x-m}, new int[] {y-m, y, y+m}, 3);
		x+=m+1;
		gfx.fillPolygon(new int[] {x-m, x, x-m}, new int[] {y-m, y, y+m}, 3);
	}
	
	private static void drawDrive4(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_DRIVE));
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int k = d/4;
		int m = 2*k;
		int x = x0+m;
		int y = y0+m;
		gfx.drawLine(x-m, y-m+k, x-k, y);
		gfx.drawLine(x-m, y+m-k, x-k, y);
		gfx.drawLine(x+m, y-m+k, x+k, y);
		gfx.drawLine(x+m, y+m-k, x+k, y);
		gfx.drawLine(x-k, y-m, x, y-k);
		gfx.drawLine(x+k, y-m, x, y-k);
		gfx.drawLine(x-k, y+m, x, y+k);
		gfx.drawLine(x+k, y+m, x, y+k);
	}

	private static void drawShield1(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawArc(x0, y0, d, d, 30, 120);
		gfx.drawArc(x0, y0+d/2, d, d, 30, 120);
	}

	private static void drawShield2(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int d4 = d/4;
		gfx.drawArc(x0, y0+d4, d, d, 20,140);
		gfx.drawArc(x0+d4, y0+d4+d4, d/2, d/2, 20, 140);
	}

	private static void drawShield3(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		int r = d/2;
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int x = x0;
		gfx.draw(new QuadCurve2D.Float(x, y0+r, x+r/2, y0, x+r, y0+r));
		x += r;
		gfx.draw(new QuadCurve2D.Float(x, y0+r, x+r/2, y0+r+r, x+r, y0+r));
		y0+= r;
		x = x0;
	}
	
	private static void drawShield4(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		int r = d/4;
		int c = (d-(r*4)); 
		x0 += c;
		y0 += c;
		gfx.drawOval(x0, y0, r, r);
		gfx.drawOval(x0+r+r, y0, r, r);
		gfx.drawOval(x0, y0+r+r, r, r);
		gfx.drawOval(x0+r+r, y0+r+r, r, r);
	}
	
	private static void drawShield5(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int r = d/3;
		gfx.drawLine(x0+r, y0, x0, y0+d);
		x0+=r;
		gfx.drawLine(x0+r, y0, x0, y0+d);
		x0+=r;
		gfx.drawLine(x0+r, y0, x0, y0+d);
	}
}
