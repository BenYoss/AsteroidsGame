package FinalAssignment;

import java.awt.Color;

@SuppressWarnings("serial")
public class Circle extends Shape {
	private int[] coordinates;
	
	public Circle(Point point, int[] size, int[] coordinates, Color color) {
		super(point, size);
		this.coordinates = coordinates;
	}
	
	@Override
	public boolean contain(Point inpoint) {
		// Determines if the point is contained within the circle
		for (Point p: points) {
			if (p.toString().equals(inpoint.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public int[] getCoordinates() {
		// Gets the necessary coordinates for the Circle
		return this.coordinates;
	}

	@Override
	public void getParameter() {
		// TODO Auto-generated method stub
		
	}
}
