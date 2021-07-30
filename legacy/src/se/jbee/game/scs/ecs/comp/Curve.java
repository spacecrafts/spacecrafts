package se.jbee.game.scs.ecs.comp;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Short.parseShort;

import se.jbee.game.any.ecs.ComponentType;
import se.jbee.game.any.ecs.meta.Component;
import se.jbee.game.any.ecs.meta.NonNegative;

public final class Curve implements ComponentType {

	/**
	 * The independent axis, usually the size or round (time)
	 */
	@Component(0)
	@NonNegative
	public final short[] range;
	/**
	 * The dependent axis
	 */
	@Component(1)
	public final short[] output;

	public Curve(short[] range, short[] output) {
		this.range = range;
		this.output = output;
	}

	public Curve(String curve) {
		String curveGroups = curve.substring(curve.indexOf('{')+1, curve.indexOf('}'));
		if (curveGroups.isEmpty() || curveGroups.trim().isEmpty()) {
			this.range = new short[0];
			this.output = new short[0];
		} else {
			String[] groups = curveGroups.split("\\s+|,");
			this.range = new short[groups.length];
			this.output = new short[groups.length];
			for (int i = 0; i < groups.length; i++) {
				String group = groups[i];
				int colon = group.indexOf(':');
				range[i] = parseShort(group.substring(0, colon));
				output[i] = parseShort(group.substring(colon+1));
			}
		}
	}

	public int at(int n) {
		int sum = 0;
		int nLeft = n;
		for (int i = 0; i < range.length-1; i++) {
			int nGroup = min(nLeft, range[i + 1] - range[i]);
			sum += nGroup * output[i];
			nLeft -= nGroup;
			if (nLeft == 0)
				return sum;
		}
		if (nLeft > 0)
			sum += nLeft * output[output.length - 1];
		return max(0, sum);
	}

	public int points() {
		return range.length;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('{');
		for (int i = 0; i < range.length; i++) {
			if (i > 0) b.append(' ');
			b.append(range[i]).append(':').append(output[i]);
		}
		b.append('}');
		return b.toString();
	}
}
