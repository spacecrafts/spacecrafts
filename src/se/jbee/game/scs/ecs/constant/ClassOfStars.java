package se.jbee.game.scs.ecs.constant;

import se.jbee.game.any.ecs.Spectrum;
import se.jbee.game.any.ecs.comp.ByteRef;
import se.jbee.game.any.ecs.comp.RGB;
import se.jbee.game.any.ecs.meta.EntityType;
import se.jbee.game.any.ecs.meta.Percent;
import se.jbee.game.any.ecs.meta.Range;

@EntityType("&startype")
public final class ClassOfStars extends Spectrum {

	public static final class Ref extends ByteRef<ClassOfStars> {

		public Ref(byte serial) {
			super(serial);
		}
		@Override
		public Class<ClassOfStars> entityType() {
			return ClassOfStars.class;
		}
	}
	public RGB hue;
	@Range(min = 1, max = 30)
	public byte size;
	@Percent
	public byte abundance;

}
