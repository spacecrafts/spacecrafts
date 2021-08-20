package se.jbee.spacecrafts.sim.engine;

public interface NumberPer<K extends Any.Entity> extends Collection<NumberPer.Value<K>> {

    int NaN = Integer.MIN_VALUE;

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

    int get(K key);

    void set(K key, int value);

    void add(K key, int delta);

    default void sub(K key, int subtracted) {
        add(key, -subtracted);
    }

    /**
     * Unsets all values in this instance
     */
    NumberPer<K> clear();

    void forEach(Consumer<K> f);

    interface Consumer<K> {
        void accept(K key, int value);
    }

    record Value<K>(
            K key,
            int value
    ) {}

    static boolean isNaN(int value) {
        return value == NaN;
    }

}
