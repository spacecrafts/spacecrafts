package se.jbee.turnmaster.data;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@link Maybe} is an immutable {@link Optional} container.
 * <p>
 * {@link #some(Object)} is always non-null. Any null value is represented by
 * {@link #nothing()}.
 */
public sealed interface Maybe<T> extends Optional<T> permits Maybe.Nothing, Maybe.Some {

    <R> Maybe<R> map(Function<T, R> f);

    @SuppressWarnings("unchecked")
    static <T> Maybe<T> nothing() {
        return Nothing.NULL;
    }

    static <T> Maybe<T> some(T value) {
        if (value == null) throw new NullPointerException();
        return new Some<>(value);
    }

    static <T> Maybe<T> ofThrowing(Supplier<T> get) {
        try {
            return some(get.get());
        } catch (RuntimeException ex) {
            return nothing();
        }
    }

    final class Nothing<T> implements Maybe<T> {

        @SuppressWarnings({"rawtypes"})
        static final Nothing NULL = new Nothing();

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public boolean is(Predicate<T> test) {
            return false;
        }

        @Override
        public T get() throws NoSuchElementException {
            throw new NoSuchElementException();
        }

        @Override
        public T orElse(T value) {
            return value;
        }

        @Override
        public <E extends RuntimeException> T orElseThrow(Supplier<E> ex) throws E {
            throw ex.get();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R> Maybe<R> map(Function<T, R> f) {
            return (Maybe<R>) this;
        }

    }

    final record Some<T>(T get) implements Maybe<T> {

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public boolean is(Predicate<T> test) {
            return test.test(get);
        }

        @Override
        public T orElse(T value) {
            return get;
        }

        @Override
        public <E extends RuntimeException> T orElseThrow(Supplier<E> ex) throws E {
            return get;
        }

        @Override
        public <R> Maybe<R> map(Function<T, R> f) {
            return some(f.apply(get));
        }
    }
}
