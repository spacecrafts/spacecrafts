package se.jbee.game.scs.logic;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import se.jbee.game.any.gfx.RGBA;
import se.jbee.game.any.logic.Progress;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Name;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.state.GameComponent;
import data.Data;

/**
 * The most important game transition.
 *
 * When all players have reached same turn as the game the game forwards to next
 * turn by accumulating costs, earnings and building progress.
 */
public class Turn implements Progress, GameComponent {

	private static final int GALAXY_ZS = 500;
	private static final int GALAXY_XS = 4000;
	private static final int GALAXY_YS = 2500;

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
		initialiseGameData(game);
		distributeStarsInGalaxy(game);
		distributePlayersAmongStars(game);
	}

	private void initialiseGameData(State game) {
		try {
			Data.load("/data/", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Color starColor(long seed) {
		Rnd rnd = new Rnd(seed);
		int dist = rnd.nextInt(255);
		int r = rnd.nextInt(200, 255);
		int g = min(255, rnd.nextInt(120, 180)+dist/4);
		int b = max(0, min(255, rnd.nextInt(60, 120)-dist/3));
		int a = rnd.nextInt(220, 255);
		if (dist < 15) { // white
			return new Color(255-dist,255,255,a);
		}
		if ((dist % 2 == 1)) {
			if (dist < 50) { // red
				g = g/2;
				b = b/2;
			}
			if (dist > 220) { // purple
				g = g*2/3;
				b = min(255,r+100);
				r = r-75;
			}
		}
		if ((dist % 2) == 0 && dist > 150) { // blue
			r = dist > 200 ? max(0, r) :  max(0, r-dist/2);
			g = min(255, g + dist*2/3);
			b = min(255, b + dist);
		}
		return new Color(r, g, b, a);
	}

	private static void distributePlayersAmongStars(State game) {
		Entity gamE = game.single(GAME);
		// for now just pick random places
		Rnd rnd = new Rnd(gamE.longNum(SEED));
		int[] players = gamE.list(PLAYERS);
		Entity[] stars = game.entities(game.single(GALAXY).list(STARS));
		int[] homes = D3.distantClusters(players.length, POSITION, stars);
		for (int i = 0; i < players.length; i++) {
			Entity star = stars[homes[i]];
			Entity player = game.entity(players[i]);
			star.put(SEED, rnd.nextLong());
			star.put(HOME, player.id());
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

	private static void distributeStarsInGalaxy(State game) {
		Entity gamE = game.single(GAME);
		int gsize = gamE.list(SETUP)[SETUP_GALAXY_SIZE];
		Entity galaxy = game.defEntity(GALAXY);
		long seed = System.currentTimeMillis();
		galaxy.put(SEED, seed);
		galaxy.put(SIZE, GALAXY_XS, GALAXY_YS, GALAXY_ZS);
		gamE.append(GALAXIES, galaxy.id());
		Rnd rnd = new Rnd(seed);
		int nos = rnd.nextInt(35, 50) * gsize;

		final Entity[] starTypeDistribution = starTypeDistribution(game);
		
		int[] stars = new int[nos];
		List<Shape> blockedAreas = new ArrayList<Shape>();
		int minimumGap = 60; // how close stars can be in 2D space in the original scale
		Entity[] eStars = new Entity[nos];
		for (int i = 0; i < nos; i++) {

			long starSeed = rnd.nextLong();
			Rnd starRnd = new Rnd(starSeed);
			Entity star = game.defEntity(STAR);
			// position
			int x, y = 0;
			do {
				x = starRnd.nextInt(0, GALAXY_XS);
				y = starRnd.nextInt(0, GALAXY_YS);
			} while (D3.overlaps(x, y, blockedAreas));
			blockedAreas.add(new Ellipse2D.Float(x-minimumGap, y-minimumGap, minimumGap*2, minimumGap*2));
			int z = starRnd.nextInt(0, GALAXY_ZS);
			star.put(SEED, starSeed);
			star.put(POSITION, x,y,z);
			star.put(NAME, Name.unique(Name.NAME_BEUDONIA, starSeed)); //TODO name lazy when system is discovered
			Entity type = starTypeDistribution[starRnd.nextInt(99)];
			star.put(STAR_TYPE, type.id());
			int[] sizes = type.list(SIZE);
			star.put(SIZE, sizes[starRnd.nextInt(sizes.length-1)]);
			star.put(RGBA, new RGBA(type.list(RGB)).shift(starRnd, 30).toRGBA());
			stars[i] = star.id();
			eStars[i] = star;
		}
		galaxy.put(STARS, stars);
		// set the minimum distance to next star for all the stars
		for (int i = 0; i < nos; i++) {
			Entity star = eStars[i];
			star.put(CLOSEST, (int)D3.closestDistance2D(star, POSITION, eStars));
		}
	}

	private static Entity[] starTypeDistribution(State game) {
		Entity[] dist = new Entity[101];
		int j = 0;
		for (Entity type : game.entities(game.all(STAR_TYPE))) {
			int n = type.num(ABUNDANCE);
			for (int k = 0; k < n; k++) {
				dist[j++] = type;
			}
		}
		return dist;
	}

}
