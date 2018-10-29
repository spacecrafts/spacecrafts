package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Range;

@Entity("technology")
public final class Technology extends Option {

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
