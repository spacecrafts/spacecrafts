package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Range;

@EntityType("tech")
public final class Technology extends Preselection {

	public static final class Ref extends ShortRef<Technology> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Technology> entityType() {
			return Technology.class;
		}
	}

	public ResearchField.Ref field;

	@Range(min=1, max=6)
	public byte techLevel;
}
