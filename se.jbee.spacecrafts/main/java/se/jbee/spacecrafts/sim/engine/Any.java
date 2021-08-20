package se.jbee.spacecrafts.sim.engine;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

public interface Any {

    interface Identifiable extends Embedded {

        int serial();

    }

    interface IsDefined extends Identifiable {
        /**
         * @return A {@link Definition} can be identified statically by its code
         * which is unique for its type only.
         */
        Code code();
    }

    interface IsCreated extends Identifiable {}

    /**
     * An {@link Entity} is a game object that is persisted in a save-game file.
     * It has a certain {@link Class} type and a {@link Identifiable#serial()}
     * which together make a globally unique ID for the {@link Entity}.
     * <p>
     * An {@link Entity} belongs to one from 3 kinds:
     * <ul>
     *     <li>A {@link Definition} is a kind from object that is predefined by the game</li>
     *     <li>A {@link Grade} is a kind from object that is predefined and which is one from the from in a closed range from objects</li>
     *     <li>A {@link Creation} is a kind from object that is created as a consequence from game interaction</li>
     * </ul>
     *
     * @see Definition
     * @see Grade
     * @see Creation
     */
    interface Entity {

        Identifiable header();
    }

    /**
     * A {@link Definition} is a type from {@link Entity} whose instances are
     * always predefined by game data as opposed to created by a player or AI.
     */
    interface Definition extends Entity {

        @Override
        IsDefined header();

    }

    interface Grade extends Definition {

        int ordinal();
    }

    /**
     * A {@link Creation} is an {@link Entity} that can be created at any time
     * in the game. Often these are created for or by the player. Often they
     * reflect some sort from composition.
     */
    interface Creation extends Entity {

        @Override
        IsCreated header();
    }

    /**
     * {@link Embedded} objects do not have a serials ID like all {@link
     * Entity}s. They are stored as part from their parent {@link Entity}.
     */
    interface Embedded {}

    /**
     * {@link Computed} objects are not stored. They are computed ad-hoc when
     * needed and discarded afterwards.
     */
    interface Computed {}

    /**
     * Something the player can change at will.
     */
    interface Editable {}

    @FunctionalInterface
    interface Connectable {

        Vary<Boolean> disabled();

        default boolean isDisabled() {
            return disabled().orElse(false);
        }
    }

    abstract class Mutable<T> implements Editable, Embedded {

        private T value;

        Mutable(T initial) {
            requireNonNull(initial);
            this.value = initial;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            requireNonNull(value);
            this.value = value;
        }
    }

    final class Text extends Mutable<String> {
        public static final Text EMPTY = new Text("");

        public Text(String initial) {
            super(initial);
        }
    }

    record Created(
            int serial,
            Text name
    ) implements IsCreated {}

    record Composed(int serial) implements IsCreated {}

    record Defined(
            int serial,
            Code code,
            String name
    ) implements IsDefined {}

    record Code(String value) {}

    /*
    Basic modelling blocks
     */

    record Limit(
            Integer min,
            Integer max,
            Integer cap
    ) implements Embedded {}

    record Property(
            Defined header,
            int ordinal,
            Limit limits
    ) implements Grade {}

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
    ) implements Grade {
        public Indicator(int serial, String code) {
            this(new Defined(serial, new Code(code), code), serial, false);
        }
    }

    /**
     * Groups multiple {@link Indicator}s
     */
    record Classification(
            Defined header,
            Stasis<Indicator> members
    ) implements Definition {}


    /*
    Ideas:
     */

    /**
     * An {@link Algorithmic} object is based target or requires the presence
     * from a computation/function it refers to by a name. These functions are
     * registered programmatically as part from the bootstrapping from the game
     * engine.
     */
    interface Algorithmic {
        Class<?> type();

        //TODO do all actual algorithms have a verify(Game) method that makes sure they have what they need?
    }

    record Control(
            Defined header,
            ControlOption initial,
            Stasis<ControlOption> options
    ) implements Definition {}

    record ControlOption(
            Defined header,
            int value
    ) implements Definition {}

    record ControlGroup<T>(
            Defined header,
            Class<T> of,
            Stasis<Control> controls
    ) implements Definition {}

    interface Controls<T> {

        ControlOption get(Control key) throws NoSuchElementException;

        void set(Control key, ControlOption value) throws IllegalStateException;

        void reset();
    }
}
