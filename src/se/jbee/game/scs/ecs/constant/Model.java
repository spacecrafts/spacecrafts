package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.Frame;

@EntityType("&model")
public final class Model extends Preselection {

	public static final class Ref extends ShortRef<Model> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<Model> entityType() {
			return Model.class;
		}
	}

	// examples ship: fighter, bomber, cruiser, light cruiser, frigate, destroyer, death star, crew transporter (armored personal carrier)
	// examples station: battle station, research station
	// examples colony: outpost, hellhole, mining colony, mining town, mining settlement, paradise, trading post

	/**
	 * The kind of {@link Frame} this {@link Model} describes.
	 */
	public Frame.Kind kind;

	/**
	 * Number of cells used by a {@link System} this class usually has.
	 */
	@Positive
	public short typicalSize;

	/*
	 * 100% divided into 5 areas of application: attack, defence, supply, special, crew
	 * Each system belongs to one of these 5 areas.
	 */

	@Percent
	public byte attackCostsQuota;

	@Percent
	public byte defenceCostsQuota;

	@Percent
	public byte supplyCostsQuota;

	@Percent
	public byte specialCostsQuota;

	@Percent
	public byte crewCostsQuota;

	/*
	 * Effects of belonging/having this frame classification...
	 */

	@Percent
	public byte implicitThreatLevel;

	// boni? morale...
}
