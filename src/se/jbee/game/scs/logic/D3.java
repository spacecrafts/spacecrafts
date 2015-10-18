package se.jbee.game.scs.logic;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import se.jbee.game.any.state.Entity;

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

	/**
	 * How the algorithm works:
	 * 
	 * - Calculate average distance over all points
	 * - create a cluster and add any starting point not yet clustered
	 * - add all point to the same cluster that can be reached traveling no more than X times the average distance (X is a correction factor)
	 * - repeat steps 2-3 until all points have be assigned to a cluster
	 * - pick the n groups with the most points (for the n results)
	 * - for each of the n groups pick the point that is closest to the center of the group
	 */
	public static int[] distantClusters(int n, int xyzComp, Entity... entities) {
		//TODO just calc the total? sums are not used (or calc the matrix to save CPU)
		float[] sums = new float[entities.length];
		for (int i = 0; i < sums.length; i++) {
			float sum = 0f;
			for (int j = 0; j < entities.length; j++) {
				if (i != j) {
					sum += distance(entities[i].list(xyzComp), entities[j].list(xyzComp));
				}
			}
			sums[i] = sum;
		}
		// determine limit
		float total = 0f;
		for (int i = 0; i < sums.length; i++) {
			total+=sums[i];
		}
		float x = 2.5f;
		float limit = total*x/(entities.length*entities.length*n);
		
		// cluster
		BitSet clustered = new BitSet(entities.length);
		int p = 0;
		List<BitSet> clusters = new ArrayList<BitSet>();
		while (p < entities.length) {
			if (!clustered.get(p)) {
				clustered.set(p);
				BitSet cluster = new BitSet();
				cluster.set(p);
				for (int i = 0; i < entities.length; i++) {
					if (!clustered.get(i) && distance(entities[p].list(xyzComp), entities[i].list(xyzComp)) < limit) {
						cluster.set(i);
					}
				}
				clustered.or(cluster);
				clusters.add(cluster);
			}
			p++;
		}
		
		// find largest n sets
		while (clusters.size() > n) {
			int len = 0;
			for (BitSet s : clusters) {
				len+=s.cardinality();
			}
			len /= clusters.size();
			Iterator<BitSet> i = clusters.iterator();
			while (clusters.size() > n && i.hasNext()) {
				if (i.next().cardinality() <= len) {
					i.remove();
				}
			}
		}
		
		// sort list of sets so largest are first (human players are expected first)
		Collections.sort(clusters, new Comparator<BitSet>() {

			@Override
			public int compare(BitSet o1, BitSet o2) {
				return Integer.signum(o2.cardinality() - o1.cardinality());
			}
		});
		//TODO has a tendency to favor player 1 as the most stars are in the first cluster and so forth. for more fairness a correction might be needed here   
		
		// pick member of each set that has smallest distance to others
		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			res[i] = mostConnectedIn(clusters.get(i), xyzComp, entities);
		} 
		return res;
	}
	
	static int mostConnectedIn(BitSet cluster, int xyzComp, Entity... entities) {
		return cluster.nextSetBit(0); //FIXME real impl.
	}
	
	static Dir dir(int x0, int y0, int x1, int y1) {
		int dx = x1-x0;
		int dy = y1-y0;
		int adx = abs(dx);
		int ady = abs(dy);
		return adx > ady
			? dy > 0 ? Dir.N : Dir.S
			: dx > 0 ? Dir.W : Dir.E;
	}
	
	static enum Dir { N,S,E,W }

	public static boolean overlaps(int x, int y, List<Shape> shapes) {
		for (Shape s : shapes) {
			if (s.contains(x, y))
				return true;
		}
		return false;
	}
}
