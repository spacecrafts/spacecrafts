package se.jbee.game.scs.logic;

import static se.jbee.game.any.state.Entity.codePoints;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.any.logic.Transition;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.Name;
import se.jbee.game.any.state.Rnd;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.screen.GameScreen;
import se.jbee.game.scs.state.GameComponent;

/**
 * Initializes game and user entities for a fresh game before the custom
 * {@link Setup} is done.
 */
public class Init implements Transition, GameComponent {

	@Override
	public State transit(State game, Logic logic) {
		game.defComponents(GameComponent.class);
		Entity gamE = game.defRoot(GAME);
		long seed = System.currentTimeMillis();
		gamE.set(SEED, seed);
		gamE.set(NAME, codePoints(uniqueGameName(seed, game)));
		gamE.set(TURN, 0);
		gamE.set(SAVEGAME_DIR, codePoints(System.getProperty("user.home")+File.separator+"spacecrafts"));
		gamE.set(RETURN_SCREEN, GameScreen.SCREEN_MAIN);
		gamE.set(SCREEN, GameScreen.SCREEN_SETUP_CONTROLLS);
		Entity p1 = game.defEntity(PLAYER);
		p1.set(NO, 1);
		p1.set(TURN, -1);
		gamE.set(PLAYERS, p1.id());
		gamE.set(SETUP, new int[] {1,20,3});
		return game;
	}

	private static String uniqueGameName(long seed, State game) {
		File dir = new File(game.single(GAME).string(SAVEGAME_DIR));
		Rnd rnd = new Rnd(seed);
		Set<String> games = new HashSet<>();
		String[] files = dir.list();
		if (files != null){
			games.addAll(Arrays.asList(files));
		}
		while (true) {
			final String name = Name.name(Name.NAME_GALAXIA, rnd.nextLong());
			final String folder = Save.gameFolder(name);
			if (!games.contains(folder))
				return name;
		}
	}

}
