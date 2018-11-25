package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("&ability")
public final class Ability extends Preselection {

	public static final class Ref extends ShortRef<Ability> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Ability> entityType() {
			return Ability.class;
		}
	}

}
