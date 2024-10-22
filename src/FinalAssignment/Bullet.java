package FinalAssignment;

import java.awt.*;

@SuppressWarnings("serial")
public class Bullet extends Circle {
	
	protected Point direction;
	protected double acceleration;
	protected double rotate;
	protected Color color;
	
	public Bullet(Point point, int[] size, int[] coordinates, Color color, double rotate, double acceleration) {
		// Constructor
		super(point, size, coordinates, color);
		this.rotate = rotate;
		this.acceleration = acceleration;
		this.direction = point;
		this.color = color;
	}
	// Paints the bullet on the screen
	public void paint(Graphics brush) {
		brush.setColor(this.color);
		brush.fillOval((int) this.direction.x, (int) this.direction.y, 4, 4);
	}
	
	public void accelerationVector(double acceleration, boolean moveReleased) {
		// Changes the acceleration of the ship
		if (moveReleased && this.acceleration > 0.02) {
			this.acceleration *= 0.90;
		}
		this.acceleration *= 1.1;
	}
	
	public void move() {
		// Determines the movement of the ship and updates instance of x,y
		// Movement = acceleration * sin(rotation) or cos(rotation).
		double radians = Math.toRadians(this.rotate + 30);
		
		double x = this.direction.x, y = this.direction.y;
		
		double changeX = x + (this.acceleration * Math.cos(radians));
		// Average of certain points
		double changeY = y + (this.acceleration * Math.sin(radians));
		this.direction.x = Math.round(changeX * Math.pow(10, 2)) / Math.pow(10, 2);
		this.direction.y = Math.round(changeY * Math.pow(10, 2)) / Math.pow(10, 2);
	}

}
