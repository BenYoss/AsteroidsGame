package FinalAssignment;

import java.awt.*;

@SuppressWarnings("serial")
public class Star extends Circle {
	
	public Star(Point point, int[] size, int[] coordinates, Color color) {
		super(point, size, coordinates, color);
		// Random rotation will be generated using the math object
	}
	
	public void paint(Graphics brush) {
		// The paint tool will be used for the star
		brush.setColor(Color.white);
		brush.drawOval((int) points[0].x, (int) points[0].y, this.size[0], this.size[1]);
	}

	@Override
	public void getParameter() {
		// TODO Auto-generated method stub
	}
}
