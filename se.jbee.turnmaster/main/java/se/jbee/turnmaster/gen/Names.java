package se.jbee.turnmaster.gen;

import se.jbee.turnmaster.RNG;

/**
 * Names made from predefined parts that are known to work to together and
 * create names of a certain character. This narrows down the possible names but
 * is simple and creates a good atmosphere.
 */
public interface Names {

    record Scheme(
        String name,
        Fragment... fragments
    ) {

        public Scheme {
            if (fragments[fragments.length - 1].probability != 0)
                throw new IllegalArgumentException(
                    "Last fragment must have a probability of zero");
        }
    }

    record Fragment(
        int probability,
        String... alternatives
    ) {}

    // Piotr, -skij, -ov, -rov, boris, vladimir, -mir, dimitri
    // names like Latin plant names

    //@formatter:off

    Scheme Baltorian = new Scheme("Baltorian",
        new Fragment(100, "Bal", "Tor", "Mat", "Kit", "Lif", "Val", "Zir", "Dor", "Di", "Ban", "Mar", "Kir"),
        new Fragment(50, "ok", "ug", "az", "ur", "za", "un", "ra", "un", "om"),
        new Fragment(100, " Tar", " Ir", " Pe", " Gor", " Ha"),
        new Fragment(0, "ma", "k", "zog", "rah", "oh", "dak"));

    Scheme Vulkan = new Scheme("Vulkan",
        new Fragment(100, "Li", "A", "U", "Ti", "Ar", "Vo", "O", "Va", "Kla", "Pla", "Ze", "E", "Sa", "Xe", "He", "Bo", "Vu", "Ta", "Ma", "Ru", "Ni", "Ro", "Ve", "Kli", "Re"),
        new Fragment(100, "t", "x", "l", "", "u", "d", "ce", "r", "cu"),
        new Fragment(100, "i", "t", "", "tr", "er", "r", "tim", "c", "mul", "eng", "g", "l"),
        new Fragment(0, "an", "um", "on", "us"));

    Scheme Galaxian = new Scheme("Galaxian",
        new Fragment(100, "as", "be", "cos", "de", "eter", "fu", "ga", "he", "iter", "ka", "lu", "me", "ni", "or", "pro", "que", "re", "si", "tu", "ver", "xe"),
        new Fragment(100, "la", "na", "mo", "nia", "tu", "tio", "mica", "le","turo", "lio"),
        new Fragment(100, " od", " ex", " ed", " mar", "x", " un", "vers", "r", "n", "m", "ys"),
        new Fragment(0, "is", "a", "it", "o", "ia", "ium", "to"));

    Scheme Venezian = new Scheme("Venezian",
        new Fragment(100, "Lui", "Ba", "Bi", "Ve", "Vi", "Ci", "Ro", "Flo", "Na", "Co", "Ha", "Tu", "Pi"),
        new Fragment(90, "ga", "ne", "po", "pa", "pu", "me", "le", "gi", "do", "te", "no", "ri", "co"),
        new Fragment(0, "zia", "lla", "lli", "ni", "mi", "ma", "na", "no", "mo", "gi", "tti", "di", "ro", "bo"));

    Scheme Wadojin = new Scheme("Wadojin",
        new Fragment(100, "Si", "Wa", "La", "Fa", "Ki", "Pa", "Hu", "Mu", "Wo", "Li", "Ju"),
        new Fragment(100, "m", "ss", "mm", "v", "d", "pen", "ch"),
        new Fragment(30, "oj", "at", "uh", "oh", "el", "t"),
        new Fragment(0, "en", "in", "nef", "el", "es"));

    Scheme Flovute = new Scheme("Flovute",
        new Fragment(100, "Flo", "Re", "Pa", "Je", "Ju", "Fe", "An", "Am", "Fu", "Mo", "Dy", "Ce", "Val", "Hu"),
        new Fragment(100, "la", "tu", "mi", "ni", "ri", "ba", "ra", "vu", "le", "go"),
        new Fragment(15, "na", "ge", "te", "con", "cion", "s", "ne", "pice", "pon", "tre"),
        new Fragment(10, "t"),
        new Fragment(0, "es"));

    Scheme Beudonian = new Scheme("Beudonian",
        new Fragment(100, "Ha", "Be", "Ge", "Ma", "Lo", "Sti", "Za", "Sa", "Je", "E"),
        new Fragment(100, "m", "n", "r", "u"),
        new Fragment(50, "b", "g", "d", "ch", "s", "k"),
        new Fragment(25, "e", "in", "en", "on", "er"),
        new Fragment(0, "g", "e", "a", "d", "o", "z", "ia"));

