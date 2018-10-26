package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Option;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.comp.Curve;

@Entity("material")
public final class Material extends Option {

	public static final class _Material extends ShortRef<Material> {
	
		public _Material(short serial) {
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
	 * The costs of using the material to build a {@link Segment} from it in relation
	 * to the size (number of cells/slots)
	 */
	public Curve constructionCosts;
}
