package se.jbee.game.common.gfx;

import static java.lang.Character.toUpperCase;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.common.state.Change;

public final class Stage {

	public static final class AreaMapping {
		public final Shape area;
		public final Change[] changeset;
		public AreaMapping(Shape area, Change... changeset) {
			super();
			this.area = area;
			this.changeset = changeset;
		}
	}
	
	public static final class KeyMapping {
		public final int keyCode; // TODO use keycode here and in check loop 
		//TODO add description what key does
		public final Change[] changeset;
		public KeyMapping(int keyCode, Change... changeset) {
			super();
			this.keyCode = keyCode;
			this.changeset = changeset;
		}
	}
	
	public static final class AreaObject {
		public final Shape area;
		public final int cursor;
		public final List<int[]> objects;
		public AreaObject(Shape area, int cursor, int[] object) {
			this(area, cursor, Collections.singletonList(object));
		}
		public AreaObject(Shape area, int cursor, List<int[]> objects) {
			super();
			this.area = area;
			this.cursor = cursor;
			this.objects = objects;
		}
	}

	public final List<AreaMapping> onLeftClick = new ArrayList<>();
	public final List<AreaMapping> onRightClick = new ArrayList<>();
	public final List<AreaObject> onMouseOver = new ArrayList<>();
	public final List<KeyMapping>  onKeyPress = new ArrayList<>();
	public final List<KeyMapping>  globalOnKeyPress = new ArrayList<>();
	
	public final AtomicReference<List<int[]>> objects = new AtomicReference<>(Collections.<int[]>emptyList());
	private final AtomicReference<List<int[]>> highlights = new AtomicReference<>(Collections.<int[]>emptyList());
	
	private List<int[]> nextObjects;
	private List<int[]> nextHighlights;
	private AtomicBoolean ready = new AtomicBoolean(false);
	private AtomicBoolean inputsDisabled = new AtomicBoolean(false);
	
	/**
	 * The frame tracks any changes to the stage so that displaying device can
	 * skip repaint as long as the frame has not changed.
	 */
	private int frame = 0;
	
	public int frame() {
		return frame;
	}
	
	public List<int[]> accents() {
		return highlights.get();
	}
	
	public void highlight(List<int[]> objects) {
		List<int[]> old = highlights.getAndSet(objects);
		if (!old.isEmpty() || !objects.isEmpty()) {
			frame++;
		}
	}
	
	public void startOver() {
		ready.set(false);
		inputsDisabled.set(false);
		onLeftClick.clear();
		onRightClick.clear();
		onMouseOver.clear();
		onKeyPress.clear();
		highlights.set(Collections.<int[]>emptyList());
		nextObjects = new ArrayList<int[]>();
		nextHighlights = new ArrayList<int[]>();
	}
	
	public void ready() {
		objects.set(nextObjects);
		highlights.set(nextHighlights);
		frame++;
		ready.set(true);
	}
	
	public boolean isReady() {
		return ready.get();
	}
	
	public void disableInputs() {
		inputsDisabled.set(true);
	}
	
	public boolean isReadyForInputs() {
		return isReady() && !inputsDisabled.get();
	}
	
	public Stage enter(int[] object) {
		nextObjects.add(object);
		return this;
	}
	
	public Stage onLeftClickIn(Shape area, Change... changeset) {
		onLeftClick.add(new AreaMapping(area, changeset));
		return this;
	}
	
	public Stage onRightClickIn(Shape area, Change... changeset) {
		onRightClick.add(new AreaMapping(area, changeset));
		return this;
	}
	
	public Stage in(Shape area, int[]...objects) {
		return in(area, -1, objects);
	}
	
	public Stage in(Shape area, int cursor, int[]...objects) {
		onMouseOver.add(new AreaObject(area, cursor, Arrays.asList(objects)));
		return this;
	}

	/**
	 * just makes sense for a-z => A-Z but this is what this is for 
	 */
	public Stage onKey(char keyChar, Change... changeset) {
		return onKey((int)toUpperCase(keyChar), changeset);
	}	
	public Stage onKey(int keyCode, Change... changeset) {
		onKeyPress.add(new KeyMapping(keyCode, changeset));
		return this;
	}
	
	/**
	 * just makes sense for a-z => A-Z but this is what this is for 
	 */
	public Stage onGlobalKey(char keyChar, Change... changeset) {
		return onGlobalKey((int)toUpperCase(keyChar), changeset);
	}
	
	public Stage onGlobalKey(int keyCode, Change... changeset) {
		globalOnKeyPress.add(new KeyMapping(keyCode, changeset));
		return this;
	}

}
