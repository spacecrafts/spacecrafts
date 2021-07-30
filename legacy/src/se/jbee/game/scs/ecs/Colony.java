package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.EntityType;

/**
 * A colony on a {@link Moon} is an "outpost". Maybe extract a special class.
 */
@EntityType("colony")
public final class Colony extends Frame<Planet.Ref> {

	public static final class Ref extends Frame.Ref<Colony> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Colony> entityType() {
			return Colony.class;
		}
	}

	@Override
	public Kind kind() {
		return Kind.COLONY;
	}
}
