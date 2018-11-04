package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Fabrication;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.Aggregated;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.NonNegative;
import se.jbee.game.any.ecs.meta.Positive;
import se.jbee.game.scs.ecs.constant.Ability;
import se.jbee.game.scs.ecs.constant.Technology;
import se.jbee.game.scs.ecs.system.System;

@Entity("player")
public final class Player extends Fabrication {

	public static final class Ref extends ByteRef<Player> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Player> entityType() {
			return Player.class;
		}
	}
	@Component(4)
	public Race.Ref race;
	@Component(5)
	public Planet.Ref homeworld;
	@Component(8)
	public Technology.Ref researching;
	public boolean ai;
	/**
	 * Summary flag to check if a player has to be considered any longer
	 */
	public boolean defeated = false;
	/**
	 * Set to zero when setup is done. Then set to current {@link Game#turn} when player is done for the current turn. When all players have the current game turn as {@link #completedTurn} the game progresses to the next turn.
	 */
	public int completedTurn = -1;
	@Positive @Component(2)
	public short no;
	@NonNegative @Component(6)
	public int reach; // maybe irrelevant - its all about ship drive
	@NonNegative @Component(7)
	public int totalWisdom;
	@NonNegative
	public int totalControlPoints;
	@Aggregated
	public int leaderReputationPoints;
	@NonNegative @Component(9)
	public int breakthrough;
	@NonNegative @Component(11)
	public int[] playerReputations; // maybe add a type for values in relation to a type of entity and their No (here player)

	/*
	 * a players view (if human)
	 */
	//TODO this should not be part of game state

	/*
	 * a player's belongings
	 */
	public Refs<Project> startedProjects;
	public Refs<Colony> controlledColonies;
	public Refs<Orbit> controlledOrbits;
	public Refs<Fleet> controlledFleets;
	public Refs<Offer> madeOffers;
	public Refs<Offer> receivedOffers;

	/*
	 * a player's discoveries
	 */
	public Refs<Technology> discoveredTechnologies;
	public Refs<System> availableEquipments;
	public Refs<Ability> availableAbilities;

	/*
	 * a player's knowledge of the "universe"
	 */
	public Refs<SolarSystem> knownSolarSystems;
	public Refs<Planet> knownPlanets; // and their moons
	public Refs<Colony> knownColonies;
	public Refs<Spacecraft> knownShips;
	public Refs<Spacestation> knownStations;
	public Refs<Fleet> knownFleets;
	public Refs<Prototype> knownDesigns;

	/*
	 * lists of entities that should be managed during the current turn
	 */

	public boolean unhandledSetInTurn;
	public Refs<Colony> unhandledColonies;
	public Refs<Spacestation> unhandledStations;
	public Refs<Spacecraft> unhandledShips;
	public Refs<Fleet> unhandledFleets;
}
