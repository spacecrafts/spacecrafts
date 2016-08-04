package se.jbee.game.scs;

import se.jbee.game.any.logic.Logic;
import se.jbee.game.scs.logic.Autosave;
import se.jbee.game.scs.logic.Init;
import se.jbee.game.scs.logic.Load;
import se.jbee.game.scs.logic.Loop;
import se.jbee.game.scs.logic.Next;
import se.jbee.game.scs.logic.Save;
import se.jbee.game.scs.logic.Setup;
import se.jbee.game.scs.logic.Turn;
import se.jbee.game.scs.process.Game;

/**
 * The specifics of the SPACECRAFTS game. 
 */
public class Spacecrafts {

	public static void main(String[] args) throws Exception {
		Logic logic = Logic.empty()
				.add(Loop.class) // this must be the first as that is the default loop
				.add(Init.class)
				.add(Load.class)
				.add(Save.class)
				.add(Autosave.class)
				.add(Next.class)
				.add(Setup.class)
				.add(Turn.class);
		new Game(logic).run();
	}
}
