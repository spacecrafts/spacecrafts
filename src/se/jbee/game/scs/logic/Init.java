package se.jbee.game.scs.logic;

import static se.jbee.game.uni.state.Entity.codePoints;

import java.io.File;
import java.io.FilenameFilter;

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
		gamE.put(SETUP, new int[] {1,1,1});
	}

	private static String uniqueGameName(long seed, State user) {
		File dir = new File(user.defEntity(USER).text(SAVEGAME_DIR));
		Rnd rnd = new Rnd(seed);
		while (true) {
			final String gameName = Name.name(Name.NAME_GALAXIA, rnd.nextLong());
			final String pattern = gameName+"-";
			String[] games = dir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return !name.contains(pattern);
				}
			});
			if (games == null || games.length == 0)
				return gameName;
		}
	}

	public static void initUser(State user) {
		if (user.all(USER).length == 0) {
			Entity u1 = user.defEntity(USER);
			u1.put(SAVEGAME_DIR, codePoints(System.getProperty("user.home")+File.separator+"spacecrafts"));
		}
	}
}
