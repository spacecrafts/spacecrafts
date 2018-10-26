package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("colony")
public final class Colony extends Platform {

	public static final class Ref extends Platform.Ref<Colony> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Colony> entityType() {
			return Colony.class;
		}
	}

	public Planet._Planet home;
}
