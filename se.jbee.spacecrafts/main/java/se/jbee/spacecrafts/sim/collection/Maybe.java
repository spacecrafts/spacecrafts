package se.jbee.spacecrafts.sim.collection;

import java.util.NoSuchElementException;

public sealed interface Maybe<T> permits Maybe.Nothing, Maybe.Some {

    @SuppressWarnings("unchecked")
    static <T> Maybe<T> nothing() {
        return Nothing.NULL;
    }

    static <T> Maybe<T> some(T e) {
        return e == null ? nothing() : new Some<>(e);
    }

    boolean isPresent();

    T get() throws NoSuchElementException;

    T getOrElse(T value);

    final class Nothing<T> implements Maybe<T> {

        @SuppressWarnings({"rawtypes"})
        static final Nothing NULL = new Nothing();

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T get() throws NoSuchElementException {
            throw new NoSuchElementException();
        }

        @Override
        public T getOrElse(T value) {
            return value;
        }
    }

    final record Some<T>(T e) implements Maybe<T> {

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T get() throws NoSuchElementException {
            return e;
        }

        @Override
        public T getOrElse(T value) {
            return e;
        }
    }
}
