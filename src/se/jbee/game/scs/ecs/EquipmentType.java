package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ShortRef;

public final class EquipmentType extends Spectrum {

	public static final class Ref extends ShortRef<EquipmentType> {
	
		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<EquipmentType> entityType() {
			return EquipmentType.class;
		}
	}

	/**
	 * Some components are attachment for other types of components in which case
	 * this type is referenced here.
	 */
	EquipmentType.Ref attachmentFor;

	public boolean isStandalone() {
		//TODO make an "any" reference instead?
		return attachmentFor == null;
	}

	// Add-ons
	//--------
	// * computer (add-on to any type of weapon) => requires groups of types
	// * ammunition

	// Components
	//------------
	// * Shield
	// * Jammer
	// * Engine
	// * Facility
	// * Stabiliser (gravity)
}
