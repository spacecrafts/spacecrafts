package se.jbee.turnmaster;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;

final class ArrayQ<T> implements Q<T> {

    static final Pick<?> EMPTY = new ArrayQ<>(0).seal();

    private Object[] elements;
    private int size;
    private boolean sealed = false;

    public ArrayQ(int initialCapacity) {
        this.elements = new Object[initialCapacity];
    }

    private ArrayQ(Object[] elements) {
        this.elements = elements;
        this.size = elements.length;
        this.sealed = true;
    }

    @Override

    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size) throw new IndexOutOfBoundsException();
        return getUnchecked(elements[index]);
    }

    @SuppressWarnings("unchecked")
    private T getUnchecked(Object element) {
        return (T) element;
    }

    @Override
    public int append(T e) {
        checkNotSealed();
        checkNonNull(e);
        ensureCapacity(1);
        elements[size++] = e;
        return size - 1;
    }

    @Override
    @SafeVarargs
    public final Q<T> concat(T... items) throws IllegalStateException, NullPointerException {
        checkNotSealed();
        int len = items.length;
        if (len == 0) return this;
        if (len == 1) {
            append(items[0]);
            return this;
        }
        checkNonNull(items);
        ensureCapacity(len);
        arraycopy(items, 0, elements, size, len);
        size += len;
        return this;
    }

    @Override
    public Q<T> concat(Collection<T> tail) throws IllegalStateException {
        checkNotSealed();
        int len = tail.size();
        ensureCapacity(len);
        if (tail instanceof ArrayQ other) {
            arraycopy(other.elements, 0, elements, this.size, len);
            this.size += len;
        } else {
            tail.forEach(this::append);
        }
        return this;
    }

    @Override
    public Q<T> seal() {
        if (sealed) throw new IllegalStateException("Q already sealed");
        sealed = true;
        return this;
    }

    @Override
    public boolean isSealed() {
        return sealed;
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
    public <B> Pick<B> map(Function<T, B> mapper) {
        Object[] mapped = new Object[size];
        for (int i = 0; i < size; i++)
            mapped[i] = mapper.apply(getUnchecked(i));
        return new ArrayQ<>(mapped);
    }

    @Override
    public Maybe<T> first(Predicate<? super T> test) {
        var index = firstIndex(test);
        return index < 0
               ? Maybe.nothing()
               : Maybe.some(get(index));
    }

    @Override
    public int firstIndex(Predicate<? super T> test) {
        for (int i = 0; i < size; i++)
            if (test.test(get(i))) return i;
        return -1;
    }

    private void checkNotSealed() {
        if (sealed) throw new IllegalStateException("Q is sealed");
    }

    private void checkNonNull(Object e) {
        if (e == null)
            throw new NullPointerException("Q elements must not be null");
    }

    private void checkNonNull(Object[] items) {
        for (Object e : items)
            checkNonNull(e);
    }

    private void ensureCapacity(int minAdditionalCapacity) {
        if (size + minAdditionalCapacity >= elements.length) {
            elements = copyOf(elements,
                size + Math.max(minAdditionalCapacity, Math.min(16, size / 8)));
        }
    }
}
