package se.jbee.game.scs.ecs.comp;

import java.util.ArrayList;
import java.util.List;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.Entity;

public final class HexGrid<T extends Entity> implements ComponentType {

	private final short width;
	private final byte[] grid;
	private final List<T> cells = new ArrayList<>();
	
	public HexGrid(short width, short height) {
		this.width = width;
		this.grid = new byte[width * height];
	}
	
	
}
