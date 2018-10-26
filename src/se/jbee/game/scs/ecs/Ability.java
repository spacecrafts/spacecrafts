package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;

@Entity("ability")
public final class Ability extends Option {

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
