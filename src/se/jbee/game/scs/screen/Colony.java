package se.jbee.game.scs.screen;


import static java.awt.event.KeyEvent.VK_ESCAPE;
import static se.jbee.game.any.state.Change.set;
import static se.jbee.game.scs.gfx.GfxObjs.background;
import static se.jbee.game.scs.gfx.GfxObjs.planet;
import se.jbee.game.any.gfx.Dimension;
import se.jbee.game.any.gfx.Stage;
import se.jbee.game.any.screen.Screen;
import se.jbee.game.any.screen.ScreenNo;
import se.jbee.game.any.state.Change;
import se.jbee.game.any.state.Entity;
import se.jbee.game.any.state.State;
import se.jbee.game.scs.gfx.Gfx;
import se.jbee.game.scs.state.GameComponent;

@ScreenNo(GameScreen.SCREEN_COLONY)
public class Colony implements Screen, GameComponent, Gfx, GameScreen {

	@Override
	public void show(State user, State game, Dimension screen, Stage stage) {
		Entity gamE = game.single(GAME);
		int colonyID = gamE.num(BASE_ENTITY);
		if (colonyID == 0) {
			return;
		}
		int gID = gamE.id();
		Entity colony = game.entity(colonyID);
		Change[] backToSolarSystem = {
				set(gID, SCREEN, SCREEN_SOLAR_SYSTEM),
				set(gID, BASE_ENTITY, colony.num(STAR)) };
		stage.onKey(VK_ESCAPE, backToSolarSystem);
		
		int w = screen.width;
		int h = screen.height;
		stage.atFront(background(0,0,w, h, BG_SPACE, colony.list(SEED)));
		int dia = 6 + colony.num(SIZE) * 6;
		stage.atFront(planet(100, 100-dia/2, dia, 0, colony.num(RGB)));
	}

}
