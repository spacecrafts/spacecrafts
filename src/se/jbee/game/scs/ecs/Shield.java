package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("shield")
public final class Shield extends Equipment {

	public static final class Ref extends Equipment.Ref<Shield> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Shield> entityType() {
			return Shield.class;
		}
	}

	//TODO add a shield property that controls the radius around the shield generator that the shield covers per shield-strength
	// this way the position and form of the shield is more relevant
}
