package se.jbee.game.uni.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
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

}
