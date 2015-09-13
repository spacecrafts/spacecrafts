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
		// shields
		case ICON_DAMPING_FIELD: drawDampingField(styles, gfx, x0, y0, d); break;
		case ICON_FORCE_FIELD: drawForceField(styles, gfx, x0, y0, d); break;
		case ICON_CLOAKING_FIELD: drawCloakingField(styles, gfx, x0, y0, d); break;
		case ICON_PARTICLE_FIELD: drawParticleField(styles, gfx, x0, y0, d); break;
		case ICON_DEFLECTOR_FIELD: drawDeflectorField(styles, gfx, x0, y0, d); break;
		// TODO antimatter field
		// shield extras
		case ICON_FIELD_CAPACITOR : drawFieldCapacitor(styles, gfx, x0, y0, d); break;
		case ICON_IMPULSE_GENERATOR : drawImpulseGenerator(styles, gfx, x0, y0, d); break;
		// beams
		case ICON_LASER   : drawLaser(styles, gfx, x0, y0, d); break;
		case ICON_PHASER   : drawPhaser(styles, gfx, x0, y0, d); break;
		case ICON_DISRUPTOR  : drawDisruptor(styles, gfx, x0, y0, d); break;
		// cannons
		case ICON_MASS_GUN  : drawMassGun(styles, gfx, x0, y0, d); break;
		case ICON_PARTICLE_CANNON: drawParticleCannon(styles, gfx, x0, y0, d); break;
		case ICON_PLASMA_CANNON : drawPlasmaCannon(styles, gfx, x0, y0, d); break;
		// missiles 
		case ICON_ROCKET  : drawRocket(styles, gfx, x0, y0, d); break;
		case ICON_PLASMA_TORPEDO  : drawPlasmaTorpedo(styles, gfx, x0, y0, d); break;
		// weapon extras
		case ICON_TURBO  : drawTurbo(styles, gfx, x0, y0, d); break;
		case ICON_TRACTOR : drawTractor(styles, gfx, x0, y0, d); break;
		case ICON_STASIS_FIELD : drawStasisField(styles, gfx, x0, y0, d); break;
		case ICON_SELF_DESTRUCT: drawSelfDestruct(styles, gfx, x0, y0, d); break;
		// drives
		case ICON_IMPULSE_DRIVE : drawImpulseDrive(styles, gfx, x0, y0, d); break;
		case ICON_HYPER_DRIVE : drawHyperDrive(styles, gfx, x0, y0, d); break;
		case ICON_WARP_DRIVE : drawWarpDrive(styles, gfx, x0, y0, d); break;
		case ICON_ORBITAL_DRIVE : drawOrbitalDrive(styles, gfx, x0, y0, d); break;
		// reactors
		case ICON_REACTOR1 : drawReactor1(styles, gfx, x0, y0, d); break;
		case ICON_REACTOR2 : drawReactor2(styles, gfx, x0, y0, d); break;
		case ICON_REACTOR3 : drawReactor3(styles, gfx, x0, y0, d); break;
		// corridor
		case ICON_ENERGY   : drawEnergy(styles, gfx, x0, y0, d); break;
		case ICON_NO_ENERGY: drawNoEnergy(styles, gfx, x0, y0, d); break;
		}
	}

	private static void drawNoEnergy(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_ENERGY));
		int r = d/2;
		int k = r/2;
		int x = x0+r;
		int y = y0+r;
		gfx.drawPolygon(new int[] {x, x+k, x, x-k }, new int[] { y-k,y,y+k, y}, 4);
	}

	private static void drawEnergy(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		drawNoEnergy(styles, gfx, x0, y0, d);
		int r = d/2;
		int k = r/2;
		int x = x0+r;
		int y = y0+r;
		gfx.fillPolygon(new int[] {x, x+k, x, x-k }, new int[] { y-k,y,y+k, y}, 4);
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
		
		gfx.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		
		gfx.drawLine(x0, y0+k, x0+k+k+k, y0+k+k);
		gfx.drawLine(x0, y0+k+k, x0+k+k+k, y0+k);
		gfx.drawLine(x0+k, y0, x0+k+k, y0+k+k+k);
		gfx.drawLine(x0+k+k, y0, x0+k, y0+k+k+k);
		
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawPolygon(
				new int[]{x0+k, x0+k+k, x0+k+k+k,x0+k+k+k,x0+k+k,x0+k,x0,x0}, 
				new int[]{y0,y0,y0+k,y0+k+k,y0+k+k+k,y0+k+k+k,y0+k+k,y0+k}, 8);

	}

	private static void drawReactor1(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_ENERGY));
		int k = d/3;
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawRoundRect(x0, y0, d, d, 2, 2);
		int l = d-k-k+1;
		gfx.fillRect(x0+k, y0+k, l, l);
	}

	private static void drawLaser(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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
	
	private static void drawPhaser(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		gfx.setStroke(new BasicStroke(d/20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		int m = d/2;
		int k = m/3;
		int x = x0+m;
		int y = y0+m;

		gfx.drawLine(x-k, y-k, x+k, y+k);
		gfx.drawLine(x+k, y-k, x-k, y+k);
		gfx.drawLine(x, y-k, x, y+k);
		gfx.drawLine(x-k, y, x+k, y);
	}

	private static void drawMassGun(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m-r/2+1;
		int yc = y0+m-r/2;
		int y = yc-r+r/2;
		gfx.fillOval(xc-r, y, r, r);
		gfx.fillOval(xc+r, y, r, r);
		gfx.fillOval(xc, y+r+r/2, r, r);
	}

	private static void drawRocket(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
	}

	private static void drawPlasmaTorpedo(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int r = max(1, d/4);
		int m = r*2;
		int xc = x0+d/2+1;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
		gfx.fillOval(xc-r, yc, m, m);
	}
	
	private static void drawParticleCannon(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		gfx.setStroke(new BasicStroke(d/12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int k = d/5;
		int j = 2*k;
		
		x0+=(d-j-j)/2;
		y0+=(d-j-j)/2;
		
		gfx.drawLine(x0, y0, x0+k, y0+k);
		gfx.drawLine(x0+j+j, y0, x0+j+k, y0+k);
		gfx.drawLine(x0+j, y0+j+k, x0+j, y0+j+j);
		gfx.fillOval(x0+k+1, y0+k+1, j-2, j-2);
	}
	
	
	private static void drawPlasmaCannon(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m-r/2+1;
		int yc = y0+m-r/2;
		int y = yc-r+r/2;
		
		gfx.fillOval(xc-r, y, r, r);
		gfx.fillOval(xc+r, y, r, r);
		
		gfx.fillPolygon(new int[] {xc-r/2, xc-r, xc}, new int[] {yc+r+r, yc,yc}, 3);
		gfx.fillPolygon(new int[] {xc-r/2+r+r, xc+r, xc+r+r}, new int[] {yc+r+r, yc,yc}, 3);
	}
	
	private static void drawDisruptor(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int k = d/4;
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
	
	private static void drawSelfDestruct(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		
		int k = d/4;
		int r = 2*k;
		
		x0+=1+(d-r-r)/2;
		y0+=1+(d-r-r)/2;
		
		gfx.fillPolygon(new int[] {x0+k, x0+r, x0}, new int[] {y0, y0+r, y0+k}, 3);
		gfx.fillPolygon(new int[] {x0+r+k, x0+r, x0+r+r}, new int[] {y0, y0+r, y0+k}, 3);
		gfx.fillPolygon(new int[] {x0+k, x0+r, x0}, new int[] {y0+r+r, y0+r, y0+k+r}, 3);
		gfx.fillPolygon(new int[] {x0+r+k, x0+r, x0+r+r}, new int[] {y0+r+r, y0+r, y0+k+r}, 3);
	}
	
	
	private static void drawStasisField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		gfx.setStroke(new BasicStroke(d/16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int j = d/8;
		int k = 2*j;
		int r = 2*k;
		
		x0+=(d-3*k)/2;
		y0+=(d-3*k)/2;
		
		gfx.drawLine(x0, y0+k, x0+k, y0);
		gfx.drawLine(x0+r+k, y0+k, x0+k+k, y0);
		gfx.drawLine(x0+k+k, y0+r+k, x0+r+k, y0+r);
		gfx.drawLine(x0, y0+r, x0+k, y0+r+k);
		
		gfx.drawLine(x0+k, y0+k, x0+j, y0+j);
		gfx.drawLine(x0+r, y0+k, x0+r+j, y0+j);
		gfx.drawLine(x0+k, y0+r, x0+j, y0+j+r);
		gfx.drawLine(x0+r, y0+r, x0+r+j, y0+j+r);
	}
	
	private static void drawTractor(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		gfx.setStroke(new BasicStroke(d/12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int k = d/4;
		d = 4*k;
		int r = 2*k;
		x0-=k/2;
		gfx.drawArc(x0-r, y0-k, d+r, d+r, -30, 60);
		gfx.drawArc(x0-k, y0, d, d, -30, 60);
		gfx.drawArc(x0, y0+k, r, r, -30, 60);
		
	}
	
	private static void drawTurbo(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_WEAPON));
		int r = d/2;
		int k = r/2;
		k -= k%2;

		int x = x0+r;
		int y = y0+r;
		
		gfx.drawOval(x-k/2, y-k/2, k, k);
		k+=1;
		gfx.drawOval(x-k, y-k, k+k, k+k);
	}


	private static void drawImpulseDrive(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawHyperDrive(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawWarpDrive(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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
	
	private static void drawOrbitalDrive(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawDampingField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawArc(x0, y0, d, d, 30, 120);
		gfx.drawArc(x0, y0+d/2, d, d, 30, 120);
	}

	private static void drawForceField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int d4 = d/4;
		gfx.drawArc(x0, y0+d4, d, d, 20,140);
		gfx.drawArc(x0+d4, y0+d4+d4, d/2, d/2, 20, 140);
	}

	private static void drawCloakingField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
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
	
	private static void drawParticleField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		int r = d/4;
		x0 += (d-r*3)/2;
		y0 += (d-r*3)/2;
		gfx.drawOval(x0, y0, r, r);
		gfx.drawOval(x0+r+r, y0, r, r);
		gfx.drawOval(x0, y0+r+r, r, r);
		gfx.drawOval(x0+r+r, y0+r+r, r, r);
	}
	
	private static void drawDeflectorField(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int r = d/3;
		int o = d/8;
		y0+=o;
		d-=o+o;
		gfx.drawLine(x0+r, y0, x0, y0+d);
		x0+=r;
		gfx.drawLine(x0+r, y0, x0, y0+d);
		x0+=r;
		gfx.drawLine(x0+r, y0, x0, y0+d);
	}
	
	private static void drawFieldCapacitor(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		int h = d/4;
		int w = 2*h;
		x0 += (d-w)/2;
		y0 += (d-3*h)/2;
		gfx.fillOval(x0, y0, w, h);
		gfx.fillOval(x0, y0+h, w, h);
		gfx.fillOval(x0, y0+h+h, w, h);
	}
	
	private static void drawImpulseGenerator(Styles styles, Graphics2D gfx, int x0, int y0, int d) {
		gfx.setColor(styles.color(COLOR_SHIELD));
		int w = d/4;
		if ((w & 1) == 0 && w > 2) {
			w--;
		}
		y0 += (d-2*w)/2;
		x0 += 1+(d-3*w)/2;
		gfx.fillPolygon(new int[] {x0+w/2, x0, x0+w}, new int[] {y0, y0+w+w, y0+w+w}, 3);
		x0 += w;
		gfx.fillPolygon(new int[] {x0, x0+w, x0+w/2}, new int[] {y0, y0, y0+w+w}, 3);
		x0 += w;
		gfx.fillPolygon(new int[] {x0+w/2, x0, x0+w}, new int[] {y0, y0+w+w, y0+w+w}, 3);
	}
	
	
}
