package se.jbee.game.scs.process;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.state.Change;
import se.jbee.game.state.State;

/**
 * The {@link Players} process takes the role of the currently active human
 * player. It prepares the screen and actions for the current state of that
 * player so the display process shows them.
 * 
 * The process processes UI input events, determines change-sets and applies
 * them. In such cases it updates itself after a change-set had been applied.
 * Then it waits for more events or an update signal from the game process.
 * 
 * The game process might send an update signal when the game process itself has
 * done state manipulations, like when battles occur or a new turn has started.
 * 
 * The currently active player is not stored within this process but part of the
 * game state.
 */
public final class Players implements Runnable, GameComponent {

	private final State game;
	private final State settings;
	
	private final List<AreaMapping> onLeftClick = new ArrayList<>();
	private final List<AreaMapping> onRightClick = new ArrayList<>();
	private final List<AreaMapping> onMouseHover = new ArrayList<>();
	private final List<KeyMapping>  onKeyPress = new ArrayList<>();
	private final List<KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
	public Players(State game, State settings) {
		super();
		this.game = game;
		this.settings = settings;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	static final class AreaMapping {
		final Shape area;
		final Change[] changeset;
		public AreaMapping(Shape area, Change[] changeset) {
			super();
			this.area = area;
			this.changeset = changeset;
		}
	}
	
	static final class KeyMapping {
		final int key;
		final Change[] changeset;
		public KeyMapping(int key, Change[] changeset) {
			super();
			this.key = key;
			this.changeset = changeset;
		}
	}
}
