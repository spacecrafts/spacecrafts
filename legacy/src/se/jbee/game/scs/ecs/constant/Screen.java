package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;

@EntityType("screen")
public abstract class Screen extends Preselection {

	public static final class Ref extends ByteRef<Screen> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Screen> entityType() {
			return Screen.class;
		}
	}

	// RENDERING
	// -----------
	// change from "pre-rendered" objects to direct rendering in every screen as
	// just the screens with lots of details otherwise have thousands of objects
	// that are not needed. its better to recompute more.

}
