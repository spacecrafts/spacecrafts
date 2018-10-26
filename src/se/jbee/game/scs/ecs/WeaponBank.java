package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("weaponbank")
public final class WeaponBank extends EquipmentArray<Weapon> {

	public static final class Ref extends EquipmentArray.Ref<WeaponBank> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<WeaponBank> entityType() {
			return WeaponBank.class;
		}
	}
}
