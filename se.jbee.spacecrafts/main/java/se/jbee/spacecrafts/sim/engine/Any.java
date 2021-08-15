package se.jbee.spacecrafts.sim.engine;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

public interface Any {

    interface Identity {

        int id();
    }

    interface Identifiable {

        Identity id();
    }

    interface Header extends Embedded, Identity {

        int serial();

        @Override
        default int id() {
            return serial();
        }
    }

    interface DefinedHeader extends Header {
        /**
         * @return A {@link Definition} can be identified statically by its code
         * which is unique for its type only.
         */
        Code code();
    }

    interface CreatedHeader extends Header {}

    /**
     * An {@link Entity} is a game object that is persisted in a save-game file.
     * It has a certain {@link Class} type and a {@link Header#serial()} which
     * together make a globally unique ID for the {@link Entity}.
     * <p>
     * An {@link Entity} belongs to one of 3 kinds:
     * <ul>
     *     <li>A {@link Definition} is a kind of object that is predefined by the game</li>
     *     <li>A {@link Quality} is a kind of object that is predefined and which is one of the members in a closed range of objects</li>
     *     <li>A {@link Creation} is a kind of object that is created as a consequence of game interaction</li>
     * </ul>
     *
     * @see Definition
     * @see Quality
     * @see Creation
     */
    interface Entity {

        Header header();
    }

    /**
     * A {@link Definition} is a type of {@link Entity} whose instances are
     * always predefined by game data as opposed to created by a player or AI.
     */
    interface Definition extends Entity {

        @Override
        DefinedHeader header();

    }

    interface Quality extends Definition {

        int ordinal();
    }

    /**
     * A {@link Creation} is an {@link Entity} that can be created at any time
     * in the game. Often these are created for or by the player. Often they
     * reflect some sort of composition.
     */
    interface Creation extends Entity {

        @Override
        CreatedHeader header();
    }

    /**
     * {@link Embedded} objects do not have a serials ID like all {@link
     * Entity}s. They are stored as part of their parent {@link Entity}.
     */
    interface Embedded {}

    /**
     * {@link Computed} objects are not stored. They are computed ad-hoc when
     * needed and discarded afterwards.
     */
    interface Computed {}

    /**
     * An {@link Algorithmic} object is based on or requires the presence of a
     * computation/function it refers to by a name. These functions are
     * registered programmatically as part of the bootstrapping of the game
     * engine.
     */
    interface Algorithmic {
        Class<?> type();

        //TODO do all actual algorithms have a verify(Game) method that makes sure they have what they need?
    }

    /**
     * Something the player can change at will.
     */
    interface Editable {}

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
            Text name,
            Text about
    ) implements CreatedHeader {}

    record Defined(
            int serial,
            Code code,
            String name
    ) implements DefinedHeader {}

    record Code(String value) {}

    record Algorithm<T>(
            Defined header,
            Class<T> type,
            T f
    ) implements Algorithmic, Definition {}


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
    ) implements Quality {
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
}
