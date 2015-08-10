package se.jbee.game.scs.process;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.state.Change;

public final class IOMapping {

	public static final class AreaMapping {
		final Shape area;
		final Change[] changeset;
		public AreaMapping(Shape area, Change... changeset) {
			super();
			this.area = area;
			this.changeset = changeset;
		}
	}

	public static final class KeyMapping {
		final int key;
		final Change[] changeset;
		public KeyMapping(int key, Change... changeset) {
			super();
			this.key = key;
			this.changeset = changeset;
		}
	}

	public final List<IOMapping.AreaMapping> onLeftClick = new ArrayList<>();
	public final List<IOMapping.AreaMapping> onRightClick = new ArrayList<>();
	public final List<IOMapping.AreaMapping> onMouseHover = new ArrayList<>();
	public final List<IOMapping.KeyMapping>  onKeyPress = new ArrayList<>();
	public final List<IOMapping.KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
	public final AtomicReference<List<int[]>> objects = new AtomicReference<>(Collections.<int[]>emptyList());
	
	public void clear() {
		onLeftClick.clear();
		onRightClick.clear();
		onMouseHover.clear();
		onKeyPress.clear();
	}
}
