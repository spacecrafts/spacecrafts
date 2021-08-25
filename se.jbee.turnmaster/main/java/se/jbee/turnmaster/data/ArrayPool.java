package se.jbee.turnmaster.data;

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
        if (e == null) throw new NoSuchElementException(
            of().getSimpleName() + " with serial: " + serial);
        return e;
    }

    @SuppressWarnings("unchecked")
    private T getNullable(int serial) {
        return (T) elements[serial];
    }

    @Override
    public T spawn(IntFunction<T> factory) {
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

    public T perish(int serial) throws IllegalStateException {
        var e = getNullable(serial);
        if (e == null) throw new IllegalStateException(
            of().getSimpleName() + " with serial: " + serial);
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
    public Maybe<T> find(Any.Code code) {
        var e = byCode.get(code);
        return e == null
               ? Maybe.nothing()
               : Maybe.some(e);
    }

    @Override
    public T spawn(IntFunction<T> factory) {
        var e = super.spawn(factory);
        byCode.put(e.header().code(), e);
        return e;
    }

}

final class ArrayRange<T extends Any.Grade> extends ArrayIndex<T> implements Range<T> {

    ArrayRange(Class<T> elementType, int initialCapacity) {
        super(elementType, initialCapacity);
    }

    @Override
    public void forEachInOrder(Consumer<? super T> f) {
        forEach(f);
    }

}
