package se.jbee.game.uni.gfx;

@FunctionalInterface
public interface Resource<T> {

	T yield(Styles styles);
}
