package test.integration.turnmaster;

import org.junit.jupiter.api.Test;
import se.jbee.turnmaster.RNG;
import se.jbee.turnmaster.gen.Names;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNames {

    @Test
    void baltorian() {
        assertSufficientlyRandom(Names.Baltorian);
    }

    @Test
    void vulkan() {
        assertSufficientlyRandom(Names.Vulkan);
    }

    @Test
    void galaxian() {
        assertSufficientlyRandom(Names.Galaxian);
    }

    @Test
    void venezian() {
        assertSufficientlyRandom(Names.Venezian);
    }

    @Test
    void wadojin() {
        assertSufficientlyRandom(Names.Wadojin);
    }

    @Test
    void flovute() {
        assertSufficientlyRandom(Names.Flovute);
    }

    @Test
    void beudonian() {
        assertSufficientlyRandom(Names.Beudonian);
    }

    @Test
    void tazadarian() {
        assertSufficientlyRandom(Names.Tazadarian);
    }

    @Test
    void lancelotian() {
        assertSufficientlyRandom(Names.Lancelotian);
    }

    @Test
    void letorian() {
        assertSufficientlyRandom(Names.Letorian);
    }

    @Test
    void hadian() {
        assertSufficientlyRandom(Names.Hadian);
    }

    @Test
    void anubitan() {
        assertSufficientlyRandom(Names.Anubitan);
    }

    @Test
    void pellegrian() {
        assertSufficientlyRandom(Names.Pellegrian);
    }

    @Test
    void milkyWay() {
        assertSufficientlyRandom(Names.MilkyWay);
    }

    private void assertSufficientlyRandom(Names.Scheme scheme) {
        Set<String> names = new LinkedHashSet<>();
        RNG rnd = new RNG();
        int n = 1000;
        for (int i = 0; i < n; i++)
            names.add(Names.nextName(rnd, scheme));
        assertTrue(names.size() * 8 > n);
    }
}
