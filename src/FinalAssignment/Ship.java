package FinalAssignment;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.lang.Math;

@SuppressWarnings("serial")
public class Ship extends Polygon implements KeyListener {
	// Acceleration in terms of whole number speed
	private double acceleration;
	private double[] x;
	private double[] y;
	private double rotate;
	private boolean lockMove;
	private Point[] center;
	// Determines the pressed key by the player
	private char pressedKey;
	
	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		this.rotation = inRotation;
		this.rotate = 0.0;
		this.acceleration = 0.0001;
		this.lockMove = false;
		this.shape = inShape;
		this.x = new double[inShape.length];
		this.y = new double[inShape.length];
		this.center = getPoints();
		// Sets positions for ship by shape
		for (int i = 0; i < inShape.length; i++) {
			this.x[i] = (inShape[i].x + position.x);
			this.y[i] = (inShape[i].y + position.y);
		}
	}
	
	@Override
	public void paint(Graphics brush) {
		// Paints ship
		int[] intX = new int[this.x.length], intY = new int[this.y.length];
		for(int i = 0; i < this.x.length; i++) {
			intX[i] = (int) this.x[i];
			intY[i] = (int) this.y[i];
		}
		
		brush.fillPolygon(intX, intY, this.x.length);
		brush.setColor(Color.black);
	}
	
	public void move() {
		// Determines the movement of the ship and updates instance of x,y
		// Movement = acceleration * sin(rotation) or cos(rotation).
		double moveAcceleration = this.acceleration;
		if (this.lockMove) {
			moveAcceleration -= this.acceleration * 3;
		}
		for (int i = 0; i < this.x.length; i++) {
			double radians = Math.toRadians(this.rotate+ 30);
			double changeX = this.x[i] + (moveAcceleration * Math.cos(radians));
			// Average of certain points
			double changeY = this.y[i] + (moveAcceleration * Math.sin(radians));
			
			this.x[i] = Math.round(changeX * Math.pow(10, 2)) / Math.pow(10, 2);
			this.y[i] = Math.round(changeY * Math.pow(10, 2)) / Math.pow(10, 2);
		}
		this.center[1] = new Point (this.x[1] + 40, this.y[1] + 10);
	}
	
	public void accelerationVector(double acceleration, boolean moveReleased) {
		// Changes the acceleration of the ship
		if (moveReleased && this.acceleration > 0.02) {
			this.acceleration *= 0.8;
		}
		this.acceleration *= 1.2;
	}
	
	// Stub methods necessary for class
	public void keyPressed(boolean[] keyPressed) {
		// Keypressed events
		
		// W = forward
		if (keyPressed[KeyEvent.VK_W] && this.acceleration < 2.0) {
			this.accelerationVector(this.acceleration, false);
		}
		// A = rotate clockwise
		if (keyPressed[KeyEvent.VK_A]) {
			this.rotation(-10);
		}
			// D = rotate counterclockwise
			
			if (keyPressed[KeyEvent.VK_D]) {
			this.rotation(10);
		}
	}

	public void rotation(int rotation) {
		// TODO: Rotation of the ship
		this.rotate += rotation;
		for (int i = 0; i < this.x.length; i++) {
			
			this.rotate = (this.rotate % (360));

			if (this.rotate < 0) {
				
				this.rotate += (360.0);
			}
			AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(rotation), this.center[1].x - 40, this.center[1].y - 10);
			Point2D result = transform.transform(new Point2D.Double(this.x[i], this.y[i]), new Point2D.Double(this.x[i], this.y[i]));
			this.x[i] = result.getX();
			this.y[i] = result.getY();
		}
	}

	public void KeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		this.accelerationVector(this.acceleration, true);
	}
	public void KeyTyped() {
		// TODO Auto-generated method stub
		
	}

	// Getters and setters of each instance
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public char getPressedKey() {
		return pressedKey;
	}
	public double getRotation() {
		return rotate;
	}
	public Point getCenter() {
		return this.center[1];
	}
	
	public void lockMovement() {
		this.lockMove = true;
	}
	
	public void unlockMovement() {
		this.lockMove = false;
	}
	
	public void setPressedKey(char pressedKey) {
		this.pressedKey = pressedKey;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public double[] returnX() {
		return this.x;
	}
	
	public void setX(double[] newX) {
		this.x = newX;
	}
	
	public void setY(double[] newY) {
		this.y = newY;
	}
	
	public double[] returnY() {
		return this.y;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
