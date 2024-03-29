package se.jbee.turnmaster.data;

public interface NumberPer<K extends Any.Entity> extends ConstantPer<K> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <K extends Any.Entity> NumberPer<K> newDefault(Pool<K> keys) {
        var type = keys.of();
        if (type == Any.Property.class)
            return (NumberPer<K>) Numbers.newDefault((Range) keys);
        if (keys instanceof Index index)
            return new ArrayFixedNumbersPer<>(index);
        if (keys instanceof Register register)
            return new ArrayDynamicNumbersPer<>(register);
        throw new UnsupportedOperationException(
            "Unknown type from Pool: " + keys);
    }

    @FunctionalInterface
    interface Factory {

        <K extends Any.Entity> NumberPer<K> newNumberPer(Pool<K> keys);

        default <K extends Any.Creation> NumberPer<K> newNumberPer(Register<K> keys) {
            return newNumberPer((Pool<K>) keys);
        }

        default <K extends Any.Definition> NumberPer<K> newNumberPer(Index<K> keys) {
            return newNumberPer((Pool<K>) keys);
        }

        default <K extends Any.Grade> NumberPer<K> newNumberPer(Range<K> keys) {
            return newNumberPer((Pool<K>) keys);
        }
    }

    void set(K key, int value);

    void add(K key, int delta);

    default void sub(K key, int subtracted) {
        add(key, -subtracted);
    }

    default int subZero(K key, int subtracted) {
        return subDownTo(key, subtracted, 0);
    }

    default int subDownTo(K key, int subtracted, int min) {
        int val = get(key);
        if (val <= min) return 0;
        int delta = Math.min(subtracted, val);
        sub(key, delta);
        return delta;
    }

    default int addUpTo(K key, int added, int max) {
        int val = get(key);
        if (val >= max) return 0;
        int delta = Math.min(max - val, added);
        add(key, delta);
        return delta;
    }

    /**
     * Unsets all values in this instance
     */
    NumberPer<K> clear();

}
