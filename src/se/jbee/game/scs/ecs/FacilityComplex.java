package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.meta.Entity;

@Entity("complex")
public final class FacilityComplex extends EquipmentArray<Facility> {

	public static final class Ref extends EquipmentArray.Ref<FacilityComplex> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<FacilityComplex> entityType() {
			return FacilityComplex.class;
		}
	}
}
