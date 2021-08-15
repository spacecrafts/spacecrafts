package se.jbee.spacecrafts.sim.engine;

import java.util.function.Consumer;
import java.util.function.Predicate;

final class DelegateSnapshot<T extends Any.Computed> implements Snapshot<T> {

    private Q<T> of;

    DelegateSnapshot(Q<T> of) {
        this.of = of.isSealed() ? of : of.seal();
    }

    @Override
    public int size() {
        return of.size();
    }

    @Override
    public void forEach(Consumer<? super T> f) {
        of.forEach(f);
    }

    @Override
    public Maybe<T> first(Predicate<? super T> test) {
        return of.first(test);
    }

    @Override
    public void update(Q<T> with) {
        of = with;
    }
}
