package se.jbee.game.scs.process;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.state.Change;

public final class Scene {

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
		final List<int[]> objects;
		public AreaObject(Shape area, int[] object) {
			this(area, Collections.singletonList(object));
		}
		public AreaObject(Shape area, List<int[]> objects) {
			super();
			this.area = area;
			this.objects = objects;
		}
	}

	public final List<AreaMapping> onLeftClick = new ArrayList<>();
	public final List<AreaMapping> onRightClick = new ArrayList<>();
	public final List<AreaObject> onMouseOver = new ArrayList<>();
	public final List<KeyMapping>  onKeyPress = new ArrayList<>();
	public final List<KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
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
