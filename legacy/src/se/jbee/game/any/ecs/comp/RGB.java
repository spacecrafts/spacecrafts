package se.jbee.game.any.ecs.comp;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;

public final class RGB implements ComponentType {

	@Component(0)
	public final int argb;

	public RGB(int argb) {
		this.argb = argb;
	}

    public int red() {
        return (argb >> 16) & 0xFF;
    }

    public int green() {
        return (argb >> 8) & 0xFF;
    }

    public int blue() {
        return (argb >> 0) & 0xFF;
    }

    public int alpha() {
        return (argb >> 24) & 0xff;
    }

    @Override
    public String toString() {
    	return Integer.toHexString(argb);
    }
}
