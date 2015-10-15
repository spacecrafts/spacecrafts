package se.jbee.game.scs.logic;

import static se.jbee.game.uni.state.Entity.codePoints;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import se.jbee.game.scs.process.Game;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;
import se.jbee.game.scs.state.UserComponent;
import se.jbee.game.uni.logic.Progress;
import se.jbee.game.uni.state.Entity;
import se.jbee.game.uni.state.Name;
import se.jbee.game.uni.state.Rnd;
import se.jbee.game.uni.state.State;

/**
 * Initializes game and user entities for a fresh game.
 */
public class Init implements Progress, GameComponent, UserComponent {

	@Override
	public void progress(State user, State game) {
		initUser(user);
		initGame(game, user);
	}

	public static void initUser(State user) {
		if (user.all(USER).length == 0) {
			Entity u1 = user.defEntity(USER);
			u1.put(SAVEGAME_DIR, codePoints(System.getProperty("user.home")+File.separator+"spacecrafts"));
		}
	}

	public static void initGame(State game, State user) {
		Entity gamE = game.defEntity(GAME);
		long seed = System.currentTimeMillis();
		gamE.put(SEED, seed);
		gamE.put(NAME, codePoints(uniqueGameName(seed, user)));
		gamE.put(TURN, 0);
		gamE.put(SCREEN, GameScreen.SCREEN_MAIN);
		Entity p1 = game.defEntity(PLAYER);
		p1.put(NO, 1);
		p1.put(TURN, -1);
		gamE.put(PLAYERS, p1.id());
		gamE.put(SETUP, new int[] {1,1,3});
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
