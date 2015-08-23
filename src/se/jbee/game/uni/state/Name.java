package se.jbee.game.uni.state;


/**
 * names made from predefined parts that are known to work to together and
 * create names of a certain character this narrows down the possible names but
 * is simple and creates a good atmosphere
 */
public class Name {
	
	private static final String[][] GLOTTAL = { 
		{"bal", "tor", "mat", "kit", "lif", "val", "zir", "dor", "di", "ban", "mar", "kir" }, 
		{"ok", "ug","az","ur","za","un","ra","un","om"}, 
		{" tar", " ir", " pe", " gor", " ha"}, 
		{"ma", "k", "zog", "rah", "oh", "dak"} };
	
	private static final String[][] MELODIC = {
		{"lui", "ba", "bi", "ve", "vi", "ci", "ro", "flo", "na", "co", "ha", "tu", "pi"},
		{"ga", "ne", "po", "pa", "pu", "me", "ler", "gi", "do", "te", "no", "ri", "co"},
		{"zia", "la", "li", "ni", "mi", "ma", "na", "no", "mo", "gi", "ti", "di", "ro", "bo"}};
	
	private static final String[][] SUSURRANT = {
		{"si","wa", "la", "fa", "ki", "pa", "hu", "mu", "wo", "li", "ju"},
		{"m", "ss", "mm", "v", "d", "pen", "ch"},
		{"oj", "at", "uh", "oh", "el", "t"},
		{"en", "in", "nef", "el", "es"}
	};
	
	private static final String[][] NASAL = {
		{},
		{}
	};
	
	//TODO use space in parts to create multi word names!
	
	public static String name(int type, long seed) {
		switch (type) {
		case 2 : return name(seed, SUSURRANT, 100, 100, 30,10,0);
		case 1 : return name(seed, MELODIC, 100, 90, 0);
		default: return name(seed, GLOTTAL, 100,50,100,0);
		}
	}

	private static String name(long seed, String[][] parts, int... morePartsChance) {
		Rnd rnd = new Rnd(seed);
		String name = "";
		for (int i = 0; i < parts.length; i++) {
			int partChance = 100 / parts[i].length;
			int j = rnd.nextInt(0, parts[i].length-1);
			while (!rnd.nextChance(partChance)) {
				j++;
			}
			j = j % parts[i].length;
			name += parts[i][j];
			if (!rnd.nextChance(morePartsChance[i])) {
				return name;
			}
		}
		return name;
	}
	
}
