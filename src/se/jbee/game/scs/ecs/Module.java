package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Aggregating;
import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.comp.Locations;
import se.jbee.game.scs.ecs.constant.Material;
import se.jbee.game.scs.ecs.device.Device;
import se.jbee.game.scs.ecs.system.System;

/**
 * A {@link Module} describes the {@link Layout} of a self-contained unit of a
 * {@link Spacecraft}, a {@link Spacestation} or {@link Colony} in a full
 * {@link Blueprint}.
 *
 * It is not necessarily self-contained in the sense that it functions on its
 * own but that it is build out of a certain {@link Material} and that it can be
 * recompsed at som epoint. On {@link Planet}s or {@link Moon}s {@link Module}s
 * describe the different areas on the surface of the planet.
 *
 * For {@link Spacecraft}s and {@link Spacestation}s it is called "sector".
 *
 * Within a {@link Colony} a {@link Module} is called "district" or "area". (or
 * is the whole planet one {@link Module}?). One benefit of building on
 * {@link Planet}s is that no additional costs for building {@link Module}s
 * arises as the {@link Material} is "already payed for".
 */
@EntityType("module")
public final class Module extends Layout implements Aggregating {

	public static final class Ref extends ShortRef<Module> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Module> entityType() {
			return Module.class;
		}
	}

	/**
	 * The {@link Blueprint} this {@link Module} is a part of.
	 */
	public Blueprint.Ref blueprint;

	/**
	 * Each {@link Module} can be made of a particular frame {@link Material} that
	 * determines the base costs of the structure. This cost is calculated by the
	 * number of {@link System} cells within the {@link Module}.
	 */
	public Material.Ref frameMaterial;

	public Device.Refs devices;

	public Locations emptyCells;

	@Override
	public void aggregate(State state) {
		int n = emptyCells.size();
		int deviceCosts = 0;
		int deviceWeight = 0;
		for (int i = 0; i < devices.size(); i++) {
			Device<?, ?> d = state.entity(devices, i);
			deviceCosts += d.totalConstructionCosts;
			deviceWeight += d.totalWeight;
			n += d.cells.size();
		}
		Material material = state.entity(frameMaterial);
		int materialCosts = material.constructionCosts.at(n);
		totalWeight = n * material.weightPerCell + deviceWeight;
		totalStructure = n * material.structurePerCell;
		totalConstructionCosts = materialCosts + deviceCosts;
	}

}
