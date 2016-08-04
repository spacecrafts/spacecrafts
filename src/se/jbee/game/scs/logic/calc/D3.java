package se.jbee.game.scs.logic.calc;

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

	/**
	 * The point clouds created by this method do fit well to the distribution
	 * of stars as stars do not occur at random. They form in a region. Crowded
	 * and empty areas are realistic in this case.
	 * 
	 * The first n points returned will be fair starting points for player in
	 * one of the clouds.
	 * 
	 * @param n number of points
	 * @param delta minimum distance between points 
	 * @param m number of clouds
	 * @param dia (maximum) diameter of a cloud
	 * 
	 * @param seed random seed
	 */
	public static int[][] pointClouds(int n, int delta, int m, int dia, int[] size, long seed) {
		int pointsInCloud = min(8, max(5, n/3/m));
		n = max(n, pointsInCloud * m * 2);
		dia = max(dia, delta*(pointsInCloud/2));
		int s_x = size[0];
		int s_y = size[1];
		int s_z = size[2];
		int n_col = min(m, s_x/2/dia);
		int n_row = m/n_col;
		if (n_col *n_row < m) {
			n_row++;
		}
		int w = s_x/n_col;
		int h = s_y/n_row;

		LinkedList<Point> cells = cells(n_col, n_row, w, h);

		Rnd rnd = new Rnd(seed);
		List<Shape> blockedAreas = new ArrayList<Shape>();
		int[][] points = new int[n][3];
		// create clusters first
		int p = 0;
		int d = min(min(dia, w),h);
		for (int i = 0; i < m; i++) {
			Point c0 = cells.remove(rnd.nextInt(cells.size()-1));
			int x0 = c0.x+rnd.nextInt(w-d);
			int y0 = c0.y+rnd.nextInt(h-d);
			double min = Double.MAX_VALUE;
			int pMin = p-pointsInCloud;
			int[] center = { x0+d/2, y0+d/2, 0 };
			for (int j = 0; j < pointsInCloud; j++) {
				int x, y = 0;
				do {
					x = x0+rnd.nextInt(d);
					y = y0+rnd.nextInt(d);
				} while (D3.overlaps(x, y, blockedAreas));
				blockedAreas.add(new Ellipse2D.Float(x-delta, y-delta, delta*2, delta*2));
				int z = rnd.nextInt(0, s_z);
				points[p][0] = x;
				points[p][1] = y;
				points[p][2] = z;
				double dist = D3.distance2D(center, points[p]);
				if (dist < min) {
					min = dist;
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
		while (p < n) {
			int x, y = 0;
			do {
				x = rnd.nextInt(s_x);
				y = rnd.nextInt(s_y);
			} while (D3.overlaps(x, y, blockedAreas));
			blockedAreas.add(new Ellipse2D.Float(x-delta, y-delta, delta*2, delta*2));
			int z = rnd.nextInt(s_z);
			points[p][0] = x;
			points[p][1] = y;
			points[p][2] = z;
			p++;
		}
		return points;
	}

	private static LinkedList<Point> cells(int cols, int rows, int w, int h) {
		LinkedList<Point> cells = new LinkedList<>();
		int y_c = 0;
		for (int r = 0; r < rows; r++) {
			int x_c = 0;
			for (int c = 0; c < cols; c++) {
				cells.add(new Point(x_c, y_c));
				x_c+=w;
			}
			y_c+=h;
		}
		return cells;
	}
}
