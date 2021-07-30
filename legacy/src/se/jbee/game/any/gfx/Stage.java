package se.jbee.game.any.gfx;

import static java.lang.Character.toUpperCase;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.ChangeListener;

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
		//TODO add a level above the concrete keys with abstract actions like "move up", "move back"
		public final int keyCode;
		//TODO add description what key does
		public final Change[] changeset;
		public KeyMapping(int keyCode, Change... changeset) {
			super();
			this.keyCode = keyCode;
			this.changeset = changeset;
		}
	}

	public static final class Hover {
		public final Shape area;
		public final int cursor;
		public final List<Drawable> objects;
		public Hover(Shape area, int cursor, Drawable object) {
			this(area, cursor, Collections.singletonList(object));
		}
		public Hover(Shape area, int cursor, List<Drawable> objects) {
			super();
			this.area = area;
			this.cursor = cursor;
			this.objects = objects;
		}
	}

	//TODO  make this primary and secondary button
	public final List<AreaMapping> onLeftClick = new ArrayList<>();
	public final List<AreaMapping> onRightClick = new ArrayList<>();
	public final List<Hover> onPointing = new ArrayList<>();
	//TODO incorporate mouse and keys into a more abstract concept of interaction that connects meaning with actual inputs
	// something that asks: what is the player trying to do? (not how is the input for ...)
	public final List<KeyMapping>  onKeyPress = new ArrayList<>();
	public final List<KeyMapping>  globalOnKeyPress = new ArrayList<>();

	public final AtomicReference<List<Drawable>> objects = new AtomicReference<>(Collections.<Drawable>emptyList());
	private final AtomicReference<List<Drawable>> highlights = new AtomicReference<>(Collections.<Drawable>emptyList());

	private List<Drawable> nextObjects;
	private List<Drawable> nextHighlights;
	private AtomicBoolean ready = new AtomicBoolean(false);
	private AtomicBoolean inputsDisabled = new AtomicBoolean(false);
	public ChangeListener listener;

	/**
	 * The frame tracks any changes to the stage so that displaying device can
	 * skip repaint as long as the frame has not changed.
	 */
	private int frame = 0;

	public int frame() {
		return frame;
	}

	public void highlight(List<Drawable> objects) {
		List<Drawable> old = highlights.getAndSet(objects);
		if (!old.isEmpty() || !objects.isEmpty()) {
			frame++;
		}
	}

	public void startOver() {
		ready.set(false);
		inputsDisabled.set(false);
		onLeftClick.clear();
		onRightClick.clear();
		onPointing.clear();
		onKeyPress.clear();
		highlights.set(Collections.<Drawable>emptyList());
		nextObjects = new ArrayList<>();
		nextHighlights = new ArrayList<>();
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

	/**
	 * Adds the object in the front of the current stage.
	 * (an object painted after all already added)
	 */
	public Stage atFront(Drawable object) {
		nextObjects.add(object);
		return this;
	}

	/**
	 * Adds the object in the back of the current stage.
	 * (an object painted before all already added)
	 */
	public Stage atBack(Drawable object) {
		nextObjects.add(0, object);
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

	public Stage in(Shape area, Drawable...objects) {
		return in(area, -1, objects);
	}

	public Stage in(Shape area, int cursor, Drawable...objects) {
		onPointing.add(new Hover(area, cursor, Arrays.asList(objects)));
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

	public void draw(Graphics2D gfx, Resources resources) {
		draw(gfx, resources, objects.get());
		draw(gfx, resources, highlights.get());
	}

	private static void draw(Graphics2D gfx, Resources resources, List<Drawable> objects) {
		for (Drawable obj : objects) {
			obj.draw(gfx, resources);
		}
	}
}
