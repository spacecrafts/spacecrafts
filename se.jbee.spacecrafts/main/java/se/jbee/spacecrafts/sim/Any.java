package se.jbee.spacecrafts.sim;

import se.jbee.spacecrafts.sim.collection.Q;

import java.util.NoSuchElementException;

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
     * It has a type and a {@link Header#serial()} which together make a
     * globally unique ID for the {@link Entity}.
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

    final class Text implements Editable, Embedded {}

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
            Q<ControlOption> options
    ) implements Definition {}

    record ControlOption(
            String label,
            int value
    ) implements Embedded {}

    record ControlGroup<T>(
            Defined header,
            Class<T> of,
            Q<Control> controls
    ) implements Definition {}

    interface Controls<T> {

        ControlOption get(Code key) throws NoSuchElementException;

        ControlOption get(Control key) throws NoSuchElementException;

        void set(Control key, ControlOption value) throws IllegalStateException;

        void reset();
    }
}
