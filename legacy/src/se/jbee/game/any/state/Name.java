package se.jbee.game.any.state;

import java.util.HashSet;
import java.util.Set;


/**
 * Names made from predefined parts that are known to work to together and
 * create names of a certain character. This narrows down the possible names but
 * is simple and creates a good atmosphere.
 */
public final class Name {

	public static final int
		NAME_BEUDONIA = 0,
		NAME_VENEZIA =  1,
		NAME_WADOJIN = 2,
		NAME_FLOVUTE = 3,
		NAME_KIRUG_TARK = 4,
		NAME_TAZADAR = 5,
		NAME_LANCELOT = 6,
		NAME_LETO = 7,
		NAME_HADES = 8,
		NAME_ANUBIS = 9,
		NAME_PELLEGRINO = 10,
		NAME_GALAXIA = 11,
		NAME_VULCAN = 12;

	private static final String[][] KIRUG_TARK = {
		{"Bal", "Tor", "Mat", "Kit", "Lif", "Val", "Zir", "Dor", "Di", "Ban", "Mar", "Kir" },
		{"ok", "ug","az","ur","za","un","ra","un","om"},
		{" Tar", " Ir", " Pe", " Gor", " Ha"},
		{"ma", "k", "zog", "rah", "oh", "dak"} };

	private static final String[][] VENEZIA = {
		{"Lui", "Ba", "Bi", "Ve", "Vi", "Ci", "Ro", "Flo", "Na", "Co", "Ha", "Tu", "Pi"},
		{"ga", "ne", "po", "pa", "pu", "me", "le", "gi", "do", "te", "no", "ri", "co"},
		{"zia", "lla", "lli", "ni", "mi", "ma", "na", "no", "mo", "gi", "tti", "di", "ro", "bo"}};

	private static final String[][] WADOJIN = {
		{"Si","Wa", "La", "Fa", "Ki", "Pa", "Hu", "Mu", "Wo", "Li", "Ju"},
		{"m", "ss", "mm", "v", "d", "pen", "ch"},
		{"oj", "at", "uh", "oh", "el", "t"},
		{"en", "in", "nef", "el", "es"}	};

	private static final String[][] FLOVUTE = {
		{"Flo", "Re", "Pa", "Je", "Ju", "Fe", "An", "Am", "Fu", "Mo", "Dy", "Ce", "Val", "Hu"},
		{"la", "tu", "mi", "ni", "ri", "ba", "ra", "vu", "le", "go"},
		{"na","ge", "te", "con", "cion", "s", "ne", "pice", "pon", "tre"},
		{"t"},
		{"es"} };

	private static final String[][] BEUDONIA = {
		{"Ha", "Be", "Ge", "Ma", "Lo", "Sti", "Za", "Sa", "Je", "E"},
		{"m", "n", "r", "u"},
		{"b", "g", "d", "ch", "s", "k"},
		{"e", "in", "en", "on", "er"},
		{"g", "e", "a", "d", "o", "z", "ia"} };

	private static final String[][] TAZADAR = {
		{"Ta", "Ra", "Za", "Tu", "Di", "Va", "A", "U", "I"},
		{"za", "r", "du","n","si", "lta"},
		{"d","m","s","am","v", ""},
		{"ar", "un", "ir", "is","az","at"} };

	private static final String[][] LANCELOT = {
		{"La","Me","Ma","Yu","Ha","Ya","Xa","Fu","Xu"},
		{"nc","nz","ik","k","t","n","r","","h"},
		{"e","a","u"},
		{"lot","rot","zu","ha","ma","ta","gi"},
		{"e","se"}};

	private static final String[][] LETO = {
		{"A","Au","Ly","Vi", "Le", "Ca", "Er", "U", "Ve", "I", "Plu", "Ge"},
		{"qua","to", "si", "da", "tar", "n", "gi", "ca", "la","gitta", "pri", "mi", "ra", "lan"},
		{"tis", "tum", "um", "go", "rius", "ux","pia", "nus", "ni", "rus", "tus", "lux", "ris", "nium", "on"} };

	private static final String[][] HADES = {
		{"Ha", "Thu", "Yo", "Ta", "O", "Ne", "Ba", "Ko", "Ro", "Ve", "Ra", "A", "Ke"},
		{"r", "n", "c", "l", "g", "t", "v", "d"},
		{"o", "e", "u", "a"},
		{"s", "k", "r", "bal", "kon", "d", "ros"} };

