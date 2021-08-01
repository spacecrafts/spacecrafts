package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Any.*;
import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Numbers;
import se.jbee.spacecrafts.sim.Resourcing.Process;
import se.jbee.spacecrafts.sim.Resourcing.Resource;
import se.jbee.spacecrafts.sim.collection.*;

public interface Crafting {

    /*
     * Static model:
     */

    record Component(
            Defined header,
            Process production,
            Numbers zeros
    ) implements Definition {}

    record Unit(
            Component of,
            Numbers actuals
    ) implements Embedded {}

    record Craft(
            Created header,
            Numbers totals,
            Flux<Influence> influences,
            Flux<Deck> decks,

            // configuration
            Top<Resource> priorities
    ) implements Creation {
        public enum Type {SHIP, OUTPOST, COLONY, ORBIT, STATION}
    }

    record Deck(
            Created header,
            Numbers totals,
            XY<Unit> units,
            Cache<Equipment> equipments
    ) implements Creation {
        public enum Type {MAIN, SUPPORT, CARGO}

    }

    //TODO build queue...

    /*
     * Dynamic model:
     */

    record Equipment(
            Cluster full,
            Controls<Equipment> controls
            // boolean enabled,
            //int priority
    ) implements Computed {}

    record Cluster(
            Component of,
            Q<Cell> members,
            Q<Cluster> addOns
    ) implements Computed {
        public record Cell(
                int x,
                int y,
                int relatives,
                Unit unit
        ) implements Embedded {}
    }

}