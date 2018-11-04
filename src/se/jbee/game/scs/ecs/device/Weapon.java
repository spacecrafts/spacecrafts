package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.WeaponSystem;

@Entity("weapon")
public final class Weapon extends Device<WeaponSystem, WeaponSystem.Ref> {

	public static final class Ref extends Device.Ref<Weapon> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Weapon> entityType() {
			return Weapon.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, WeaponSystem system) {
		// TODO Auto-generated method stub

	}
}