    Scheme Tazadarian = new Scheme("Tazadarian",
        new Fragment(100, "Ta", "Ra", "Za", "Tu", "Di", "Va", "A", "U", "I"),
        new Fragment(100, "za", "r", "du", "n", "si", "lta"),
        new Fragment(100, "d", "m", "s", "am", "v", ""),
        new Fragment(0, "ar", "un", "ir", "is", "az", "at"));

    Scheme Lancelotian = new Scheme("Lancelotian",
        new Fragment(100, "La", "Me", "Ma", "Yu", "Ha", "Ya", "Xa", "Fu", "Xu"),
        new Fragment(100, "nc", "nz", "ik", "k", "t", "n", "r", "", "h"),
        new Fragment(25, "e", "a", "u"),
        new Fragment(10, "lot", "rot", "zu", "ha", "ma", "ta", "gi"),
        new Fragment(0, "e", "se"));

    Scheme Letorian = new Scheme("Letorian",
        new Fragment(100, "A", "Au", "Ly", "Vi", "Le", "Ca", "Er", "U", "Ve", "I", "Plu", "Ge"),
        new Fragment(60, "qua", "to", "si", "da", "tar", "n", "gi", "ca", "la", "gitta", "pri", "mi", "ra", "lan"),
        new Fragment(0, "tis", "tum", "um", "go", "rius", "ux", "pia", "nus", "ni", "rus", "tus", "lux", "ris", "nium", "on"));

    Scheme Hadian = new Scheme("Hadian",
        new Fragment(100, "Ha", "Thu", "Yo", "Ta", "O", "Ne", "Ba", "Ko", "Ro", "Ve", "Ra", "A", "Ke"),
        new Fragment(100, "r", "n", "c", "l", "g", "t", "v", "d"),
        new Fragment(40, "o", "e", "u", "a"),
        new Fragment(0, "s", "k", "r", "bal", "kon", "d", "ros"));

    Scheme Anubitan = new Scheme("Anubitan",
        new Fragment(100, "Am", "Ag", "An", "M", "N", "S", "N", "T", "Th", "Un", "K", "B", "H", "R", "H", "Sch"),
        new Fragment(90, "a", "e", "u", "amu"),
        new Fragment(5, "bet", "haf", "des", "tem", "nut", "ef", "tet", "ta", "sut", "n", "net", "tis", "bis", "m", "pa", "th", "f", "t", "ris", "k", "mut", "sat", "hes", "tep", "tef"),
        new Fragment(0, "-Re", "-Amun", "-anch"));

    Scheme Pellegrian = new Scheme("Pellegrian",
        new Fragment(100, "Co", "Bo", "Pe", "Gu", "Lui", "To", "Da", "Va", "Fo", "Bu"),
        new Fragment(90, "co", "ce", "cu", "", "", ""),
        new Fragment(50, "", "", "", "bbo", "gi", "tti", "da", "po"),
        new Fragment(100, "ll", "lleg", "v", "g", "r"),
        new Fragment(0, "ino", "rino", "io", "ina"));

    // Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune
    // Ceres Pluto Haumea Makemake Eris
    // Orcus Salacia Quaoar Gonggong Sedna
    // Io, Europa, Ganymede, Callisto, Mimas, Enceladus, Tethys, Dione, Rhea
    // Titan Iapetus Miranda Ariel Umbriel Titania Oberon Triton Charon Dysnomia

    Scheme MilkyWay = new Scheme("MilkyWay",
        new Fragment(100, "Plu", "Mar", "Ve", "Ne", "Hel", "Mer", "Ear", "Jupi", "Sa", "Ura", "Cer", "Er", "Or", "Sa", "Gong", "Cal", "Tri", "Ti", "Di", "I", "Hau", "Ma", "Mir", "Ob", "Hyp", "Pho"),
        new Fragment(100, "la", "an", "er", "", "", "", "", "", "", "", "", ""),
        new Fragment(0, "cury", "to", "s", "nus", "ptune", "ios", "kur", "th", "ter", "turn", "es", "is", "cus", "isto", "mas", "ton", "el", "tan", "one", "mede", "pa", "res", "o", "ke", "nix", "yx", "be")
    );

    //@formatter:on

    static String nextName(RNG rng, Scheme scheme) {
        StringBuilder name = new StringBuilder();
        for (Fragment f : scheme.fragments) {
            int j = rng.nextInt(0, f.alternatives.length);
            name.append(f.alternatives[j]);
            if (rng.nextInt(100) > f.probability) {
                return name.toString();
            }
        }
        return name.toString();
    }

}


