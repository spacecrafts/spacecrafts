package se.jbee.game.scs.ecs.system;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("propulsionsystem")
public class PropulsionSystem extends TacticalSystem {

	public static final class Ref extends TacticalSystem.Ref<PropulsionSystem> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<PropulsionSystem> entityType() {
			return PropulsionSystem.class;
		}
	}

	byte thrust;
}
