package se.jbee.game.scs.gfx.obj;

import static java.lang.Math.max;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.QuadCurve2D;

import se.jbee.game.any.gfx.ObjClass;
import se.jbee.game.any.gfx.Resources;
import se.jbee.game.scs.gfx.Gfx;

/**
 * Rendering of all the icons used for {@link Gfx#OBJ_ICON}.
 */
public class Icon implements Gfx, ObjClass {

	public static void draw( Graphics2D gfx, int type, int x0, int y0, int d) {
		Stroke s = gfx.getStroke();
		drawIcon( gfx, type, x0, y0, d);
		gfx.setStroke(s);
	}

	private static void drawIcon( Graphics2D gfx, int type, int x0, int y0, int d) {
		switch (type) {
		case ICON_BUILDING          : gfx.fillOval(x0, y0, d, d);	break;
		case ICON_SLOT              :
		case ICON_RESOURCE          : gfx.drawOval(x0, y0, d, d);	break;
		// shields
		case ICON_DAMPING_FIELD     : drawDampingField( gfx, x0, y0, d); break;
		case ICON_FORCE_FIELD       : drawForceField( gfx, x0, y0, d); break;
		case ICON_CLOAKING_FIELD    : drawCloakingField( gfx, x0, y0, d); break;
		case ICON_PARTICLE_FIELD    : drawParticleField( gfx, x0, y0, d); break;
		case ICON_DEFLECTOR_FIELD   : drawDeflectorField( gfx, x0, y0, d); break;
		// TODO antimatter field
		// shield extras
		case ICON_FIELD_CAPACITOR   : drawFieldCapacitor( gfx, x0, y0, d); break;
		case ICON_IMPULSE_GENERATOR : drawImpulseGenerator( gfx, x0, y0, d); break;
		// beams
		case ICON_LASER             : drawLaser( gfx, x0, y0, d); break;
		case ICON_PHASER            : drawPhaser( gfx, x0, y0, d); break;
		case ICON_DISRUPTOR         : drawDisruptor( gfx, x0, y0, d); break;
		// cannons
		case ICON_MASS_GUN          : drawMassGun( gfx, x0, y0, d); break;
		case ICON_PARTICLE_CANNON   : drawParticleCannon( gfx, x0, y0, d); break;
		case ICON_PLASMA_CANNON     : drawPlasmaCannon( gfx, x0, y0, d); break;
		// missiles
		case ICON_ROCKET            : drawRocket( gfx, x0, y0, d); break;
		case ICON_PLASMA_TORPEDO    : drawPlasmaTorpedo( gfx, x0, y0, d); break;
		// weapon extras
		case ICON_TURBO             : drawTurbo( gfx, x0, y0, d); break;
		case ICON_TRACTOR           : drawTractor( gfx, x0, y0, d); break;
		case ICON_STASIS_FIELD      : drawStasisField( gfx, x0, y0, d); break;
		// special
		case ICON_REPAIR_DRONE      : drawRepairDrone( gfx, x0, y0, d); break;
		case ICON_SELF_DESTRUCT     : drawSelfDestruct( gfx, x0, y0, d); break;
		case ICON_TARGET_COMPUTER   : drawTargetComputer(gfx, x0, y0, d); break;
		case ICON_JAMMER            : drawJammer( gfx, x0, y0, d); break;
		// drives
		case ICON_IMPULSE_DRIVE     : drawImpulseDrive( gfx, x0, y0, d); break;
		case ICON_HYPER_DRIVE       : drawHyperDrive( gfx, x0, y0, d); break;
		case ICON_WARP_DRIVE        : drawWarpDrive( gfx, x0, y0, d); break;
		case ICON_ORBITAL_DRIVE     : drawOrbitalDrive( gfx, x0, y0, d); break;
		// reactors
		case ICON_REACTOR1          : drawReactor1( gfx, x0, y0, d); break;
		case ICON_REACTOR2          : drawReactor3( gfx, x0, y0, d); break;
		case ICON_REACTOR3          : drawReactor3( gfx, x0, y0, d); break;
		case ICON_REACTOR4          : drawReactor4( gfx, x0, y0, d); break;
		case ICON_REACTOR5          : drawReactor5( gfx, x0, y0, d); break;
		// corridor
		case ICON_ENERGY            : drawEnergy( gfx, x0, y0, d); break;
		case ICON_ENERGY_OFF        : drawNoEnergy( gfx, x0, y0, d); break;
		}
	}
	
	@Override
	public void draw(Graphics2D gfx, Resources resources, int[] obj) {
		gfx.setColor(resources.color(obj[5]));	
		draw(gfx, obj[1], obj[2], obj[3], obj[4]);		
	}

	private static void drawNoEnergy( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/2;
		int k = r/2;
		int x = x0+r;
		int y = y0+r;
		gfx.drawPolygon(new int[] {x, x+k, x, x-k }, new int[] { y-k,y,y+k, y}, 4);
	}

