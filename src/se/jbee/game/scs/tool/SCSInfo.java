package se.jbee.game.scs.tool;

public class SCSInfo {

	// pairs of { base value, linkage percent }
	
	static final int[][] REACTORS = {{9,1}, {7,4}, {6,5}};
	static final int[][] FOOD = {{2,3},{3,3},{4,3}}; 
	static final int[][] WISDOM = {{2,3},{4,3}};
	
	public static void main(String[] args) {
		componentBank(3, 50, REACTORS);
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
	static void componentBank(int consumption, int size, int[][] profiles) {
		for (int n = 1; n < size; n++) {
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

	static void technologyCosts() {
		for (int n = 1; n <= 120; n++) {
			int tc = n*20 + ((n/2)*(n/2)) + ((n/3)*(n/3)*(n/3));
			System.out.println(String.format("%3s %4s", n, tc));
		}
	}
}
