package se.jbee.game.scs.logic;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import se.jbee.game.uni.state.Entity;

public final class D3 {

	public static double distance(int[] xyz0, int[] xyz1) {
		int dx = xyz1[0] - xyz0[0];
		int dy = xyz1[1] - xyz0[1];
		int dz = xyz1[2] - xyz0[2];
		return sqrt(dx*dx+dy*dy+dz*dz);
	}

	public static int[] distantClusters(int n, int comp, Entity... entities) {
		float[] sums = new float[entities.length];
		for (int i = 0; i < sums.length; i++) {
			float sum = 0f;
			for (int j = 0; j < entities.length; j++) {
				if (i != j) {
					sum += distance(entities[i].list(comp), entities[j].list(comp));
				}
			}
			sums[i] = sum;
		}
		// determine limit
		float total = 0f;
		for (int i = 0; i < sums.length; i++) {
			total+=sums[i];
		}
		float limit = total*2.5f/(entities.length*entities.length*n);
		
		// group
		BitSet clustered = new BitSet(entities.length);
		int p = 0;
		List<BitSet> clusters = new ArrayList<BitSet>();
		while (p < entities.length) {
			if (!clustered.get(p)) {
				clustered.set(p);
				BitSet cluster = new BitSet();
				cluster.set(p);
				for (int i = 0; i < entities.length; i++) {
					if (!clustered.get(i) && distance(entities[p].list(comp), entities[i].list(comp)) < limit) {
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
		
		// pick member of each set that has smallest distance to others
		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			res[i] = mostCentralIn(clusters.get(i), comp, entities);
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
	
	// idea2:
	// calculate average distance over all points
	// starts with a point and mark all that can be reached by traveling not more than half of average distance, this is a group
	// pick any not marked point, again mark, and make group
	// proceed until all point have been added to a group,
	// pick the n groups with most points in them for the n players. 
	// pick the star closest to the others within a group
}