	private static void drawEnergy( Graphics2D gfx, int x0, int y0, int d) {
		drawNoEnergy( gfx, x0, y0, d);
		int r = d/2;
		int k = r/2;
		int x = x0+r;
		int y = y0+r;
		gfx.fillPolygon(new int[] {x, x+k, x, x-k }, new int[] { y-k,y,y+k, y}, 4);
	}

	private static void drawReactor5( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/2-1;
		int x1 = x0+d-r;
		int y1 = y0+d-r;
		gfx.fillPolygon(new int[]{x0,x0,x0+r}, new int[] {y0,y0+r,y0}, 3);
		gfx.fillPolygon(new int[]{x1,x1+r,x1+r}, new int[] {y0,y0+r,y0}, 3);
		gfx.fillPolygon(new int[]{x0,x0,x0+r}, new int[] {y1,y1+r,y1+r}, 3);
		gfx.fillPolygon(new int[]{x1+r,x1,x1+r}, new int[] {y1+r,y1+r,y1}, 3);
	}

	private static void drawReactor4( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/2-1;
		if (r+r >= d) {
			r--;
		}
		int v = 2;
		gfx.fillRect(x0+v, y0+v, r, r);
		gfx.fillRect(x0+d-r+1, y0+v, r, r);
		gfx.fillRect(x0+v, y0+d-r+1, r, r);
		gfx.fillRect(x0+d-r+1, y0+d-r+1, r, r);
	}

