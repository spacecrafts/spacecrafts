package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.system.WeaponSystem;

@EntityType("weapon")
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
	protected void aggregate(State state, WeaponSystem system) {
		// TODO Auto-generated method stub

	}
}
