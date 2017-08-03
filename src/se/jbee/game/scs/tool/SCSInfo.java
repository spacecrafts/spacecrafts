package se.jbee.game.scs.tool;

import static java.lang.Float.isInfinite;

public class SCSInfo {

	// pairs of { base value, linkage percent }
	
	static final int[][] REACTORS = {{9,1}, {7,4}, {6,5}};
	static final int[][] FOOD = {{2,3},{3,3},{4,3}}; 
	static final int[][] WISDOM = {{2,3},{4,3}};
	
	public static void main(String[] args) {
		//componentBank(3, 1024, FOOD);
		housingClusters();
	}
	
	static final class Component {
		final String name;
		final int constructionCosts;
		final int energyUsage;
		final int[][] outputs;
		
		public Component(String name, int constructionCosts, int energyUsage, int[][] outputs) {
			super();
			this.name = name;
			this.constructionCosts = constructionCosts;
			this.energyUsage = energyUsage;
			this.outputs = outputs;
		}
		
	}

	/**
	 * 
	 * @param consumption
	 *            how much the consumer of the produced thing consumes per
	 *            component. e.g. 3 energy per yard.
	 * @param size
	 *            maximum amount of components to output
	 * @param profiles
	 *            groups of base value and percent-points of increase per bank
	 *            member
	 */
	@Deprecated
	static void componentBank(int consumption, int size, int[][] profiles) {
		for (int n = 1; n < size; n+= (n < 16 ? 1 : n)) {
			System.out.print(String.format("%3s", n));
			float p = consumption*100*n;
			for (int i = 0; i < profiles.length; i++) {
				int base = profiles[i][0] * n;
				int inc = base*profiles[i][1]*n/100;
				int sum = base+inc;
				float ip = p/sum;
				System.out.print(String.format("\t| %3s + %3s = %3s ~ %.1f%% ", base, inc, sum, ip));
			}
			System.out.println();
		}
	}
	
	static void foodClusters() {
		componentCluster(64, 256, 
				new Component("Cybernetics Center", 3, 2, new int[][] {{1,2},{8,3},{16,4}}),
				new Component("Biosphere", 2, 2, new int[][] {{1,2},{6,3},{12,4},{18,5}}),
				new Component("Habitat Dome", 4, 3, new int[][] {{1,3},{4,4},{8,5},{12,6},{16,7},{24,8}}),
				new Component("Hydroponic Farm", 2, 2, new int[][] {{1,4},{5,5},{10,6},{15,7},{20,8}}),
				new Component("Food Forest", 1,0, new int[][] {{1,0},{4,4},{6,5},{8,6},{10,7}})
				);
	}
	
	static void housingClusters() {
		componentCluster(64, 256, 
				new Component("Service Depot", 3, 4, new int[][] {{1,4}}),
				new Component("Regen-bay", 2, 2, new int[][] {{1,3},{10,4}}),
				new Component("Crew's Quarters", 2, 1, new int[][] {{1,1},{4,2},{8,3}}),
				new Component("Living Quarters", 1, 2, new int[][] {{1,3}, {8,4}}),
				new Component("Biosphere", 2, 2, new int[][] {{1,2},{6,3},{12,4},{18,5}}),
				new Component("Habitat Dome", 2, 2, new int[][] {{1,2},{4,3},{8,4},{12,5},{16,6},{24,7}})
				);
	}
	
	static void energyClusters() {
		componentCluster(64, 512,
				new Component("Ion Cell", 3, 0, new int[][]{{1, 9}, {3,10}}), // most efficient option 1-18
				new Component("Fusion Core", 3, 0, new int[][]{{1, 8}, {5, 9}, {8, 10}, {12, 11}, {17, 12}, {23, 13}, {30,14}, {38,15}}), 
				new Component("Crystal Core", 3, 0, new int[][] {{1,6}, {3,7}, {5,8}, {7,9}, {9,10}, {11,11}, {13,12},{15,13}, {17,14}, {19,15}, {20,16}}), // most efficient option 19-56, but need rare materials
				new Component("Extractor", 3, 0, new int[][] {{1,7}, {7,8}, {14, 9}, {21, 12}, {28, 15}, {35, 18}, {42,21}}) // most efficient option > 56
				);
	}
	
	static void componentCluster(int linearMax, int maxCount, Component... comps) {
		char x = 'A';
		String header = "  n";
		for (int j = 0; j < comps.length; j++) {
			System.out.println(x+": "+comps[j].name);
			header+= String.format(String.format("\t%4s avg rel     CP rel   EP rel", x));
			x+=1;
		}
		System.out.println(header);
		for (int n = 1; n <= maxCount; n+=(n < linearMax ? 1 : 16)) {
			System.out.print(String.format("%3s", n));
			for (int j = 0; j < comps.length; j++) {
				int[][] bases = comps[j].outputs;
				int totalOutput = 0;
				int c = n;
				for (int b = 0; b < bases.length-1; b++) {
					int from0 = bases[b][0];
					if (from0 <= n) {
						int count = Math.min(c, bases[b+1][0]-from0);
						c -= count;
						totalOutput += count * bases[b][1];
					}
				}
				if (c > 0) {
					totalOutput += c * bases[bases.length-1][1];
				}
				float avgOutput = (float)totalOutput/n;
				float relOutput = 100f * n/totalOutput;
				int totalCosts = (comps[j].constructionCosts * n);
				float relCosts = (float)totalOutput/totalCosts;
				int totalUsage = (comps[j].energyUsage * n);
				float relUsage = (float)totalOutput/totalUsage;
				if (isInfinite(relOutput)) 
					relOutput = 0f;
				if (isInfinite(relUsage))
					relUsage = 0f;
				if (isInfinite(relCosts))
					relCosts = 0f;
				System.out.print(String.format("\t%4s %.1f %.1f%%  %3s %.1f  %3s %.1f", 
						totalOutput, avgOutput, relOutput , totalCosts, relCosts, totalUsage, relUsage));
			}
			System.out.println();
		}
	}

	static void technologyCosts() {
		for (int n = 1; n <= 120; n++) {
			int tc = n*20 + ((n/2)*(n/2)) + ((n/3)*(n/3)*(n/3));
			System.out.println(String.format("%3s %4s", n, tc));
		}
	}
}
