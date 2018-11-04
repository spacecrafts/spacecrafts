package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.constant.Screen;

/**
 * Controls the current view (screen, actions, etcetera) for human {@link Player}s.
 */
public final class View extends Manifestation {

	public static final class Ref extends ByteRef<View> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<View> entityType() {
			return View.class;
		}
	}

	@NonNegative
	short screenWidth;
	@NonNegative
	short screenHeight;
	/**
	 * Refers to the view element that has the focus, 0 is no focus.
	 */
	@NonNegative
	byte focus;
	/**
	 * When paging the {@link #page} refers to the currently viewed page.
	 * First page being page 0.
	 */
	@NonNegative
	short page;

	Screen.Ref currentScreen;
	Screen.Ref returnScreen;
	se.jbee.game.any.ecs.comp.Ref<?> baseEntity;
}
