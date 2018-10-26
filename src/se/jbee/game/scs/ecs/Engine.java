package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("engine")
public class Engine extends Equipment {

	public static final class Ref extends Equipment.Ref<Engine> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Engine> entityType() {
			return Engine.class;
		}
	}

	byte thrust;
}