	private static void drawReactor3( Graphics2D gfx, int x0, int y0, int d) {
		int k = d/2;
		int j = k/2;
		int l = k-(k%2)+1;
		gfx.fillOval(x0+k-j, y0+k-j, l,l);
		gfx.setStroke(new BasicStroke(Math.max(1f, d/10f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawPolygon(
				new int[]{x0+k, x0+k+k, x0+k+k, x0+k, x0, x0},
				new int[]{y0, y0+k-j, y0+k+j,y0+k+k,y0+k+j, y0+k-j}, 6);
	}

	private static void drawReactor1( Graphics2D gfx, int x0, int y0, int d) {
		int k = d/3;
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawRoundRect(x0, y0, d, d, 2, 2);
		int l = d-k-k+1;
		gfx.fillRect(x0+k, y0+k, l, l);
	}

	private static void drawLaser( Graphics2D gfx, int x0, int y0, int d) {
		gfx.setStroke(new BasicStroke(d/10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int m = d/2;
		int k = m/3;
		int x = x0+m;
		int y = y0+m;
		gfx.drawLine(x-k, y-k, x, y);
		gfx.drawLine(x+k, y-k, x, y);
		gfx.drawLine(x, y, x, y+k);
	}

	private static void drawPhaser( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawMassGun( Graphics2D gfx, int x0, int y0, int d) {
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m-r/2+1;
		int yc = y0+m-r/2;
		int y = yc-r+r/2;
		gfx.fillOval(xc-r, y, r, r);
		gfx.fillOval(xc+r, y, r, r);
		gfx.fillOval(xc, y+r+r/2, r, r);
	}

	private static void drawRocket( Graphics2D gfx, int x0, int y0, int d) {
		int m = d/2;
		int r = max(1, m/2);
		int xc = x0+m;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
	}

	private static void drawPlasmaTorpedo( Graphics2D gfx, int x0, int y0, int d) {
		int r = max(1, d/4);
		int m = r*2;
		int xc = x0+d/2+1;
		int yc = y0+m;
		gfx.fillPolygon(new int[] {xc-r, xc, xc+r}, new int[] {yc+r, yc-r, yc+r}, 3);
		gfx.fillOval(xc-r, yc, m, m);
	}

	private static void drawParticleCannon( Graphics2D gfx, int x0, int y0, int d) {
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


	private static void drawPlasmaCannon( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawDisruptor( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawRepairDrone( Graphics2D gfx, int x0, int y0, int d) {
		int k = d/4;
		int r = k+k;

		x0+=(d-r-r)/2;
		y0+=(d-r-r)/2;

		gfx.fillOval(x0, y0, k, k);
		gfx.fillOval(x0+r, y0, k, k);
		gfx.fillOval(x0, y0+r, k, k);
		gfx.fillOval(x0+r, y0+r, k, k);
		gfx.fillOval(x0+k-1, y0+k-1, k+2, k+2);
	}

	private static void drawSelfDestruct( Graphics2D gfx, int x0, int y0, int d) {
		int k = d/4;
		int r = 2*k;

		x0+=1+(d-r-r)/2;
		y0+=1+(d-r-r)/2;

		gfx.fillPolygon(new int[] {x0+k, x0+r, x0}, new int[] {y0, y0+r, y0+k}, 3);
		gfx.fillPolygon(new int[] {x0+r+k, x0+r, x0+r+r}, new int[] {y0, y0+r, y0+k}, 3);
		gfx.fillPolygon(new int[] {x0+k, x0+r, x0}, new int[] {y0+r+r, y0+r, y0+k+r}, 3);
		gfx.fillPolygon(new int[] {x0+r+k, x0+r, x0+r+r}, new int[] {y0+r+r, y0+r, y0+k+r}, 3);
	}

	private static void drawJammer( Graphics2D gfx, int x0, int y0, int d) {
		int m = d/6;
		int o = m+m;
		int p = o+o;
		gfx.setStroke(new BasicStroke(d/12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawLine(x0+m, y0+m, x0+o, y0+o);
		gfx.drawLine(x0+p+m, y0+m, x0+p, y0+o);
		gfx.drawLine(x0+o+m, y0, x0+o+m, y0+o);
		gfx.drawLine(x0, y0+o+m, x0+o, y0+o+m);
		gfx.drawLine(x0+p, y0+o+m, x0+p+o, y0+o+m);
		gfx.drawLine(x0+o+m, y0+p, x0+o+m, y0+p+o);
		gfx.drawLine(x0+m, y0+p+m, x0+o, y0+p);
		gfx.drawLine(x0+p, y0+p, x0+p+m, y0+p+m);
	}

	private static void drawTargetComputer(Graphics2D gfx, int x0, int y0, int d) {
		int k = d/4;
		int r = 2*k;

		x0+=(d-r-r)/2;
		y0+=(d-r-r)/2;

		// diamond
		gfx.setStroke(new BasicStroke(d/20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawLine(x0, y0+r, x0+r, y0);
		gfx.drawLine(x0, y0+r, x0+r, y0+r+r);
		gfx.drawLine(x0+r+r, y0+r, x0+r, y0);
		gfx.drawLine(x0+r+r, y0+r, x0+r, y0+r+r);

		// cross
		gfx.setStroke(new BasicStroke(d/12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawLine(x0, y0+r, x0+k, y0+r);
		gfx.drawLine(x0+r+r, y0+r, x0+k+r, y0+r);
		gfx.drawLine(x0+r, y0, x0+r, y0+k);
		gfx.drawLine(x0+r, y0+r+r, x0+r, y0+r+k);
	}

	private static void drawStasisField( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawTractor( Graphics2D gfx, int x0, int y0, int d) {
		gfx.setStroke(new BasicStroke(d/12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int k = d/4;
		d = 4*k;
		int r = 2*k;
		x0-=k/2;
		gfx.drawArc(x0-r, y0-k, d+r, d+r, -30, 60);
		gfx.drawArc(x0-k, y0, d, d, -30, 60);
		gfx.drawArc(x0, y0+k, r, r, -30, 60);

	}

	private static void drawTurbo( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/2;
		int k = r/2;
		k -= k%2;

		int x = x0+r;
		int y = y0+r;

		gfx.drawOval(x-k/2, y-k/2, k, k);
		k+=1;
		gfx.drawOval(x-k, y-k, k+k, k+k);
	}


	private static void drawImpulseDrive( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawHyperDrive( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawWarpDrive( Graphics2D gfx, int x0, int y0, int d) {
		int k = d/4;
		int m = 2*k;
		int x = x0+m;
		int y = y0+m;
		m -= d/8;
		gfx.fillPolygon(new int[] {x-m, x, x-m}, new int[] {y-m, y, y+m}, 3);
		x+=m+1;
		gfx.fillPolygon(new int[] {x-m, x, x-m}, new int[] {y-m, y, y+m}, 3);
	}

	private static void drawOrbitalDrive( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawDampingField( Graphics2D gfx, int x0, int y0, int d) {
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		gfx.drawArc(x0, y0, d, d, 30, 120);
		gfx.drawArc(x0, y0+d/2, d, d, 30, 120);
	}

	private static void drawForceField( Graphics2D gfx, int x0, int y0, int d) {
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int d4 = d/4;
		gfx.drawArc(x0, y0+d4, d, d, 20,140);
		gfx.drawArc(x0+d4, y0+d4+d4, d/2, d/2, 20, 140);
	}

	private static void drawCloakingField( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/2;
		gfx.setStroke(new BasicStroke(d/8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int x = x0;
		gfx.draw(new QuadCurve2D.Float(x, y0+r, x+r/2, y0, x+r, y0+r));
		x += r;
		gfx.draw(new QuadCurve2D.Float(x, y0+r, x+r/2, y0+r+r, x+r, y0+r));
		y0+= r;
		x = x0;
	}

	private static void drawParticleField( Graphics2D gfx, int x0, int y0, int d) {
		int r = d/4;
		x0 += (d-r*3)/2;
		y0 += (d-r*3)/2;
		gfx.drawOval(x0, y0, r, r);
		gfx.drawOval(x0+r+r, y0, r, r);
		gfx.drawOval(x0, y0+r+r, r, r);
		gfx.drawOval(x0+r+r, y0+r+r, r, r);
	}

	private static void drawDeflectorField( Graphics2D gfx, int x0, int y0, int d) {
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

	private static void drawFieldCapacitor( Graphics2D gfx, int x0, int y0, int d) {
		int h = d/4;
		int w = 2*h;
		x0 += (d-w)/2;
		y0 += (d-3*h)/2;
		gfx.fillOval(x0, y0, w, h);
		gfx.fillOval(x0, y0+h, w, h);
		gfx.fillOval(x0, y0+h+h, w, h);
	}

	private static void drawImpulseGenerator( Graphics2D gfx, int x0, int y0, int d) {
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
