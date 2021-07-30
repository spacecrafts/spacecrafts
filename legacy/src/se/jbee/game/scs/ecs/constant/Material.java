package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.Planet;
import se.jbee.game.scs.ecs.Spacecraft;
import se.jbee.game.scs.ecs.comp.Curve;
import se.jbee.game.scs.ecs.layout.Module;
import se.jbee.game.scs.ecs.system.System;

/**
 * {@link Material}s for {@link Spacecraft} construction but also for
 * {@link Planet} surface and underground support materials.
 *
 * Groups are used to distinguish natual from artificial materials.
 */
@EntityType("material")
public final class Material extends Preselection {

	public static final class Ref extends ByteRef<Material> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Material> entityType() {
			return Material.class;
		}
	}

	@Positive
	public byte structurePerCell;
	@Positive
	public byte weightPerCell;
	/**
	 * The costs of using the material to build a {@link Module} from it in relation
	 * to the size (number of cells or slots)
	 */
	public Curve constructionCosts;

	/**
	 * The maximum summed {@link System#weight}s within the same {@link Module}
	 * possible. This limits the maximum {@link Module} size depending on the
	 * concrete {@link System}s used.
	 */
	@Positive
	public int loadCapacity;

	/**
	 * True for natural occurring materials. This is a indication that the material
	 * is found on the surface or underground of a {@link Planet} and not man made.
	 */
	public boolean planetary;
}
