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
	
	public static final class AreaObject {
		final Shape area;
		final int[] object;
		public AreaObject(Shape area, int[] object) {
			super();
			this.area = area;
			this.object = object;
		}
	}

	public final List<IOMapping.AreaMapping> onLeftClick = new ArrayList<>();
	public final List<IOMapping.AreaMapping> onRightClick = new ArrayList<>();
	public final List<IOMapping.AreaObject> onMouseOver = new ArrayList<>();
	public final List<IOMapping.KeyMapping>  onKeyPress = new ArrayList<>();
	public final List<IOMapping.KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
	public final AtomicReference<List<int[]>> objects = new AtomicReference<>(Collections.<int[]>emptyList());
	public final AtomicReference<List<int[]>> areaObjects = new AtomicReference<>(Collections.<int[]>emptyList());
	
	public void clear() {
		onLeftClick.clear();
		onRightClick.clear();
		onMouseOver.clear();
		onKeyPress.clear();
		areaObjects.set(Collections.<int[]>emptyList());
	}
}
