package se.jbee.game.scs.tool;

public class SCSInfo {

	private static final int[][] ENERGY = {{7,1}, {6,3}, {5,5}};
	private static final int[][] FOOD = {{2,3},{3,3},{4,3}}; 
	private static final int[][] WISDOM = {{3,3},{5,3}};
	
	public static void main(String[] args) {
		// bn + i/2n (n+1)
		// 2n + 3/2n (n+1)
		// 4n + 2/2n (n+1)
		// 8n + 1/2n (n+1)
		
		int max = 150;
		int use = 1;
		int[][] profiles = {{2,3}}; 
		for (int n = 1; n < max; n++) {
			System.out.print(String.format("%3s", n));
			float p = use*100*n;
			for (int i = 0; i < profiles.length; i++) {
				int base = profiles[i][0] * n;
				int inc = base*profiles[i][1]*n/100;
				int sum = base+inc;
				float ip = p/sum;
				System.out.print(String.format("\t| %3s + %3s = %3s ~ %.1f%% ", base, inc, sum, ip));
			}
			System.out.println();
		}
		
		for (int n = 1; n <= 120; n++) {
			int tc = n*20 + ((n/2)*(n/2)) + ((n/3)*(n/3)*(n/3));
			System.out.println(String.format("%3s %4s", n, tc));
		}
		// 3*7 = 21	
		// 6*7 = 42
		// 9*7 = 63
		// 
	}
}
