package se.jbee.turnmaster;

import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@link Vary} is a mutable {@link Optional} container where a {@code null}
 * value means "nothing" and any other value {@link #isSome()}.
 * <p>
 * The value can be updated in place using {@link #set(Object)} which might
 * change a some to a nothing or vice versa or just update the present value.
 */
public sealed interface Vary<T> extends Optional<T> permits Vary.Varying {

    static <T> Vary<T> nothing() {
        return new Varying<>(null);
    }

    static <T> Vary<T> some(T value) {
        return new Varying<>(value);
    }

    /**
     * @param value new value, may be {@code null} (considered as nothing)
     * @return the replaced value if one was present
     */
    Maybe<T> set(T value);

    default void clear() {
        set(null);
    }

    final class Varying<T> implements Vary<T> {

        private T value;

        Varying(T value) {
            this.value = value;
        }

        @Override
        public boolean isSome() {
            return value != null;
        }

        @Override
        public boolean is(Predicate<T> test) {
            return value != null && test.test(value);
        }

        @Override
        public T get() {
            return orElseThrow(NoSuchElementException::new);
        }

        @Override
        public T orElse(T value) {
            return isSome()
                   ? this.value
                   : value;
        }

        @Override
        public <E extends RuntimeException> T orElseThrow(Supplier<E> ex) throws E {
            if (!isSome()) throw ex.get();
            return value;
        }

        @Override
        public Maybe<T> set(T value) {
            Maybe<T> old = isSome()
                           ? Maybe.some(value)
                           : Maybe.nothing();
            this.value = value;
            return old;
        }
    }
}
