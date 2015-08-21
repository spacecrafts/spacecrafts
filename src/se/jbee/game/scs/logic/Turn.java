package se.jbee.game.scs.logic;

import static java.lang.Math.sqrt;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.uni.gfx.Rnd;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.State;

/**
 * The most important game transition.
 * 
 * When all players have reached same turn as the game the game forwards to next
 * turn by accumulating costs, earnings and building progress.
 */
public class Turn implements Progress, GameComponent {

	private static final int GALAXY_DEPTH = 500;
	private static final int GALAXY_SIZE = 4000;

	@Override
	public void progress(State user, State game) {
		final Entity gamE = game.single(GAME);
		final int turn = gamE.num(TURN);
		
		if (turn == 0) {
			initialiseNewGameWorld(game);
		}
		// 1. trade
		
		
		
		gamE.put(TURN, turn+1); // forward turn
		gamE.put(ACTION, ACTION_STEP); // make the right screen appear for the first player
	}

	private void initialiseNewGameWorld(State game) {
		distributeStarsInGalaxy(game); 
		distributePlayersAmongStars(game);
	}
	
	private static void distributePlayersAmongStars(State game) {
		Entity gamE = game.single(GAME);
		// for now just pick random places
		Rnd rnd = new Rnd(gamE.longNum(SEED));
		int[] players = gamE.list(PLAYERS);
		int[] stars = game.single(GALAXY).list(STARS);
		long seed = System.currentTimeMillis();
		for (int i = 0; i < players.length; i++) {
			int n = rnd.nextInt(0, stars.length-1);
			Entity star = game.entity(stars[n]);
			Entity player = game.entity(players[i]);
			star.put(SEED, seed+n); // make sure all stars get different seeds
			player.put(STAR, star.id());
			distributePlanetsInSolarSystem(game, star);
			int[] planets = star.list(PLANETS);
			// we just pick the first for now
			int planetID = planets[0];
			player.put(HOME, planetID);
			Entity colony = game.defEntity(COLONY);
			colony.put(PLAYER, player.id());
			colony.put(PLANET, planetID);
			Entity planet = game.entity(planetID);
			planet.put(COLONY, colony.id());
			Entity orbit = game.defEntity(ORBIT);
			orbit.put(PLANET, planetID);
			orbit.put(COLONY, colony.id());
			colony.put(ORBIT, orbit.id());
		}
	}

	private static void distributePlanetsInSolarSystem(State game, Entity star) {
		Rnd rnd = new Rnd(star.longNum(SEED));
		int nop = rnd.nextInt(2, 4);
		int[] planets = new int[nop];
		for (int i = 0; i < nop; i++) {
			Entity planet = game.defEntity(PLANET);
			planet.put(STAR, star.id());
			planet.put(POSITION, i+1); // as a planet orbits around the star it does not have a fix position in overall space, the position is simply the distance from the star, here simplified by its position
			planets[i] = planet.id();
		}
		star.put(PLANETS, planets);
	}
	
	static double distance(int[] xyz0, int[] xyz1) {
		int dx = xyz1[0] - xyz0[0];
		int dy = xyz1[1] - xyz0[1];
		int dz = xyz1[2] - xyz0[2];
		return sqrt(dx*dx+dy*dy+dz*dz);
	}

	private static void distributeStarsInGalaxy(State game) {
		Entity gamE = game.single(GAME);
		int size = gamE.list(SETUP)[SETUP_GALAXY_SIZE];
		Entity galaxy = game.defEntity(GALAXY);
		long seed = System.currentTimeMillis();
		galaxy.put(SEED, seed);
		galaxy.put(SIZE, GALAXY_SIZE);
		gamE.append(GALAXIES, galaxy.id());
		Rnd rnd = new Rnd(gamE.longNum(SEED));
		int nos = rnd.nextInt(35, 50) * size;
		
		int[] stars = new int[nos];
		for (int i = 0; i < nos; i++) {
			int x = rnd.nextInt(0, GALAXY_SIZE);
			int y = rnd.nextInt(0, GALAXY_SIZE);
			int z = rnd.nextInt(0, GALAXY_DEPTH);
			
			Entity star = game.defEntity(STAR);
			star.put(SEED, rnd.nextLong());
			star.put(POSITION, x,y,z);
			stars[i] = star.id();
		}
		galaxy.put(STARS, stars);
	}
	

}
