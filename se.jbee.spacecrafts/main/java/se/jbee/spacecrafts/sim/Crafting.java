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
            //TODO requirements, e.g. substances or indicators
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
            Top<Section> sections,

            // operating priorities for units (which resources you try to maximise)
            Top<Resource> priorities
    ) implements Creation, Balance {}

    record Section(
            Created header,
            Material structure,
            Maybe<Material> plating,
            Numbers totals,
            Marks properties,
            Pick<Deck> decks,
            Top<Commission> commissions
    ) implements Creation, Balance {}

    record Commission(
            Deck on,
            Unit item,
            boolean dismantle
    ) implements Embedded {}

    record Deck(
            Text name,
            boolean shared,
            XY<Unit> units,
            // in priority order:
            Top<Equipment> equipments
    ) implements Embedded {}

    record Launch(Flux<Section> launched) implements Embedded {}

    /*
     * Dynamic model:
     */

    record Equipment(
            Cluster full,
            Vary<Boolean> disabled,
            Numbers actuals
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
