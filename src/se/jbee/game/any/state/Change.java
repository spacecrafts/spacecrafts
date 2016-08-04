package se.jbee.game.any.state;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.copyOfRange;

import java.util.Arrays;


/**
 * A specific change to a specific entity (component).
 */
public final class Change {

	public static enum Op {
		/* attrs  */ SET, UNSET,
		/* set    */ INSERT, REMOVE,
		/* list   */ REPLACE, APPEND, PREPEND, TAKE, DROP,
		/* map    */ PUT, UNPUT, MOVE,
		/* bitset */ SETBITS, UNSETBITS,
		/* all    */ CLEAR, COPY }

	public static Change set(int entity, int comp, int...value) {
		return new Change(entity, comp, Op.SET, value);
	}

	public static Change unset(int entity, int comp) {
		return new Change(entity, comp, Op.UNSET, null);
	}

	public static Change drop(int entity, int comp, int n) {
		return new Change(entity, comp, Op.DROP, n);
	}

	public static Change take(int entity, int comp, int n) {
		return new Change(entity, comp, Op.TAKE, n);
	}

	public static Change append(int entity, int comp, int... elements) {
		return new Change(entity, comp, Op.APPEND, elements);
	}

	public static Change replace(int entity, int comp, int index, int value) {
		return new Change(entity, comp, Op.REPLACE, index, value);
	}

	public static Change put(int entity, int comp, int key, int value) {
		return new Change(entity, comp, Op.PUT, key, value);
	}
	
	public static Change move(int entityFrome, int comp, int key, int entityTo) {
		return new Change(entityFrome, comp, Op.MOVE, key, entityTo);
	}

	public final int entity;   // which entity is manipulated
	public final int comp;     // component of the entity that is manipulated (if not clearing all)
	public final Op op;        // in what way is it manipulated?
	public final int[] value;  // the value (or general: argument to the operation)

	public Change(int entity, int comp, Op op, int... value) {
		super();
		this.entity = entity;
		this.comp = comp;
		this.op = op;
		this.value = value;
	}

	public Change(int entity, int comp, Op op, int value) {
		this(entity, comp, op, new int[] { value });
	}

	public void apply(State state) {
		Entity e = state.entity(entity);
		switch (op) {
		case SET       : e.set(comp, value); break;
		case UNSET     : e.unset(comp); break;
		case INSERT    : e.insert(comp, value); break;
		case REMOVE    : e.remove(comp, value); break;
		case REPLACE   : e.list(comp)[value[0]] = value[1]; break;
		case APPEND    : e.append(comp, value); break;
		case PREPEND   : e.prepend(comp, value); break;
		case TAKE      : e.set(comp, copyOf(e.list(comp), value[0])); break;
		case DROP      : int[] v = e.list(comp); e.set(comp, copyOfRange(v, value[0], v.length)); break;
		case PUT       : e.put(comp, value[0], value[1]); break;
		case UNPUT     : e.unput(comp, value[0]); break;
		case MOVE      : state.entity(value[2]).put(comp, value[0], e.unput(comp, value[0])); break;
		case SETBITS   : e.setBits(comp, value); break;
		case UNSETBITS : e.unsetBits(comp, value); break;
		case CLEAR     : e.clear(); break;
		case COPY      : e.set(comp, state.entity(value[0]).list(value[1]).clone()); break;
		}
	}

	@Override
	public String toString() {
		return String.format("%s e%d c%d %s", op.name(), entity, comp, Arrays.toString(value) );
	}

	public static void apply(Change[] changeset, State game) {
		for (Change c : changeset) {
			c.apply(game);
		}
	}

}
