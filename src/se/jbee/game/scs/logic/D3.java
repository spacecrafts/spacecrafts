package se.jbee.game.scs.logic;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import se.jbee.game.any.state.Entity;

public final class D3 {

	public static double distance(int[] xyz0, int[] xyz1) {
		int dx = xyz1[0] - xyz0[0];
		int dy = xyz1[1] - xyz0[1];
		if (xyz0.length == 2 && xyz1.length == 2) {
			return sqrt(dx*dx+dy*dy);
		}
		int dz = xyz1[2] - xyz0[2];
		return sqrt(dx*dx+dy*dy+dz*dz);
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
			res[i] = mostCentralIn(clusters.get(i), xyzComp, entities);
		} 
		return res;
	}
	
	static int mostCentralIn(BitSet group, int comp, Entity... entities) {
		int[] total=new int[3];
		int s = 0;
		int k = group.cardinality();
		for (int i = 0; i < k; i++) {
			s = group.nextSetBit(s);
			int[] xyz = entities[s++].list(comp);
			total[0]+=xyz[0];
			total[1]+=xyz[1];
			total[2]+=xyz[2];
		}
		total[0] /= k;
		total[1] /= k;
		total[2] /= k;
		double min = Float.MAX_VALUE;
		int minIndex = 0;
		s=0;
		for (int i = 0; i < k; i++) {
			s = group.nextSetBit(s);
			int[] xyz = entities[s++].list(comp);
			double dist = distance(total, xyz);
			if (dist < min) {
				min = dist;
				minIndex = s-1;
			}
		}
		return minIndex;
	}
	
	/**
	 * This assumes points of radius 1 and all input points are given in terms of their center. 
	 * So a circle has diameter 2. As a consequence the grid of such points has 1|1, 1|3, 1|5, ... 3|1, 3|3, 3|5 ...
	 * 
	 * A circle with center at 1|1 has 
	 * - its N position at 1|0
	 * - its S position at 1|2
	 * - its W position at 0|1
	 * - its E position at 2|1
	 */
	static int[] surroundingPoints(int[] xys, int shape) {
		int[] dx_dys = new int[xys.length];
		for (int p = 0; p < xys.length; p+=2) {
			// where are the other points?
			int[] dirSums = new int[4];
			for (int k = 0; k < xys.length; k+=2) {
				if (p != k) {
					dirSums[dir(xys[p], xys[p+1], xys[k], xys[k+1]).ordinal()]++;
				}
			}
			// what are dx/dy than?
			
		}
		
		
		return xys;
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
}
