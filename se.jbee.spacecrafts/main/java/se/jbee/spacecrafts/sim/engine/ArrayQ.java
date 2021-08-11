package se.jbee.spacecrafts.sim.engine;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.copyOf;

final class ArrayQ<T> implements Q<T> {

    private Object[] elements;
    private int size;
    private boolean sealed = false;

    public ArrayQ(int initialCapacity) {
        this.elements = new Object[initialCapacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size) throw new IndexOutOfBoundsException();
        return (T) elements[index];
    }

    @Override
    public void append(T e) {
        if (sealed) throw new IllegalStateException("Q is sealed");
        if (e == null)
            throw new NullPointerException("Q elements must not be null");
        if (size >= elements.length)
            elements = copyOf(elements, elements.length + 8);
        elements[size++] = e;
    }

    @Override
    public void seal() {
        if (sealed) throw new IllegalStateException("Q already sealed");
        sealed = true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Consumer<? super T> f) {
        for (int i = 0; i < size; i++)
            f.accept(get(i));
    }

    @Override
    public Maybe<T> first(Predicate<? super T> test) {
        var index = firstIndex(test);
        return index < 0 ? Maybe.nothing() : Maybe.some(get(index));
    }

    @Override
    public int firstIndex(Predicate<? super T> test) {
        for (int i = 0; i < size; i++)
            if (test.test(get(i))) return i;
        return -1;
    }
}
