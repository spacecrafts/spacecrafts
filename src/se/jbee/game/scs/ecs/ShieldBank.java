package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("shieldbank")
public final class ShieldBank extends EquipmentArray<Shield> {

	public static final class Ref extends EquipmentArray.Ref<ShieldBank> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<ShieldBank> entityType() {
			return ShieldBank.class;
		}
	}
}
