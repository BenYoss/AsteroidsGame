package FinalAssignment;


import java.awt.*;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class Asteroid extends Ship {
	
	// Instance variables
	private double acceleration;
	private double[] x;
	private double[] y;
	private Point position;
	private double rotate;
	private Point[] center;
	private Point[] shape;
	private boolean split;

	public Asteroid(Point[] inShape, Point inPosition, double inRotation, int[] size, double acceleration) {
		super(inShape, inPosition, inRotation);
		this.rotation = inRotation;
		this.rotate = 0.0;
		this.acceleration = acceleration;
		this.shape = inShape;
		this.center = getPoints();
		this.x = new double[inShape.length];
		this.y = new double[inShape.length];
		this.position = inPosition;
		
		// Populates the positions of x and y from shape
		for (int i = 0; i < inShape.length; i++) {
			
			this.x[i] = (inShape[i].x + position.x);
			this.y[i] = (inShape[i].y + position.y);
		}
	}
	// Paints the asteroid
	@Override
	public void paint(Graphics brush) {
		int[] intX = new int[this.x.length], intY = new int[this.y.length];
		// Calculate dimensions
		for(int i = 0; i < this.x.length; i++) {
			
			intX[i] = (int) this.x[i];
			intY[i] = (int) this.y[i];
		}
		
		brush.setColor(Color.lightGray);
		brush.fillPolygon(intX, intY, this.x.length);
		brush.setColor(Color.darkGray);
		brush.drawPolygon(intX, intY, this.x.length);
	}
	
	// Moves the asteroid around
	@Override
	public void move() {
		// Determines the movement of the ship and updates instance of x,y
		// Movement = acceleration * sin(rotation) or cos(rotation).
		for (int i = 0; i < this.x.length; i++) {
			
			double radians = Math.toRadians(this.rotate+ 30);
			double changeX = this.x[i] + (this.acceleration * Math.cos(radians));
			
			// Average of certain points
			double changeY = this.y[i] + (this.acceleration * Math.sin(radians));
			this.x[i] = Math.round(changeX * Math.pow(10, 2)) / Math.pow(10, 2);
			this.y[i] = Math.round(changeY * Math.pow(10, 2)) / Math.pow(10, 2);
		}
		this.center[1] = new Point (this.x[1], this.y[1]);
		this.position.x = this.x[1];
		this.position.y = this.y[1];
	}
	
	// Getters and setters for each instance
	public double getRotation() {
		return this.rotate;
	}

	public void setRotation(double rotation) {
		this.rotate = rotation;
	}

	public Point getCenter() {
		return this.position;
	}

	public void setX(double[] x) {
		this.x = x;
	}
	public Point[] getShape() {
		return this.shape;
	}
	public Point getPosition() {
		return this.position;
	}
	public double[] returnX() {
		return this.x;
	}
	public double[] returnY() {
		return this.y;
	}
	// Allows asteroid to create smaller asteroids if hit
	public void allowSplit() {
		this.split = true;
	}
	
	public boolean getSplit() {
		return this.split;
	}

	public void setY(double[] y) {
		this.y = y;
	}
	
	public void setPosition(double locationX, double locationY) {
		// Sets a new position of the asteroid on the screen while retaining shape
		AffineTransform transform = AffineTransform.getTranslateInstance(locationX, locationY);
		transform.setToIdentity();
		double[] transformedPoints = new double[this.x.length];
		
		for (int i = 0; i < this.x.length; i++) {
			
			if (i - 1 >= 0 && i + 1 < this.x.length) {

				double[] point = {this.x[i], 0};
				double[] pointY = {this.y[i], 0};
				transform.transform(point, 0, point, 0, 1);
				transform.setToIdentity();
				transform.transform(pointY, 0, pointY, 0, 1);
				transform.setToIdentity();
				transformedPoints[i] = point[0];			
				this.x[i] = point[0];
				this.y[i] = pointY[0];
			}
		}
		
	}
}
