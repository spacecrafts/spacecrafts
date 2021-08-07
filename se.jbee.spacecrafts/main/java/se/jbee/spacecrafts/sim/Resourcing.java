package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.state.Collection;
import se.jbee.spacecrafts.sim.state.Q;
import se.jbee.spacecrafts.sim.state.Stasis;

import java.util.function.Consumer;

import static se.jbee.spacecrafts.sim.Any.*;

public interface Resourcing {

    record Limit(
            Integer min,
            Integer max,
            Integer cap
    ) implements Embedded {}

    record Property(
            Defined header,
            int ordinal,
            Limit limit
    ) implements Quality {}

    /**
     * Groups multiple {@link Property}s
     */
    record Domain(
            Defined header,
            Stasis<Property> members
    ) implements Definition {}

    record Indicator(
            Defined header,
            int ordinal,
            boolean hidden
    ) implements Quality {}

    /**
     * Groups multiple {@link Indicator}s
     */
    record Classification(
            Defined header,
            Stasis<Indicator> members
    ) implements Definition {}


    /**
     * Elevates a {@link Property} to a physical {@link Resource}.
     * <p>
     * The order is the order in which {@link Resource}s are processed.
     */
    record Resource(
            Defined header,
            int ordinal,
            Property amount,
            Property boost
    ) implements Quality {}

    record Substance(
            Defined header,
            Stasis<Resource> deposits,
            Stasis<Influence> regional,
            Stasis<Influence> widely
    ) implements Definition {}

    record Influence(
            Defined header,
            int ordinal,
            Process progression,
            Numbers zeros
    ) implements Quality {}

    /**
     * Groups multiple {@link Influence}s.
     */
    record Phenomenon(
            Defined header,
            Stasis<Influence> members
    ) implements Definition {}

    record Quantity(
            int n,
            Resource of,
            boolean unconditional
    ) implements Embedded {}

    record Effect(
            int n,
            Property of,
            boolean unconditional
    ) implements Embedded {}

    record Process(
            Q<Check> preconditions,
            Q<Quantity> ins,
            Q<Quantity> outs,
            Q<Effect> shifts
    ) implements Embedded {}

    record Check(
            Stasis<Property> on,
            Numbers limits,
            Algorithm<Comparison> target
    ) implements Embedded {}

    interface Comparison {

        boolean test(Collection<Property> on, Numbers actuals, Numbers limits);

        //TODO add a way so that non-acceptance is explained?
    }

    interface Numbers {
        int get(Property key);

        void set(Property key, int value);

        void add(Property key, int delta);

        void zero(Numbers zeros);

        void add(Numbers added);

        void sub(Numbers subtracted);

        void cap(Numbers at);

        void forEach(NumberConsumer f);

        interface NumberConsumer {
            void accept(Property key, int value);
        }
    }

    /**
     * {@link Marks} are like a dynamic enum set. {@link Classification}s can be
     * used for a dynamic groupings.
     */
    interface Marks {

        boolean has(Indicator key);

        void set(Indicator key, boolean value);

        void zero(Marks zeros);

        void clear();

        void forEach(Consumer<? super Indicator> f);

    }
}
