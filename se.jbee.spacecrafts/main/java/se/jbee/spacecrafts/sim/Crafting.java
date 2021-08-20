package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.Resourcing.Influence;
import se.jbee.spacecrafts.sim.Resourcing.Process;
import se.jbee.spacecrafts.sim.Resourcing.Resource;
import se.jbee.spacecrafts.sim.engine.Any.*;
import se.jbee.spacecrafts.sim.engine.*;

public interface Crafting {

    /*
     * Static model:
     */

    record Component(
            Defined header,
            Process production,
            Numbers zeros
    ) implements Definition {}

    record Material(
            Defined header,
            Numbers properties
    ) implements Definition {}

    record Unit(
            Component of,
            XY.Location at,
            Numbers actuals
    ) implements Embedded {}

    record Craft(
            Created header,
            Numbers totals,
            Maybe<Craft> cloneOf,
            Flux<Influence> influences,

            // in order top to bottom
            Top<Deck> decks,

            // operating priorities for units (which resources you try to maximise)
            Top<Resource> priorities

    ) implements Creation {}

    record Deck(
            Created header,
            Material structure,
            Maybe<Material> plating,
            Numbers totals,
            Marks properties,
            XY<Unit> units,
            // in priority order:
            Top<Unit> construction,
            Top<Equipment> equipments
    ) implements Creation {}
    // indicator-type: MAIN, SUPPORT, CARGO, ORBITAL

    record Launch(Flux<Deck> launched) implements Embedded {}

    /*
     * Dynamic model:
     */

    record Equipment(
            Cluster full,
            Vary<Boolean> disabled
    ) implements Connectable {}

    record Cluster(
            Component of,
            Pick<Cell> members,
            Pick<Cluster> addOns
    ) implements Computed {
        public record Cell(
                Unit of,
                int relatives
        ) implements Embedded {}
    }
}
