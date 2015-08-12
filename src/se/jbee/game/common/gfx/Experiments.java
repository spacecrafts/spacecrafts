package se.jbee.game.common.gfx;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * a container for code I play around with 
 */
public class Experiments {

	static void arcs(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setPaint(new Color(153,153,0, 100));
		for (int i = 0; i < 200; i++) {
			g.drawArc(500, 325+i, 500, 90, 180, 180);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
	}
	
	static void glow(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		for (int i = 0; i < 200; i++) {
			g.setPaint(new Color(153,153,0, i));
			g.drawArc(300, 325+i, 500, 90, 180, 180);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
	}
	
	static void test(Graphics2D g) {
		g.setColor(new Color(170, 221, 255));
		g.drawArc(0, 0, 300, 100, 90, 180);
		
		for (int i = 0; i < 1000; i++) {
			//g.drawArc(-150, i, 300, 100, 90, -180);
			//g.drawOval(i%800, i, 20, 20);
			//g.drawLine(0, i, 100, 100+i);
			//g.drawPolygon(new int[] {0, 10, 20, 30 },  new int[] {0, 10, 20, 30 },  3);
		}
		int[][] a={{50,0},{100,50},{50,100},{0,50}};
		GeneralPath p = getRoundedGeneralPath(Arrays.asList(a));
		g.setColor(Color.red);
		g.draw(p);

	}
	

	public static GeneralPath getRoundedGeneralPath(Polygon polygon) {
		List<int[]> l = new ArrayList<int[]>();
		for(int i=0; i < polygon.npoints; i++){
			l.add(new int[]{polygon.xpoints[i],polygon.ypoints[i]});
		}
		return getRoundedGeneralPath(l);
	}

	public static GeneralPath getRoundedGeneralPath(List<int[]> l) {
		List<Point> list=new ArrayList<Point>();
		for (int[] point : l){
			list.add(new Point(point[0],point[1]));
		}
		return getRoundedGeneralPathFromPoints(list);
	}
	public static GeneralPath getRoundedGeneralPathFromPoints(List<Point> l) {
		l.add(l.get(0));
		l.add(l.get(1));
		GeneralPath p = new GeneralPath();
		p.moveTo(l.get(0).x,l.get(0).y);
		for(int pointIndex=1; pointIndex<l.size()-1;pointIndex++){
			Point p1=l.get(pointIndex-1);
			Point p2=l.get(pointIndex);
			Point p3=l.get(pointIndex+1);
			Point mPoint = calculatePoint(p1, p2);
			p.lineTo(mPoint.x, mPoint.y);
			mPoint = calculatePoint(p3, p2);
			p.curveTo(p2.x, p2.y, p2.x, p2.y, mPoint.x, mPoint.y);
		}
		return p;
	}
	private static Point calculatePoint(Point p1, Point p2) {
		float arcSize=25;
		double d1=Math.sqrt(Math.pow(p1.x-p2.x, 2)+Math.pow(p1.y-p2.y, 2));
		double per=arcSize/d1;
		double d_x=(p1.x-p2.x)*per;
		double d_y=(p1.y-p2.y)*per;
		int xx=(int)(p2.x+d_x);
		int yy=(int)(p2.y+d_y);
		return new Point(xx,yy);
	}		

	
	static void planet2(Graphics2D g) {
		// dark edges for 3d effect
		Paint pg = new RadialGradientPaint(575,
				500, 250,
                new float[] { .6f, 1f },
                new Color[] { new Color(153,99,63, 0), new Color(51,33,21, 150) });
		g.setPaint(pg);
		g.fillOval(300, 300, 500, 500);
		
		// dark edges for 3d effect
		pg = new RadialGradientPaint(550,
				550, 250,
                new float[] { .65f, .85f, .98f, 1f },
                new Color[] { new Color(0,0,0, 0), new Color(0,0,0, 50), new Color(0,0,0, 100), new Color(0,0,0, 200) });
		g.setPaint(pg);
		g.fillOval(300, 300, 500, 500);			
		
		// this gives different colors in vertical direction. 
		pg = new RadialGradientPaint(new Rectangle2D.Float( 475, 0, 150, 100), new float[]{0.6f,  0.75f, 0.8f, 0.9f}, new Color[] { new Color(153,99,63, 20), new Color(255,255,255, 20), new Color(51,33,21, 20), new Color(153,99,63, 20) }, CycleMethod.REPEAT );
		g.setPaint(pg);
		g.fillOval(300, 300, 500, 500);
		
		// TODO add a texture paint to give some roughness to the surface
	}
	
	static void planet(Graphics2D g2, int x, int y, int w, int h) {
		// Adds shadows at the top
        Paint p;
        p = new GradientPaint(x, y, new Color(0.0f, 0.0f, 0.0f, 0.4f),
                x, y+h, new Color(0.0f, 0.0f, 0.0f, 0.0f));
        g2.setPaint(p);
        g2.fillOval(x, y, w - 1, h - 1);
        
        // Adds highlights at the bottom 
        p = new GradientPaint(x, y, new Color(1.0f, 1.0f, 1.0f, 0.0f),
                x, y+h, new Color(1.0f, 1.0f, 1.0f, 0.4f));
        g2.setPaint(p);
        g2.fillOval(x, y, w - 1, h - 1);
        
        // Creates dark edges for 3D effect
        p = new RadialGradientPaint(new Point2D.Double(x + (w / 2.0),
                y+(h / 2.0)), w / 2.0f,
                new float[] { 0.0f, 1.0f },
                new Color[] { new Color(153,99,63, 127),
                    new Color(0.0f, 0.0f, 0.0f, 0.8f) });
        g2.setPaint(p);
        g2.fillOval(x, y, w - 1, h - 1);
        
        // Adds oval inner highlight at the bottom
        p = new RadialGradientPaint(new Point2D.Double((x+ w) / 2.0,
                (y + h) * 1.5), w / 2.3f,
                new Point2D.Double(w / 2.0, h * 1.75 + 6),
                new float[] { 0.0f, 0.8f },
                new Color[] { new Color(64, 142, 203, 255),
                    new Color(64, 142, 203, 0) },
                RadialGradientPaint.CycleMethod.NO_CYCLE,
                RadialGradientPaint.ColorSpaceType.SRGB,
                AffineTransform.getScaleInstance(1.0, 0.5));
        g2.setPaint(p);
        g2.fillOval(x, y, w - 1, h - 1);
        
        // Adds oval specular highlight at the top left
        p = new RadialGradientPaint(new Point2D.Double(x+w,
                y/2+(h/2)), w / 1.4f,
                new Point2D.Double(45.0, 25.0),
                new float[] { 0.0f, 0.5f },
                new Color[] { new Color(1.0f, 1.0f, 1.0f, 0.4f),
                    new Color(1.0f, 1.0f, 1.0f, 0.0f) },
                RadialGradientPaint.CycleMethod.NO_CYCLE);
        g2.setPaint(p);
        g2.fillOval(x, y, w - 1, h - 1);		
	}
}
