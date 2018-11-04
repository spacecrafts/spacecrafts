package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Preselection;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.Frame;

@Entity("#frame")
public final class FrameClassification extends Preselection {

	public static final class Ref extends ShortRef<FrameClassification> {

		public Ref(short serial) {
			super(serial);
		}
		@Override
		public Class<FrameClassification> entityType() {
			return FrameClassification.class;
		}
	}

	// examples ship: fighter, bomber, cruiser, light cruiser, frigate, destroyer, death star, crew transporter (armored personal carrier)
	// examples station: battle station, research station
	// examples colony: outpost, hellhole, mining colony, mining town, mining settlement, paradise, trading post

	/**
	 * The kind of {@link Frame} this {@link FrameClassification} describes.
	 */
	public Frame.Kind kind;

	/**
	 * Number of cells used by a {@link System} this class usually has.
	 */
	@Positive
	public short size;

	/*
	 * 100% divided into 6 areas of application: attack, defence, supply, special, crew
	 * Each system belongs to one of these 5 areas.
	 */

	@Percent
	public byte attackCosts;

	@Percent
	public byte defenceCosts;

	@Percent
	public byte supplyCosts;

	@Percent
	public byte specialCosts;

	@Percent
	public byte crewCosts;

	/*
	 * Effects of belonging/having this frame classification...
	 */

	@Percent
	public byte implicitThreatLevel;

	// boni? morale...
}
