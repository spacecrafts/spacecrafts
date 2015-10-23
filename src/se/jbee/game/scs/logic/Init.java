package se.jbee.game.scs.logic;

import static se.jbee.game.any.state.Entity.codePoints;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Name;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;

/**
 * Initializes game and user entities for a fresh game.
 */
public class Init implements Transition, GameComponent, UserComponent {

	@Override
	public void transit(State user, State game) {
		initUser(user);
		initGame(game, user);
	}

	public static void initUser(State user) {
		if (user.all(USER).length == 0) {
			Entity u1 = user.defEntity(USER);
			u1.set(SAVEGAME_DIR, codePoints(System.getProperty("user.home")+File.separator+"spacecrafts"));
		}
	}

	public static void initGame(State game, State user) {
		Entity gamE = game.defEntity(GAME);
		long seed = System.currentTimeMillis();
		gamE.set(SEED, seed);
		gamE.set(NAME, codePoints(uniqueGameName(seed, user)));
		gamE.set(TURN, 0);
		gamE.set(SCREEN, GameScreen.SCREEN_MAIN);
		Entity p1 = game.defEntity(PLAYER);
		p1.set(NO, 1);
		p1.set(TURN, -1);
		gamE.set(PLAYERS, p1.id());
		gamE.set(SETUP, new int[] {1,1,3});
	}

	private static String uniqueGameName(long seed, State user) {
		File dir = new File(user.defEntity(USER).text(SAVEGAME_DIR));
		Rnd rnd = new Rnd(seed);
		Set<String> games = new HashSet<>();
		String[] files = dir.list();
		if (files != null){
			games.addAll(Arrays.asList(files));
		}
		while (true) {
			final String name = Name.name(Name.NAME_GALAXIA, rnd.nextLong());
			final String folder = Game.savegameFolder(name);
			if (!games.contains(folder))
				return name;
		}
	}

}
