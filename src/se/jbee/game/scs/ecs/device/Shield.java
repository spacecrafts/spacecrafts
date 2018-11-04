package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.system.DefenceSystem;

@Entity("shield")
public final class Shield extends Device<DefenceSystem, DefenceSystem.Ref> {

	public static final class Ref extends Device.Ref<Shield> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Shield> entityType() {
			return Shield.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, DefenceSystem system) {
		// TODO Auto-generated method stub

	}

	// benefit of an cloaking field is that a cloaked fleet does not cause thread evaluation
}
