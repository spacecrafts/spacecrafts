package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Classification;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.comp.ShortRef;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Percent;

@Entity("habitability")
public final class Habitability extends Classification {

	public static final class _Habitability extends ShortRef<Habitability> {
	
		public _Habitability(short serial) {
			super(serial);
		}
		@Override
		public Class<Habitability> entityType() {
			return Habitability.class;
		}
	}
	public int typicalRgb;
	@Percent
	public byte abundance;
	public Atmosphere.Ref atmosphere;
	public Surface.Ref surface;
	public StellarZone.Ref zone;
	public Refs<Material> materials;
	@Percent
	public byte[] materialProbabilities;

}
