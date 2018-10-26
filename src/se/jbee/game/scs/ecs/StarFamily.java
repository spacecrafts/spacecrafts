package se.jbee.game.scs.ecs;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.meta.Entity;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Range;

@Entity("starclass")
public final class StarFamily extends Spectrum {

	public static final class Ref extends ByteRef<StarFamily> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<StarFamily> entityType() {
			return StarFamily.class;
		}
	}
	public RGB hue;
	@Range(min = 1, max = 30)
	public int size;
	@Percent
	public byte abundance;
}
