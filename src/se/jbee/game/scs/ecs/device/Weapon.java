package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.system.WeaponSystem;

@Entity("weapon")
public final class Weapon extends Device<WeaponSystem> {

	public static final class Ref extends Device.Ref<Weapon> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Weapon> entityType() {
			return Weapon.class;
		}
	}
}
