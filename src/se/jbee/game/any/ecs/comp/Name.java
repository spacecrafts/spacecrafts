package se.jbee.game.any.ecs.comp;

import static java.util.Arrays.copyOfRange;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;

public final class Name implements ComponentType, CharSequence {

	@Component(0)
	public final char[] characters;

	public Name(char[] characters) {
		this.characters = characters;
	}

	@Override
	public int length() {
		return characters.length;
	}

	@Override
	public char charAt(int index) {
		return characters[index];
	}

	@Override
	public Name subSequence(int start, int end) {
		return new Name(copyOfRange(characters, start, end));
	}

	@Override
	public String toString() {
		return String.valueOf(characters);
	}
}
