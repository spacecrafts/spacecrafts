package se.jbee.spacecrafts.sim.state;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Maybe<A> permits Maybe.Nothing, Maybe.Some {

    @SuppressWarnings("unchecked")
    static <T> Maybe<T> nothing() {
        return Nothing.NULL;
    }

    static <T> Maybe<T> some(T value) {
        return value == null ? nothing() : new Some<>(value);
    }

    boolean isSome();

    A get() throws NoSuchElementException;

    A orElse(A value);

    <E extends RuntimeException> A orElseThrow(Supplier<E> ex) throws E;

    <B> Maybe<B> map(Function<A, B> f);

    final class Nothing<A> implements Maybe<A> {

        @SuppressWarnings({"rawtypes"})
        static final Nothing NULL = new Nothing();

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public A get() throws NoSuchElementException {
            throw new NoSuchElementException();
        }

        @Override
        public A orElse(A value) {
            return value;
        }

        @Override
        public <E extends RuntimeException> A orElseThrow(Supplier<E> ex) throws E {
            throw ex.get();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <B> Maybe<B> map(Function<A, B> f) {
            return (Maybe<B>) this;
        }
    }

    final record Some<A>(A e) implements Maybe<A> {

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public A get() throws NoSuchElementException {
            return e;
        }

        @Override
        public A orElse(A value) {
            return e;
        }

        @Override
        public <E extends RuntimeException> A orElseThrow(Supplier<E> ex) throws E {
            return e;
        }

        @Override
        public <B> Maybe<B> map(Function<A, B> f) {
            return some(f.apply(e));
        }
    }
}
