package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.*;
import se.jbee.spacecrafts.sim.Resourcing.Manifestation;
import se.jbee.spacecrafts.sim.Resourcing.Numbers;
import se.jbee.spacecrafts.sim.Resourcing.Process;
import se.jbee.spacecrafts.sim.Resourcing.Resource;

public interface Crafting {

    /*
     * Static model:
     */

    record Component(
            Defined header,
            Process production,
            Numbers zeros
    ) implements Definition {}


    record Of<T extends Definition>(
            T kind,
            Numbers actuals
    ) implements Embedded {}

    record Craft(
            Created header,
            Numbers totals,
            Z<Of<Manifestation>> x,
            Z<Deck> decks,
            // configuration
            Top<Resource> priorities
    ) implements Creation {
        public enum Type {SHIP, OUTPOST, COLONY, ORBIT, STATION}
    }

    record Deck(
            Created header,
            Deck.Type type,
            XY<Of<Component>> grid,
            Index<Equipment> equipments,
            Index<Equipment.Configuration> configurations
    ) implements Creation {
        public enum Type {MAIN, SUPPORT, CARGO}

    }

    record Coordinate(
            int x,
            int y
    ) implements Embedded {}

    /*
     * Dynamic model:
     */

    record Equipment(
            EID id,
            Cluster cluster
    ) implements Computed, Identifiable {

        /**
         * A {@link Equipment} is a computed object, but we want to keep some
         * player choices about a {@link Equipment} in a persistent way. Those
         * are stored as its {@link Configuration} linked by the {@link EID}.
         */
        record Configuration(
                EID id,
                boolean enabled,
                int priority
        ) implements Embedded, Identifiable {}
    }

    record Cluster(
            Component of,
            int edges,
            Q<Coordinate> members,
            Q<Cluster> addOns
    ) implements Computed {}

    /**
     * Is hashed based on the cell {@link Coordinate}s belonging to the {@link
     * Equipment}. This way it is a unique ID within the {@link Deck} that
     * changes when the {@link Deck} is modified in a way that is relevant.
     */
    record EID(int id) implements Identity {}
}
