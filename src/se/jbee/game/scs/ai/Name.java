package se.jbee.game.scs.ai;

import static java.lang.Math.max;
import se.jbee.game.uni.gfx.Rnd;

/**
 * names made from predefined parts that are known to work to together and
 * create names of a certain character this narrows down the possible names but
 * is simple and creates a good atmosphere
 */
public class Name {
	
	private static final String[][] GLOTTAL = { {"bal", "tor", "mat", "kit", "mo", "lif", "val", "zir", "dor", "di", "ban" }, {"ok", "afu","az","ur","za","un","ra"}, {"tar", "ir", "pe", "gor", "ha"}, { "ma", "k", "zo", "ah", "oh", "das" } };
	
	public static String name(int type, long seed) {
		switch (type) {
		default: return name(seed, GLOTTAL, 2);
		}
	}

	private static String name(long seed, String[][] parts, int lengthDivisor) {
		Rnd rnd = new Rnd(seed);
		int morePartsChance = 100;
		String name = "";
		for (int i = 0; i < parts.length; i++) {
			int partChance = 100 / parts[i].length;
			int j = rnd.nextInt(0, parts[i].length-1);
			while (!rnd.nextChance(partChance)) {
				j++;
			}
			j = j % parts[i].length;
			name += parts[i][j];
			if (!rnd.nextChance(morePartsChance)) {
				return name;
			}
			morePartsChance = max(morePartsChance/lengthDivisor, 5);
		}
		return name;
	}
	
}
