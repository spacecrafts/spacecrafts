package se.jbee.game.uni.state;


/**
 * names made from predefined parts that are known to work to together and
 * create names of a certain character this narrows down the possible names but
 * is simple and creates a good atmosphere
 */
public final class Name {
	
	private static final String[][] KIRUG_TARK = { 
		{"Bal", "Tor", "Mat", "Kit", "Lif", "Val", "Zir", "Dor", "Di", "Ban", "Mar", "Kir" }, 
		{"ok", "ug","az","ur","za","un","ra","un","om"}, 
		{" Tar", " Ir", " Pe", " Gor", " Ha"}, 
		{"ma", "k", "zog", "rah", "oh", "dak"} };
	
	private static final String[][] VENEZIA = {
		{"Lui", "Ba", "Bi", "Ve", "Vi", "Ci", "Ro", "Flo", "Na", "Co", "Ha", "Tu", "Pi"},
		{"ga", "ne", "po", "pa", "pu", "me", "ler", "gi", "do", "te", "no", "ri", "co"},
		{"zia", "la", "li", "ni", "mi", "ma", "na", "no", "mo", "gi", "ti", "di", "ro", "bo"}};
	
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
	
	private static final String[][] HARES = {
		{"Ha", "Thu", "Yo", "Ta", "O", "Ne", "Ba", "Ko", "Ro", "Ve", "Ra", "A"},
		{"r", "n", "c", "l", "g", "t", "v"},
		{"o", "e", "u", "a"},
		{"kon","k", "r", "s", "ban", "r", "d"} };
	
	public static final String[][] RA =  { 
		{"Am", "Ag", "An", "M", "N", "S", "N", "T", "Th", "Un", "K", "B", "H", "R", "H", "Sch"},
		{"a", "e", "u", "amu"},
		{"bet","haf", "des", "tem", "nut", "ef", "tet", "ta", "sut", "n", "net", "tis", "bis", "m", "pa", "th", "f", "t", "ris", "k", "mut", "sat", "hes", "tep", "tef"},
		{"-Re", "-Amun", "-anch"}
	};
	
	private static final String[][] AMANDA = { // Vespa, Piagio
		{"Coco", "Bo", "Pe", "Gu", "Lui", "To", "Da", "Va", "Fu", "Bu"},
		{"llo", "lle", "lli", "bbo", "gi", "tti", "da", "po"},
		{"lino", "vino", "grino", "cesco", "rio", "gio"},
		{""}
	};
	
	// Piotr, -skij, -ov, -rov, boris, vladimir, -mir, dimitri
	
	public static String name(int type, long seed) {
		switch (type) {
		case 10 : return name(seed, AMANDA, 90, 50, 25, 0);
		case 9 : return name(seed, RA, 100, 90, 5, 0);
		case 8 : return name(seed, HARES, 100,100,25,0);
		case 7 : return name(seed, LETO, 100, 60, 40, 20, 0);
		case 6 : return name(seed, LANCELOT, 100, 100, 25, 10,0);
		case 5 : return name(seed, TAZADAR, 100, 100, 100, 0);
		case 4 : return name(seed, KIRUG_TARK, 100,50,100,0);
		case 3 : return name(seed, FLOVUTE, 100, 100, 15, 10,0);
		case 2 : return name(seed, WADOJIN, 100, 100, 30,10,0);
		case 1 : return name(seed, VENEZIA, 100, 90, 0);
		default:
		case 0 : return name(seed, BEUDONIA, 100, 100, 50, 25,0);
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
