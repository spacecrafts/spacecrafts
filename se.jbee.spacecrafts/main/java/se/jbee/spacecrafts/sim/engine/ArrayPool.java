package se.jbee.spacecrafts.sim.engine;

import se.jbee.spacecrafts.sim.Any;
import se.jbee.spacecrafts.sim.state.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.copyOf;

abstract class ArrayPool<T extends Any.Entity> implements Pool<T> {

    private final Class<T> elementType;
    private int size;
    private int span;
    private Object[] elements;
    private int reusableCount;
    private int[] reusableIndexes;

    ArrayPool(Class<T> elementType, int initialCapacity) {
        this.elementType = elementType;
        this.span = -1;
        this.elements = new Object[initialCapacity];
        this.reusableCount = 0;
        this.reusableIndexes = new int[8];
    }

    @Override
    public Class<T> of() {
        return elementType;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int span() {
        return span;
    }

    @Override
    public T get(int serial) throws NoSuchElementException {
        T e = getNullable(serial);
        if (e == null)
            throw new NoSuchElementException(of().getSimpleName() + " with serial: " + serial);
        return e;
    }

    @SuppressWarnings("unchecked")
    private T getNullable(int serial) {
        return (T) elements[serial];
    }

    @Override
    public T add(IntFunction<T> factory) {
        if (reusableCount == 0) {
            if (size >= elements.length) elements = copyOf(elements,
                    elements.length + max(8, min(32, elements.length / 2)));
            var e = factory.apply(size);
            span = Math.max(span, size);
            elements[size++] = e;
            return e;
        }
        int serial = reusableIndexes[--reusableCount];
        var e = factory.apply(serial);
        elements[serial] = e;
        return e;
    }

    @Override
    public void forEach(Consumer<? super T> f) {
        for (int i = 0; i <= span; i++) {
            var e = getNullable(i);
            if (e != null) f.accept(e);
        }
    }

    @Override
    public Maybe<T> first(Predicate<? super T> test) {
        for (int i = 0; i <= span; i++) {
            var e = getNullable(i);
            if (e != null && test.test(e)) return Maybe.some(e);
        }
        return Maybe.nothing();
    }

    public T remove(int serial) throws IllegalStateException {
        var e = getNullable(serial);
        if (e == null)
            throw new IllegalStateException(of().getSimpleName() + " with serial: " + serial);
        elements[serial] = null;
        if (serial == span) {
            while (elements[span] == null) span--;
        } else {
            if (reusableCount == reusableIndexes.length)
                reusableIndexes = copyOf(reusableIndexes,
                        reusableIndexes.length + 8);
            reusableIndexes[reusableCount++] = serial;

        }
        size--;
        return e;
    }
}

class ArrayRegister<T extends Any.Creation> extends ArrayPool<T> implements Register<T> {

    ArrayRegister(Class<T> elementType, int initialCapacity) {
        super(elementType, initialCapacity);
    }
}

class ArrayIndex<T extends Any.Definition> extends ArrayPool<T> implements Index<T> {

    private final Map<Any.Code, T> byCode = new HashMap<>();

    ArrayIndex(Class<T> elementType, int initialCapacity) {
        super(elementType, initialCapacity);
    }

    @Override
    public T get(Any.Code code) {
        var e = byCode.get(code);
        if (e == null)
            throw new NoSuchElementException(of().getSimpleName() + " with code: " + code);
        return e;
    }

    @Override
    public T add(IntFunction<T> factory) {
        var e = super.add(factory);
        byCode.put(e.header().code(), e);
        return e;
    }

}

final class ArrayRange<T extends Any.Quality> extends ArrayIndex<T> implements Range<T> {

    private Object[] byOrdinal;

    ArrayRange(Class<T> elementType, int initialCapacity) {
        super(elementType, initialCapacity);
        this.byOrdinal = new Object[initialCapacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachInOrder(Consumer<? super T> f) {
        for (Object e : byOrdinal)
            if (e != null) f.accept((T) e);
    }

    @Override
    public T add(IntFunction<T> factory) {
        T e = super.add(factory);
        int ordinal = e.ordinal();
        if (ordinal >= byOrdinal.length) byOrdinal = copyOf(byOrdinal,
                max(ordinal + 1, byOrdinal.length) + 8);
        byOrdinal[ordinal] = e;
        return e;
    }

}
