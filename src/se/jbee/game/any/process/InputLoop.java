package se.jbee.game.any.process;

import java.awt.Component;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.ChangeListener;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;

/**
 * On input events or change of screens the {@link Stage} is prepared with the
 * actual {@link State} of the game.
 */
public final class InputLoop implements Runnable, GameComponent, ChangeListener {

	private static final int CYCLE_TIME_MS = 20; 
	
	private final Component display;
	private final Stage stage;
	private final Screen[] screens;
	
	private AtomicReference<State> game = new AtomicReference<>();
	private boolean quit = false;
	private int lastScreen = -1;
	
	/**
	 * This is used so this thread actually applies the event changes coming in
	 * asynchronous to this thread that reads the same game {@link State}. With
	 * this however the human player changes are applied synchronous with the
	 * usage.
	 */
	private final AtomicReference<Change[]> unappliedChangeset = new AtomicReference<>();


	public InputLoop(Stage stage, Screen[] screens, Component display) {
		super();
		this.display = display;
		this.stage = stage;
		this.screens = screens;
		stage.listener = this;
	}
	
	public void setGame(State game) {
		this.game.set(game);
	}

	@Override
	public void on(Change[] changeset) {
		if (unappliedChangeset.compareAndSet(null, changeset)) {
			lastScreen = -1;
		} // we ignore changes coming in "to fast" (should not happen anyway)
	}
	
	@Override
	public void run() {
		while (!quit) {
			State game = this.game.get();
			long loopStart = System.currentTimeMillis();
			Change[] changeset = unappliedChangeset.getAndSet(null);
			if (changeset != null) {
				Change.apply(changeset, game);				
			}
			int currentScreen = game.root().num(SCREEN);
			if (currentScreen != lastScreen) {
				stage.startOver();
				screens[currentScreen].show(game, new Dimension(display.getSize()), stage);
				stage.ready();
				lastScreen = currentScreen;
			}
			// sleep so that drawing + sleeping = loop time
			long cycleTimeMs = System.currentTimeMillis() - loopStart;
			if (cycleTimeMs < CYCLE_TIME_MS) {
				try { Thread.sleep(CYCLE_TIME_MS - cycleTimeMs); } catch (Exception e) {}
			}
		}
	}

}
