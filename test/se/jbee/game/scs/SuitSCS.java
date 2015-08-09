package se.jbee.game.scs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.jbee.game.state.TestEntity;
import se.jbee.game.state.TestState;

@RunWith(Suite.class)
@SuiteClasses({ TestEntity.class, TestState.class, TestGame.class })
public class SuitSCS {

}
