package se.jbee.spacecrafts.sim;

import se.jbee.turnmaster.data.Any;
import se.jbee.turnmaster.data.Any.Code;

public interface Domains {

    /**
     * Properties that need to be provided during a construction process. These
     * are {@link se.jbee.spacecrafts.sim.Resourcing.Resource}s but also other
     * {@link se.jbee.turnmaster.data.Any.Property}s.
     */
    Code construction = code("construction");

    /**
     * Properties that are used when operating a component that is completed
     * (build). This e.g. does not include {@link se.jbee.spacecrafts.sim.Resourcing.Resource}s.
     */
    Code operation = code("operation");

    /**
     * Properties that are liable to taxation. Meaning the taxation percentage
     * of the output is turned into money.
     */
    Code taxed = code("taxed");

    /**
     * Set of properties aggregated from {@link se.jbee.spacecrafts.sim.Crafting.Section}
     * to {@link se.jbee.spacecrafts.sim.Crafting.Craft}.
     */
    Code craft = code("craft");

    /**
     * Set of properties aggregated from {@link se.jbee.spacecrafts.sim.Crafting.Craft}
     * to {@link se.jbee.spacecrafts.sim.Governing.Fraction}.
     */
    Code fraction = code("fraction");

    static Code code(String name) {
        return new Code(Any.Domain.class, name);
    }
}
