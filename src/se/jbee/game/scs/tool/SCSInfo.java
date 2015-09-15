package se.jbee.game.scs.tool;

public class SCSInfo {

	public static void main(String[] args) {
		// bn + i/2n (n+1)
		// 2n + 3/2n (n+1)
		// 4n + 2/2n (n+1)
		// 8n + 1/2n (n+1)
		
		int max = 100;
		int use = 2;
		for (int n = 1; n < max; n++) {
			
			int ab = 7*n;
			int ai = ab*1*n/100; // 7 + 1%
			int a = ab+ai;
			int bb = 6*n;
			int bi = bb*3*n/100; // 6 + 3%
			int b = bb+bi;
			int cb = 5*n;
			int ci = cb*5*n/100; // 5 + 5%
			int c = cb+ci;
			float p = use*100*n;
			float ap = p/(a);
			float bp = p/(b);
			float cp = p/(c);
			System.out.println(String.format("%3s | %3s + %3s = %3s %.1f%% | %3s + %3s = %3s %.1f%% | %3s + %3s = %3s %.1f%%", n, ab,ai, a, ap, bb,bi, b, bp ,cb,ci,c, cp ));
		}
	}
}
