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

    record Unit(
            Component of,
            XY.Location at,
            Numbers actuals
    ) implements Embedded {}

    record Material(
            Defined header,
            Numbers properties
    ) implements Definition {}

    record Craft(
            Created header,
            Numbers totals,
            Maybe<Craft> cloneOf,
            Flux<Influence> influences,
            Flux<Deck> decks,

            // configuration
            Top<Resource> priorities

            //TODO have a list of planned launches where some decks become a new ship or orbital?
    ) implements Creation {}

    record Deck(
            Created header,
            Material structure,
            Vary<Material> plating,
            Numbers totals,
            Marks properties,
            XY<Unit> units,
            Top<Unit> construction,
            Top<Equipment> equipments
    ) implements Creation {}
    // indicator-type: MAIN, SUPPORT, CARGO, ORBITAL

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
