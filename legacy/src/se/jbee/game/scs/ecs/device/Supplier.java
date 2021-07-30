package se.jbee.game.scs.ecs.device;

import se.jbee.game.any.ecs.State;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.scs.ecs.constant.Resource;
import se.jbee.game.scs.ecs.system.SupplySystem;

/**
 * A {@link Supplier} is a  {@link Device} that yields one or more of the game {@link Resource}s.
 */
@EntityType("supplier")
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
	protected void aggregate(State state, SupplySystem system) {
		// TODO Auto-generated method stub

	}
}
