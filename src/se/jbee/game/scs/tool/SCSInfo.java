package se.jbee.game.scs.tool;

import static java.lang.Float.isInfinite;

public class SCSInfo {

	static final int[][] REACTORS = {{9,1}, {7,4}, {6,5}};
	static final int[][] FOOD = {{2,3},{3,3},{4,3}}; 
	static final int[][] WISDOM = {{2,3},{4,3}};
	
	public static void main(String[] args) {
		energyClusters();
		driveClusters();
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
	
	static final class Material {
		final String name;
		final int structure;
		final int weight;
		final int[][] constructionCosts;

		public Material(String name, int structure, int weight,	int[][] constructionCosts) {
			super();
			this.name = name;
			this.structure = structure;
			this.weight = weight;
			this.constructionCosts = constructionCosts;
		}
	}
	
	static void materialCluster(int linearMax, int maxSize, Material... materials) {
		char x = 'A';
		String header = "  n";
		for (int j = 0; j < materials.length; j++) {
			System.out.println(x+": "+materials[j].name);
			header+= String.format(String.format("\t%4s structure", x));
			x+=1;
		}
		System.out.println(header);
		for (int n = 5; n <= maxSize; n+=15) {
			System.out.print(String.format("%4s", n));
			for (int j = 0; j < materials.length; j++) {
				Material material = materials[j];
				int[][] costs = material.constructionCosts;
				int totalCosts = 0;
				if (costs[costs.length-1][1] < 0 && n >= costs[costs.length-1][0]) {
					System.out.print(String.format("\t%4s %4s %3s", "-", "-", "-"));
				} else {
					int c = n;
					for (int b = 0; b < costs.length-1; b++) {
						int from0 = costs[b][0];
						if (from0 <= n) {
							int count = Math.min(c, costs[b+1][0]-from0);
							c -= count;
							totalCosts += count * costs[b][1];
						}
					}
					if (c > 0) {
						totalCosts += c * costs[costs.length-1][1];
					}
					int totalStructure = n*material.structure;
					float sturcturePerCost   = (float)totalStructure / totalCosts;
					System.out.print(String.format("\t%4s %4s %.1f", 
							totalCosts, totalStructure, sturcturePerCost));
				}
			}
			System.out.println();
		}
	}
	
	static void materialClusters() {
		materialCluster(64, 750, 
				new Material("Irozine",     3, 6,  new int[][]{ {1,1}, {20,2}, {50,3}, {90,4}, {140,5}, {200,-1} }),
				new Material("Litalium",    1, 2,  new int[][]{ {1,1}, {25,2}, {45,3}, {60,4}, {70,5}, {75,-1} }),
				new Material("Nicaron",     3, 5,  new int[][]{ {1,2}, {50,3}, {150,4}, {300,5}, {500,6}, {750,8} }),
				new Material("Tritanium",   6, 4,  new int[][]{ {1,4}, {100,5}, {300,6}, {600,7} }),
				new Material("Zirkonite",  12, 8,  new int[][]{ {1,6}, {40,7}, {70,8}, {90,9}, {100,10}, {150,-1} }),
				new Material("Iridiamond", 18, 12, new int[][]{ {1,8}, {80,9}, {150,10}, {210,11}, {260,12}, {300,13}, {330,14}, {350,-1} })
				);
	}

	static void weaponsClusters() {
		componentCluster(32,128,
				new Component("EM-Shield",    3, 8, new int[][]{ {1,5}, {4,6}, {9,7}, {14,8}, {20,9}, {27,10}, {35,11} }),
				new Component("Waves",        2, 7, new int[][]{ {1,4}, {3,5}, {7,6}, {12,7} }),
				new Component("Particles",    3, 9, new int[][]{ {1,5}, {2,6}, {4,7}, {8,8},  {16,9}  }),
				new Component("Mass-Drivers", 4, 4, new int[][]{ {1,6}, {6,7}, {12,8},{18,9}, {24,10} })
				);
	}
	
	static void driveClusters() {
		componentCluster(32, 256, 
				new Component("Impulse Drive", 4, 4, new int[][]{ {1,45},{3,50},{5,55},{10,60},{20,65},{30,70},{40,75},{60,100},{80,120},{100,150},{120,190},{140,240},{160,300},{180,370},{200,450} }),
				new Component("Wrap Drive",    3, 6, new int[][]{ {1,20},{3,30},{5,40},{10,50},{20,55},{30,60},{40,65},{60,70},{80,75},{100,80},{120,85},{140,90},{160,95},{180,100} })
				);
	}
	
	static void jammerCluster() {
		componentCluster(32, 128, 
				new Component("Disturbance Transmitter", 4, 3, new int[][]{ {1,5}, {10,6}, {30,7}, {60,8}, {100,9}, {150,10} })
				);
	}
	
	// 10 weapons in 250 cells => 25 cells per bank
	// 10 computers in 50 cells => 5 cells per bank should give about 30 (so an avg of 6 per cell)
	static void computerCluster() { // 18-62 (6-1000)
		componentCluster(16, 128, 
				new Component("Electronic Computer", 2, 3, new int[][] { {1,5}, {2,3}, {3,4}, {4,5}, {10,6}, {16,7} }),
				new Component("Crystal Computer",    3, 2, new int[][] { {1,6}, {2,4}, {3,5}, {4,6}, {12,7} }),
				new Component("Quantum Computer",    4, 4, new int[][] { {1,7}, {2,5}, {3,6}, {4,7}, {14,8} })
		);
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
				new Component("Ion Cell",     3, 0, new int[][]{ {1,9}, {3,10}}), // most efficient option 1-18
				new Component("Fusion Core",  3, 0, new int[][]{ {1,8}, {5,9}, {8, 10}, {12, 11}, {17, 12}, {23, 13}, {30,14}, {38,15}}), 
				new Component("Crystal Core", 3, 0, new int[][]{ {1,6}, {3,7}, {5,8}, {7,9}, {9,10}, {11,11}, {13,12},{15,13}, {17,14}, {19,15}, {20,16}}), // most efficient option 19-56, but need rare materials
				new Component("Extractor",    3, 0, new int[][]{ {1,7}, {7,8}, {14, 9}, {21, 12}, {28, 15}, {35, 18}, {42,21}}) // most efficient option > 56
				);
	}
	
	static void otherResourcesClusters() {
		componentCluster(32, 1024, 
				new Component("Lab",     3, 3, new int[][]{ {1,2}, {4,3}, {12,4}, {25,5}, {50,6}, {100,7}, {200,8} }),
				new Component("Academy", 3, 2, new int[][]{ {1,2}, {4,3}, {12,4}, {25,5}, {50,6}, {100,7}, {200,8} }),
				new Component("Studio",  2, 0, new int[][]{ {1,5},{4,6},{10,7},{16,8},{24,9},{34,10} }),
				new Component("Mine",    3, 3, new int[][]{ {1,3},{2,4},{3,5},{4,6},{5,7},{6,8} })
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
		float d3 = 6f; // range from 3-6 (very slow to very fast tech)
		float d2 = 2f;
		int prev = 0;
		for (int n = 1; n <= 120; n++) {
			float n2 = ((n/d2)*(n/d2));
			float n3 = ((n/d3)*(n/d3)*(n/d3));
			int tc = (int) (n*20 + Math.floor(n2) + Math.floor(n3));
			System.out.println(String.format("%3s %4s (+%4s)", n, tc, (tc-prev)));
			prev = tc;
		}
	}
}
