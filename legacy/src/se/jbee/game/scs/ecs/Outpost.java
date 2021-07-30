package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("outpost")
public final class Outpost extends Frame<Moon.Ref> {

	public static final class Ref extends Frame.Ref<Outpost> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Outpost> entityType() {
			return Outpost.class;
		}
	}

	@Override
	public Kind kind() {
		return Kind.OUTPOST;
	}
}
