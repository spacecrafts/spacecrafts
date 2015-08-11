package se.jbee.game.scs.process;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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
	
	private List<int[]> nextObjects;
	private List<int[]> nextAreaObjects;
	private AtomicBoolean ready = new AtomicBoolean(false);
	
	public void startOver() {
		ready.set(false);
		onLeftClick.clear();
		onRightClick.clear();
		onMouseOver.clear();
		onKeyPress.clear();
		areaObjects.set(Collections.<int[]>emptyList());
		nextObjects = new ArrayList<int[]>();
		nextAreaObjects = new ArrayList<int[]>();
	}
	
	public void ready() {
		objects.set(nextObjects);
		areaObjects.set(nextAreaObjects);
		ready.set(true);
	}
	
	public boolean isReady() {
		return ready.get();
	}
	
	public Scene place(int[] object) {
		nextObjects.add(object);
		return this;
	}
	
	public Scene bindLeftClick(Shape area, Change... changeset) {
		onLeftClick.add(new AreaMapping(area, changeset));
		return this;
	}
	
	public Scene bindRightClick(Shape area, Change... changeset) {
		onRightClick.add(new AreaMapping(area, changeset));
		return this;
	}
	
	public Scene bind(Shape area, int[]...objects) {
		onMouseOver.add(new AreaObject(area, Arrays.asList(objects)));
		return this;
	}
}