	private static final String[][] ANUBIS =  {
		{"Am", "Ag", "An", "M", "N", "S", "N", "T", "Th", "Un", "K", "B", "H", "R", "H", "Sch"},
		{"a", "e", "u", "amu"},
		{"bet","haf", "des", "tem", "nut", "ef", "tet", "ta", "sut", "n", "net", "tis", "bis", "m", "pa", "th", "f", "t", "ris", "k", "mut", "sat", "hes", "tep", "tef"},
		{"-Re", "-Amun", "-anch"}
	};

	private static final String[][] PELLEGRINO = { // Vespa
		{"Coco", "Bo", "Pe", "Gu", "Lui", "To", "Da", "Va", "Fu", "Bu"},
		{"llo", "lle", "lli", "bbo", "gi", "tti", "da", "po"},
		{"lino", "vino", "grino", "cesco", "rio", "gio"}
	};

	private static final String[][] GALAXIA = { 
		{"as", "be", "cos", "de", "eter", "fu", "ga", "he", "iter", "ka", "lu", "me", "ni", "or", "pro", "que", "re", "si", "tu", "ver", "xe"},
		{"la", "na", "mo", "nia", "tu", "tio", "mica", "le", "turo", "lio"},
		{" od", " ex", " ed", " mar", "x", " un", "vers", "r", "n", "m", "ys"},
		{"is", "a", "it", "o", "ia", "ium"}
	};
	
	// Piotr, -skij, -ov, -rov, boris, vladimir, -mir, dimitri
	// names like Latin plant names
	
	private static final String[][] VULCAN = {
		{"Li", "A", "U", "Ti", "Ar", "Vo", "O", "Va", "Kla", "Pla", "Ze", "E", "Sa", "Xe", "He", "Bo", "Vu", "Ta", "Ma", "Ru", "Ni", "Ro", "Ve", "Kli", "Re" },
		{"t", "x", "l", "", "u", "d", "ce", "r", "cu"},
		{"i", "t", "", "tr", "er", "r", "tim", "c", "mul", "eng", "g", "l"},
		{"an", "um", "on", "us"}
	};

	public static String name(int type, long seed) {
		switch (type) {
		case NAME_PELLEGRINO: return name(seed, PELLEGRINO, 90, 50, 25, 0);
		case NAME_ANUBIS    : return name(seed, ANUBIS, 100, 90, 5, 0);
		case NAME_HADES     : return name(seed, HADES, 100,100,40,0);
		case NAME_LETO      : return name(seed, LETO, 100, 60, 40, 20, 0);
		case NAME_LANCELOT  : return name(seed, LANCELOT, 100, 100, 25, 10,0);
		case NAME_TAZADAR   : return name(seed, TAZADAR, 100, 100, 100, 0);
		case NAME_KIRUG_TARK: return name(seed, KIRUG_TARK, 100,50,100,0);
		case NAME_FLOVUTE   : return name(seed, FLOVUTE, 100, 100, 15, 10,0);
		case NAME_WADOJIN   : return name(seed, WADOJIN, 100, 100, 30,10,0);
		case NAME_VENEZIA   : return name(seed, VENEZIA, 100, 90, 0);
		case NAME_GALAXIA   : return name(seed, GALAXIA, 100, 100, 100, 0);
		case NAME_BEUDONIA  : return name(seed, BEUDONIA, 100, 100, 50, 25,0);
		default:
		case NAME_VULCAN	: return name(seed, VULCAN, 100, 100, 100, 100);
		}
	}

	private static String name(long seed, String[][] parts, int... morePartsChance) {
		Rnd rnd = new Rnd(seed);
		StringBuilder name = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			int partChance = 100 / parts[i].length;
			int j = rnd.nextInt(0, parts[i].length-1);
			while (!rnd.nextChance(partChance)) {
				j++;
			}
			j = j % parts[i].length;
			name.append(parts[i][j]);
			if (!rnd.nextChance(morePartsChance[i])) {
				return name.toString();
			}
		}
		return name.toString();
	}

	public static String uniqueIn(State context, int type, long seed) {
		final Set<String> used = new HashSet<>();
		for (int i = 0; i < context.size(); i++) {
			Entity e = context.entity(i);
			String n = e.name();
			if (!n.isEmpty()) {
				used.add(n);
			}
		}
		String name = name(type, seed);
		Rnd rnd = new Rnd(seed);
		while (used.contains(name)) {
			 seed = rnd.nextLong();
			 name = name(type, seed);
		}
		used.add(name);
		return name;
	}
}
