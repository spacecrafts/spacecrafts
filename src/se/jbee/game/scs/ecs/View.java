package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Manifestation;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.scs.ecs.constant.Screen;

/**
 * Controls the current view (screen, actions, etcetera) for human players.
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
	int screenWidth;
	@NonNegative
	int screenHeight;
	/**
	 * Refers to the view element that has the focus, 0 is no focus.
	 */
	@NonNegative
	int focus;
	/**
	 * When paging the {@link #page} refers to the currently viewed page.
	 */
	int page;

	Screen.Ref currentScreen;
	Screen.Ref returnScreen;
	se.jbee.game.any.ecs.comp.Ref<?> baseEntity;
}
