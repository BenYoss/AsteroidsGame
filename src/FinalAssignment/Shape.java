package FinalAssignment;

import java.awt.*;

@SuppressWarnings("serial")
public abstract class Shape extends Canvas {
	protected Point[] points;
	protected int[] size;
	
	public Shape(Point point, int[] size) {
		this.points = new Point[1];
		this.points[0] = point;
		this.size = size;
	}
	
	public boolean contain(Point point) {
		// Determines if the shape contains a point
		for (Point p: this.points) {
			if (p.toString().equals(point.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean intersect(Shape shape) {
		// Gathers the information necessary to perform an intersect with the shape
		for (Point point : shape.points) {
			if (this.points.toString().contains(point.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public abstract void getParameter(); // Gets parameters
}
