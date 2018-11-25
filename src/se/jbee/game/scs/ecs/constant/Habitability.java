package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Classification;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.comp.Refs;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Percent;

@EntityType("&habitability")
public final class Habitability extends Classification {

	public static final class Ref extends ByteRef<Habitability> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<Habitability> entityType() {
			return Habitability.class;
		}
	}
	public RGB typicalColor;
	@Percent
	public byte abundance;
	public Atmosphere.Ref atmosphere;
	public Surface.Ref surface;
	public StellarZone.Ref zone;
	public Refs<Material> materials;
	@Percent
	public byte[] materialProbabilities;

}
