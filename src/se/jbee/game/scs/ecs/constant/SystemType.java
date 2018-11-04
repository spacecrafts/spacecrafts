package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Module;
import se.jbee.game.scs.ecs.Prototype;
import se.jbee.game.scs.ecs.system.System;

/**
 * What group does a {@link System} belong to?
 *
 * Answers the question: Can a {@link System}s using the
 * {@link SystemType} be placed on a certain cell.
 */
@Entity(":system")
public final class SystemType extends Preselection {

	public static final class Ref extends ShortRef<SystemType> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<SystemType> entityType() {
			return SystemType.class;
		}
	}

	/**
	 * Outward is border on each {@link Module} and the complete bottom and top
	 * {@link Module}s in a {@link Prototype}.
	 */
	public boolean mustBeOutward;

	/**
	 * {@link Material}s are also used for surface and underground materials which
	 * have their own groups. This can be used to restrict building on certain
	 * support materials.
	 */
	public char mustBeOnMaterialGroup;

	public char mustBeOnResourceGroup;

	/*
	 * Special planetary conditions to meet
	 */

	public Resource.Ref mustBeOnResource;
	public Atmosphere.Ref mustHaveAtmosphere;
	public Gravitation.Ref mustHaveGravitation;
	public Surface.Ref mustHaveSurface;

}
