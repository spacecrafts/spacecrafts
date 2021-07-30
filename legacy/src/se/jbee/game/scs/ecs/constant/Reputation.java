package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.scs.ecs.Player;

/**
 * One {@link Player} in the eye of another one.
 */
@EntityType("reputation")
public final class Reputation extends Spectrum {

	public static final class Ref extends ByteRef<Reputation> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Reputation> entityType() {
			return Reputation.class;
		}
	}

	/**
	 * The minimum reputation (level) needed to be effective. The instance is valid
	 * to the next higher {@link #minimumReputation}. Thus the lower end (worst
	 * reputation) is marked by minimum reputation of zero, the high end (best
	 * reputation) by that with the highest minimum reputation.
	 */
	@NonNegative
	public int minimumReputation;

	@Percent
	public byte likelihoodOfMakingOffers;
	@Percent
	public byte likelihoodOfAcceptingOffers;
	@Percent
	public byte likelihoodOfRiskingWar;
	@Percent
	public byte likelihoodOfOfferingPeace;
	@Percent
	public byte likelihoodOfLying;
	/**
	 * Controls how strong relevant incidents affect the reputation negatively.
	 */
	@Percent
	public byte toleranceForIncidents;
	/**
	 * How much will a player accept foreign forces in systems with own presence.
	 */
	@Percent
	public byte toleranceForTerritorialCrossing;
	//TODO add more fields that give the likelihood of other diplomatic actions
}
