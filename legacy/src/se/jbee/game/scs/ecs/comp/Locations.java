package se.jbee.game.scs.ecs.comp;

import java.util.Iterator;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.scs.ecs.layout.Module;

/**
 * A list of 2D {@link Location}s in the virtual grid of a {@link Module}.
 */
public final class Locations implements ComponentType, Iterable<Location> {

	@Component(0)
	private final byte[] xs;
	@Component(1)
	private final byte[] ys;

	public Locations(byte[] xs, byte[] ys) {
		this.xs = xs;
		this.ys = ys;
	}

	public Location get(int n) {
		return new Location(xs[n], ys[n]);
	}

	public int size() {
		return xs.length;
	}

	public boolean contains(Location l) {
		for (int i = 0; i < xs.length; i++)
			if (xs[i] == l.x && ys[i] == l.y)
				return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('(');
		for (int i = 0; i < xs.length; i++) {
			if (i > 0) b.append(' ');
			b.append(xs[i]).append(':').append(ys[i]);
		}
		b.append(')');
		return b.toString();
	}

	@Override
	public Iterator<Location> iterator() {
		return new Iterator<Location>() {

			int i = 0;

			@Override
			public boolean hasNext() {
				return i < xs.length;
			}

			@Override
			public Location next() {
				return get(i++);
			}
		};
	}
}
