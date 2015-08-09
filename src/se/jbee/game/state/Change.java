package se.jbee.game.state;

import java.util.Arrays;


/**
 * A specific change to a specific entity (component). 
 */
public final class Change {

	public static enum Op { /* map*/ PUT, ERASE, /* set */ INSERT, REMOVE, /* list */ APPEND, PREPEND, /* bitset */ SET, UNSET, /* all */ CLEAR }
	
	public final int entity;   // which entity is manipulated
	public final int comp;     // component of the entity that is manipulated (if not clearing all)
	public final Op op;        // in what way is it manipulated?
	public final int[] value;  // the value

	public Change(int entity, int comp, Op op, int[] value) {
		super();
		this.entity = entity;
		this.comp = comp;
		this.op = op;
		this.value = value;
	}

	public Change(int entity, int comp, Op op, int value) {
		this(entity, comp, op, new int[] { value });
	}
	
	public void apply(State game) {
		Entity e = game.entity(entity);
		switch (op) {
		case PUT    : e.put(comp, value); break;
		case ERASE  : e.erase(comp); break;
		case INSERT : e.insert(comp, value); break;
		case REMOVE : e.remove(comp, value); break;
		case APPEND : e.append(comp, value); break;
		case PREPEND: e.prepend(comp, value); break;
		case SET    : e.set(comp, value); break;
		case UNSET  : e.unset(comp, value); break;
		case CLEAR  : e.clear(); break;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s e%d c%d %s", op.name(), entity, comp, Arrays.toString(value) );
	}

}
