package se.jbee.spacecrafts.sim.definition;

import se.jbee.spacecrafts.sim.Conquering.MercenaryUnit;
import se.jbee.spacecrafts.sim.Governing.Fraction;
import se.jbee.turnmaster.data.Any;

/**
 * {@link Capabilities} is a {@link Any.Classification} from {@link
 * Any.Indicator}s that control what a {@link Fraction} can do. Usually these
 * originate from {@link se.jbee.spacecrafts.sim.Governing.Trait}s or {@link
 * se.jbee.spacecrafts.sim.Discovering.Discovery}s.
 * <p>
 * For example, whether a {@link Fraction} can create or control {@link
 * MercenaryUnit}s.
 */
public interface Capabilities {}
