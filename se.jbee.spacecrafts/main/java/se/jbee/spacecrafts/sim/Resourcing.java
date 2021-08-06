package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.collection.Q;

import java.util.function.Consumer;

import static se.jbee.spacecrafts.sim.Any.*;

public interface Resourcing {

    record Property(
            Defined header,
            int ordinal,
            Limit limit
    ) implements Quality {}

    record PropertyGroup(
            Defined header,
            int ordinal,
            Q<Property> members
    ) implements Quality {}

    record Tag(
            Defined header,
            boolean hidden
    ) implements Definition {}

    record TagGroup(
            Defined header,
            int ordinal,
            Q<Tag> members,
            boolean multiselect
    ) implements Quality {}

    record Limit(
            Integer min,
            Integer max,
            Integer cap
    ) implements Embedded {}

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

    interface Comparison {

        boolean test(Q<Property> on, Numbers actuals, Numbers limits);

        //TODO add a way so that non-acceptance is explained?
    }

    record Check(
            Q<Property> on,
            Numbers limits,
            Algorithm<Comparison> target
    ) implements Embedded {}

    record Process(
            Q<Check> preconditions,
            Q<Quantity> ins,
            Q<Quantity> outs,
            Q<Effect> shifts
    ) implements Embedded {}

    record Influence(
            Defined header,
            int ordinal,
            Process progression,
            Numbers zeros
    ) implements Quality {}

    record Substance(
            Defined header,
            Q<Resource> deposits,
            Q<Influence> regional,
            Q<Influence> widely
    ) implements Definition {}

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
     * {@link Tags} are like a dynamic enum set. {@link TagGroup}s can be used
     * to for a dynamic enumeration.
     */
    interface Tags {

        boolean has(Tag key);

        void set(Tag key, boolean value);

        void zero(Tags zeros);

        void clear();

        void forEach(Consumer<? super Tag> f);

    }
}
