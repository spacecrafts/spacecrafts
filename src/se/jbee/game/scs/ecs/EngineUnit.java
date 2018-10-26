package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;

@Entity("engineunit")
public final class EngineUnit extends EquipmentArray<Engine> {

	public static final class Ref extends EquipmentArray.Ref<EngineUnit> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<EngineUnit> entityType() {
			return EngineUnit.class;
		}
	}

	@NonNegative
	public int totalThrust;
}
