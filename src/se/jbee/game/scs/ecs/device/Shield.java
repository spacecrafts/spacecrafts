package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.system.DefenceSystem;

@Entity("shield")
public final class Shield extends Device<DefenceSystem> {

	public static final class Ref extends Device.Ref<Shield> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Shield> entityType() {
			return Shield.class;
		}
	}
}
