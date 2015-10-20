package se.jbee.game.scs.logic;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.jbee.game.any.gfx.Point;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Rnd;

/**
 * Math in 2D and 3D space.
 */
public final class D3 {

	public static double distance(int[] xyz0, int[] xyz1) {
		int dx = xyz1[0] - xyz0[0];
		int dy = xyz1[1] - xyz0[1];
		int dz = xyz1[2] - xyz0[2];
		return sqrt(dx*dx + dy*dy + dz*dz);
	}

	public static double distance2D(int[] xy0, int[] xy1) {
		int dx = xy1[0] - xy0[0];
		int dy = xy1[1] - xy0[1];
		return sqrt(dx*dx + dy*dy);
	}

	public static double closestDistance2D(Entity e, int xyzComp, Entity... entities) {
		final int[] xyz1 = e.list(xyzComp);
		double min = Double.MAX_VALUE;
		for (Entity b : entities) {
			if (e != b) {
				min = Math.min(min, distance2D(xyz1, b.list(xyzComp)));
			}
		}
		return min;
	}

	public static boolean overlaps(int x, int y, List<Shape> shapes) {
		for (Shape s : shapes) {
			if (s.contains(x, y))
				return true;
		}
		return false;
	}

	public static int[][] pointClouds(int n_points, int n_clouds, int s_x, int s_y, int s_z, int s_void, int s_cloud, long seed) {
		int[][] points = new int[n_points][3];
		Rnd rnd = new Rnd(seed);
		List<Shape> blockedAreas = new ArrayList<Shape>();
		int n_pointsInCloud = min(8, max(5, n_points/3/n_clouds));
		s_cloud = max(s_cloud, s_void*(n_pointsInCloud/2));
		int n_col = min(n_clouds, s_x/2/s_cloud);
		int n_row = n_clouds/n_col;
		if (n_col *n_row < n_clouds) {
			n_row++;
		}
		int w = s_x/n_col;
		int h = s_y/n_row;

		LinkedList<Point> cells = new LinkedList<>();
		int y_c = 0;
		for (int r = 0; r < n_row; r++) {
			int x_c = 0;
			for (int c = 0; c < n_col; c++) {
				cells.add(new Point(x_c, y_c));
				x_c+=w;
			}
			y_c+=h;
		}
		// make a list of cells for clouds, than pick one from list at random

		// create clusters first
		int p = 0;
		int s = min(min(s_cloud, w),h);
		for (int i = 0; i < n_clouds; i++) {
			Point c0 = cells.remove(rnd.nextInt(cells.size()-1));
			int x0 = c0.x+rnd.nextInt(w-s);
			int y0 = c0.y+rnd.nextInt(h-s);
			double min = Double.MAX_VALUE;
			int pMin = p-n_pointsInCloud;
			int[] center = { x0+s/2, y0+s/2, 0 };
			for (int j = 0; j < n_pointsInCloud; j++) {
				int x, y = 0;
				do {
					x = x0+rnd.nextInt(s);
					y = y0+rnd.nextInt(s);
				} while (D3.overlaps(x, y, blockedAreas));
				blockedAreas.add(new Ellipse2D.Float(x-s_void, y-s_void, s_void*2, s_void*2));
				int z = rnd.nextInt(0, s_z);
				points[p][0] = x;
				points[p][1] = y;
				points[p][2] = z;
				double d = D3.distance2D(center, points[p]);
				if (d < min) {
					min = d;
					pMin = p;
				}
				p++;
			}
			if (pMin != i) {
				int[] t = points[i];
				points[i] = points[pMin];
				points[pMin] = t;
			}
		}
		while (p < n_points) {
			int x, y = 0;
			do {
				x = rnd.nextInt(s_x);
				y = rnd.nextInt(s_y);
			} while (D3.overlaps(x, y, blockedAreas));
			blockedAreas.add(new Ellipse2D.Float(x-s_void, y-s_void, s_void*2, s_void*2));
			int z = rnd.nextInt(s_z);
			points[p][0] = x;
			points[p][1] = y;
			points[p][2] = z;
			p++;
		}
		return points;
	}
}
