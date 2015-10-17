package se.jbee.game.any.gfx;

@FunctionalInterface
public interface Resource<T> {

	T yield(Styles styles);
}
