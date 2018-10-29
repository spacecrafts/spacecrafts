package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.system.PropulsionSystem;

@Entity("engine")
public final class Engine extends Device<PropulsionSystem> {

	public static final class Ref extends Device.Ref<Engine> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Engine> entityType() {
			return Engine.class;
		}
	}

	@NonNegative
	public int totalThrust;
}
