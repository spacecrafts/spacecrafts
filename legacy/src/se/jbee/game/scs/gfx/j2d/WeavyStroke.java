package se.jbee.game.scs.gfx.j2d;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import se.jbee.game.any.state.Rnd;

public final class WeavyStroke implements Stroke {

	private final float detail;
	private final float amplitude;
	private final Rnd rnd;

	public WeavyStroke( float detail, float amplitude, long seed ) {
		this.detail	= detail;
		this.amplitude	= amplitude;
		this.rnd = new Rnd(seed);
	}

	@Override
	public Shape createStrokedShape( Shape shape ) {
		GeneralPath result = new GeneralPath();
		shape = new BasicStroke( 10 ).createStrokedShape( shape );
		PathIterator it = new FlatteningPathIterator( shape.getPathIterator( null ), 1f );
		float points[] = new float[6];
		float moveX = 0, moveY = 0;
		float lastX = 0, lastY = 0;
		float thisX = 0, thisY = 0;
		int type = 0;
		float next = 0;

		while ( !it.isDone() ) {
			type = it.currentSegment( points );
			switch( type ){
			case PathIterator.SEG_MOVETO:
				moveX = lastX = randomlyMoved( points[0] );
				moveY = lastY = randomlyMoved( points[1] );
				result.moveTo( moveX, moveY );
				next = 0;
				break;

			case PathIterator.SEG_CLOSE:
				points[0] = moveX;
				points[1] = moveY;
				// Fall into....

			case PathIterator.SEG_LINETO:
				thisX = randomlyMoved( points[0] );
				thisY = randomlyMoved( points[1] );
				float dx = thisX-lastX;
				float dy = thisY-lastY;
				float distance = (float)Math.sqrt( dx*dx + dy*dy );
				if ( distance >= next ) {
					float r = 1.0f/distance;
					while ( distance >= next ) {
						float x = lastX + next*dx*r;
						float y = lastY + next*dy*r;
						result.lineTo( randomlyMoved( x ), randomlyMoved( y ) );
						next += detail;
					}
				}
				next -= distance;
				lastX = thisX;
				lastY = thisY;
				break;
			}
			it.next();
		}

		return result;
	}
    
    private float randomlyMoved( float x ) {
        return x+rnd.nextFloat()*amplitude*2-1;
    }

}