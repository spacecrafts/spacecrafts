package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Constant;
import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.Code;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.Colony;
import se.jbee.game.scs.ecs.Module;
import se.jbee.game.scs.ecs.Spacecraft;
import se.jbee.game.scs.ecs.Spacestation;
import se.jbee.game.scs.gfx.obj.Planet;

/**
 * While a fix set of resource {@link Kind}s are embedded into the game
 * mechanics there is a part for each resource that is best described and
 * created as {@link Constant}s.
 *
 * Each {@link Kind} also might come in many concrete resources, like all the
 * rare materials one could mine.
 *
 * Food can also be provided by natural planetary resources that maybe only
 * require a farmer but no special building.
 *
 * Houses can be build using natural building materials available. Such
 * materials are not shipped or counted somewhere. Their presence on a
 * {@link Planet} just enables certain types of buildings (dwellings).
 *
 * Existing temples can spread knowledge and wisdom.
 *
 * {@link Resource}s play an important role in making planetary {@link Colony}s
 * more attractive and important than building production {@link Spacestation}
 * or {@link Spacecraft}s.
 */
@EntityType("&resource")
public final class Resource extends Preselection {

	public static final class Ref extends ByteRef<Resource> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Resource> entityType() {
			return Resource.class;
		}
	}

	public static enum Kind { // this is also the group code or resources
		@Code('h') HOUSING,
		@Code('t') TRAINING, // of troops => # of troops
		@Code('p') PRODUCTION,
		@Code('f') FOOD,
		@Code('r') RARE_MATERIAL,
		@Code('k') KNOWLEDGE,
		@Code('w') WISDOM,
		@Code('c') CULTURE,
		@Code('e') ENERGY,
		@Code('C') CONTROL
	}

	public Kind kind;

	/**
	 * The number of construction points it costs to remove a cell of this resource
	 * completely (leaves the {@link Module#frameMaterial} as new underground).
	 */
	@Positive
	byte extractionCosts;
}
