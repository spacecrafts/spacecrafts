package se.jbee.turnmaster.data;

import java.util.Objects;

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

    interface IsCreated extends Identifiable {

        int inTurn();
    }

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

    record Created(
        int serial,
        Text name,
        int inTurn
    ) implements IsCreated {}

    record Composed(
        int serial,
        int inTurn
    ) implements IsCreated {}

    record Generated(
        int serial,
        Text name,
        int inTurn,
        long seed
    ) implements IsCreated {}

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
        // volatile => whether it disappears or accumulates after a turn
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

    abstract class Mutable<T> implements Editable, Embedded {

        protected T value;

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

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Mutable<?> mutable = (Mutable<?>) obj;
            return Objects.equals(value, mutable.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value == null
                   ? ""
                   : value.toString();
        }

    }

    final class Text extends Mutable<String> implements CharSequence {

        public static final Text EMPTY = new Text("");

        public Text(String initial) {
            super(initial);
        }

        public Text(CharSequence initial) {
            this(initial.toString());
        }

        @Override
        public int length() {
            return value.length();
        }

        @Override
        public char charAt(int index) {
            return value.charAt(index);
        }

        @Override
        public Text subSequence(int start, int end) {
            return new Text(value.subSequence(start, end).toString());
        }
    }
}
