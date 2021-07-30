package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("weaponmode")
public final class WeaponMode extends Preselection {

	public static final class Ref extends ByteRef<WeaponMode> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<WeaponMode> entityType() {
			return WeaponMode.class;
		}
	}
}
