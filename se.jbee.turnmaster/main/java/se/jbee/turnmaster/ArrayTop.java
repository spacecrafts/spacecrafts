package se.jbee.turnmaster;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.copyOfRange;

final class ArrayTop<T> implements Top<T> {

    private final int capacity;
    private Object[] elements;
    private int size;

    ArrayTop(int initialCapacity, int maxCapacity) {
        this(maxCapacity, 0, new Object[initialCapacity]);
    }

    private ArrayTop(int capacity, int size, Object[] elements) {
        this.capacity = capacity;
        this.size = size;
        this.elements = elements;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void forEach(Consumer<? super T> f) {
        for (int i = 0; i < size; i++)
            f.accept(getUnchecked(i));
    }

    @Override
    public Maybe<T> first(Predicate<? super T> test) {
        for (int i = 0; i < size; i++)
            if (test.test(getUnchecked(i))) return Maybe.some(getUnchecked(i));
        return Maybe.nothing();
    }

    @Override
    public int firstIndex(Predicate<T> test) {
        for (int i = 0; i < size; i++)
            if (test.test(getUnchecked(i))) return i;
        return -1;
    }

    @Override
    public void moveToTop(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        if (index <= 1) {
            moveUp(index);
            return;
        }
        var e = elements[index];
        arraycopy(elements, 0, elements, 1, index);
        elements[0] = e;
    }

    @Override
    public void moveToTop(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        Top.super.moveToTop(fromIndex, toIndex);
    }

    @Override
    public void moveToBottom(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        if (index >= size - 2) {
            moveDown(index);
            return;
        }
        var e = elements[index];
        arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[size - 1] = e;
    }

    @Override
    public void moveToBottom(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        Top.super.moveToBottom(fromIndex, toIndex);
    }

    @Override
    public void moveUp(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        if (index > 0) {
            var e = elements[index - 1];
            elements[index - 1] = elements[index];
            elements[index] = e;
        }
    }

    @Override
    public void moveUp(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        Top.super.moveUp(fromIndex, toIndex);
    }

    @Override
    public void moveDown(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        if (index < size - 1) {
            var e = elements[index];
            elements[index] = elements[index + 1];
            elements[index + 1] = e;
        }
    }

    @Override
    public void moveDown(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);
        Top.super.moveDown(fromIndex, toIndex);
    }

    @Override
    public void pushTop(T e) {
        checkNonNull(e);
        size = isUnbound()
               ? size + 1
               : Math.min(capacity, size + 1);
        if (size > elements.length) {
            Object[] tmp = new Object[nextCapacity(4)];
            tmp[0] = e;
            if (elements.length > 0)
                arraycopy(elements, 0, tmp, 1, elements.length);
            elements = tmp;
        } else if (size > 1) {
            arraycopy(elements, 0, elements, 1, size - 1);
        }
        elements[0] = e;
    }

    @Override
    @SafeVarargs
    public final void pushTop(T... items) {
        int len = isUnbound()
                  ? items.length
                  : Math.min(items.length, capacity);
        checkNonNull(items, len);
        int moved = isUnbound()
                    ? size
                    : Math.min(size, capacity - len);
        if (moved + len > elements.length) {
            Object[] tmp = new Object[nextCapacity(len)];
            arraycopy(items, 0, tmp, 0, len);
            if (moved > 0) arraycopy(elements, 0, tmp, len, moved);
            elements = tmp;
            size = len + moved;
        } else {
            if (moved > 0) arraycopy(elements, 0, elements, len, moved);
            arraycopy(items, 0, elements, 0, len);
            size += len;
        }
    }

    @Override
    public void pushBottom(T e) {
        checkNonNull(e);
        if (size == capacity) {
            elements[size - 1] = e;
            return;
        }
        size++;
        if (size > elements.length)
            elements = Arrays.copyOf(elements, nextCapacity(4));
        elements[size - 1] = e;
    }

    @Override
    @SafeVarargs
    public final void pushBottom(T... items) {
        if (items.length == 0) return;
        pushBottom(items, items.length);
    }

    @Override
    public void pushBottom(Collection<T> items) {
        if (items.isEmpty()) return;
        if (items instanceof ArrayTop other) {
            pushBottom(other.elements, items.size());
        } else {
            int overflow = size + items.size() - capacity;
            if (overflow > 0) popBottom(overflow);
            Top.super.pushBottom(items);
        }
    }

    private void pushBottom(Object[] items, int len) {
        checkNonNull(items, len);
        if (size + len > elements.length)
            elements = copyOf(elements, nextCapacity(len));
        int at = Math.min(size, capacity - len);
        arraycopy(items, 0, elements, at, len);
        size = at + len;
    }

    @Override
    public T peek(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return getUnchecked(index);
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        T e = getUnchecked(index);
        if (index == size - 1) {
            size--;
            return e;
        }
        arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return e;
    }

    @Override
    public void remove(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        checkRange(fromIndex, toIndex);
        int len = (toIndex - fromIndex) + 1;
        if (toIndex + 1 == size) {
            size -= len;
            return;
        }
        arraycopy(elements, toIndex + 1, elements, fromIndex,
            size - toIndex - 1);
        size -= len;
    }

    @Override
    public void clear() {
        elements = new Object[Math.min(capacity / 4, 8)];
        size = 0;
    }

    private void checkRange(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        if (toIndex < fromIndex) throw new IllegalArgumentException(
            format("to index must not be smaller than from index: %s >= %s",
                toIndex, fromIndex));
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public Top<T> slice(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        checkRange(fromIndex, toIndex);
        int len = (toIndex - fromIndex) + 1;
        var slice = copyOfRange(elements, fromIndex, toIndex + 1);
        return new ArrayTop<>(capacity, len, slice);
    }

    @SuppressWarnings("unchecked")
    private T getUnchecked(int index) {
        return (T) elements[index];
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    private void checkNonNull(Object e) {
        if (e == null) throw new NullPointerException();
    }

    private void checkNonNull(Object[] items, int len) {
        for (int i = 0; i < len; i++)
            checkNonNull(items[i]);
    }

    private boolean isUnbound() {
        return capacity <= 0;
    }

    private int nextCapacity(int minAdditionalCapacity) {
        return isUnbound()
               ? size + minAdditionalCapacity
               : Math.min(capacity,
                   size + Math.max(minAdditionalCapacity, capacity / 8));
    }
}
