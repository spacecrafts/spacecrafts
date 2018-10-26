package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("race")
public final class Race extends Fabrication {

	public static final class Ref extends ByteRef<Race> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Race> entityType() {
			return Race.class;
		}
	}

	public Refs<Trait> traits;
	public Refs<Ability> initialAbilities;
	public Refs<Equipment> initialEquipments;
	public Refs<Technology> initialTechnologies;
}
