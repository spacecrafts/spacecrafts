package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.scs.ecs.Frame;
import se.jbee.game.scs.ecs.constant.Resource;
import se.jbee.game.scs.ecs.system.SupplySystem;

/**
 * A {@link Supplier} is a  {@link Device} that yields one or more of the game {@link Resource}s.
 */
@Entity("supplier")
public final class Supplier extends Device<SupplySystem, SupplySystem.Ref> {

	public static final class Ref extends Device.Ref<Supplier> {

		public Ref(int serial) {
			super(serial);
		}
		@Override
		public Class<Supplier> entityType() {
			return Supplier.class;
		}
	}

	@Override
	protected void updateAggregated(State state, Frame<?> frame, SupplySystem system) {
		// TODO Auto-generated method stub

	}
}
