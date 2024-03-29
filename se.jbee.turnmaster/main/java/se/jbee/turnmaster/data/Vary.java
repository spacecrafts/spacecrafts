package se.jbee.turnmaster.data;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

    default void update(UnaryOperator<T> f) {
        if (isSome()) set(f.apply(get()));
    }

    default void clear() {
        set(null);
    }

    default boolean isEqual(T at) {
        return isSome() && get().equals(at);
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

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Varying<?> varying)) return false;
            return Objects.equals(value, varying.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
