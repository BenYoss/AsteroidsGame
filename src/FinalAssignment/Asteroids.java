/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
*/

package FinalAssignment;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
class Asteroids extends Game {
	
  // Instance variables
  private boolean gameOverBlinker;
  private Ship hip;
  private Ship hip2;
  private double windowX;
  private double windowY;
  private Point[] starPoints;
  private List<Bullet> bullets;
  private List<Asteroid> asteroids;
  private int score;
  private int asteroidCount;
  private boolean hip2Enabled = false;
  private double timerTime;
  
  public Asteroids() {
	// Dimensions for the swing window
    super("Asteroids!",800,600);
    this.windowX = 800;
    this.windowY = 600;
    this.score = 0;
    this.bullets = new CopyOnWriteArrayList<Bullet>();
    // Starting points of the ship
    Point[] shipPoints = { 
    		new Point(230.00, 200.00),
    		new Point(280.00, 185.00),
    		new Point(260.00, 140.00),
    		new Point(255.00, 175.00)};
    // Second ship initialization (for easter egg : press "!")
    Point[] ship2Points = { 
    		new Point(170.00, 140.00),
    		new Point(220.00, 125.00),
    		new Point(200.00, 80.00),
    		new Point(195.00, 115.00)};
    // Sets the count for the shapes in game (asteroid count increases incrementally
    int starCount = 25;
    this.asteroidCount = 5;
    // Initializes the shapes (i.e. stars, asteroids)
    this.asteroids = new CopyOnWriteArrayList<Asteroid>();
    this.starPoints = new Point[starCount];
    
    // Blinking animation for game over
    this.gameOverBlinker = true;
    
    // Populates the canvas with random stars
    for (int i = 0; i < starCount; i++) {
    	int randomXCoord = (int) (Math.random() * 600 )+ 1;
		int randomYCoord = (int) (Math.random() * 600 )+ 1;
    	Point newStarPoint = new Point(randomXCoord, randomYCoord);
    	this.starPoints[i] = newStarPoint;
    }
    
    // Ship with dimensions
    this.hip = new Ship(shipPoints, new Point((width / 2) - 25, (height / 2) - 25), 0.3);
    this.hip2 = new Ship(ship2Points, new Point((width / 2) - 25, (height / 2) - 25), 0.3);
    
    this.timerTime = System.currentTimeMillis();
    // Key listener events initialized.
    this.addKeyListener(new KeyAdapter() {
    	
    	// Keeps track of all pressed keys to make multi key tapping capabilities
    	private boolean[] keyPressed = new boolean[256];
    	 
    	public void keyTyped(KeyEvent e) {
    		hip.KeyTyped();
    	}
    	
    	public void keyPressed(KeyEvent e) {
    		keyPressed[e.getKeyCode()] = true;
    		int[] bulletSize = {2, 2};
    		int[] coordinates = {(int) hip.returnX()[1], (int) hip.returnY()[1]};
    		int[] coordinates2 = {(int) hip2.returnX()[1], (int) hip2.returnY()[1]};
    		// Enables the EASTER EGG
    		if (e.getKeyChar() == '!') {
    			hip2Enabled = true;
    		}
    		// If space is pressed, bullets will spawn
    		if (keyPressed[KeyEvent.VK_SPACE]) {
    			if (hip2Enabled) {
    				
    				for (int i = 0; i < (Math.random() * 200) + 1; i++) {
    					
    					bullets.add(new Bullet(
    							new Point(coordinates2[0],
    									coordinates2[1]),
    							bulletSize,
    							coordinates2,      
    							Color.yellow,
    							Math.random() * 360, 10
    							));    					
    				}
    			}
    			bullets.add(new Bullet(
    							new Point(coordinates[0],
    							coordinates[1]),
    							bulletSize,
    							coordinates,
    							Color.cyan,
    							hip.getRotation(), 3
    							));
    		}
    		// Keypresses would occur if multiple movement keys are pressed
    		if (keyPressed[KeyEvent.VK_W] || keyPressed[KeyEvent.VK_A] || keyPressed[KeyEvent.VK_D]){
    			
    			if (hip2Enabled) {
    				
    				hip2.keyPressed(keyPressed);
    			}
    			hip.keyPressed(keyPressed);			
    		}
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		// after W = acceleration for several seconds
    		int keyCode = e.getKeyCode();
    		if (keyCode >= 0 && keyCode < 256) {
                keyPressed[keyCode] = false;
            }
    		try {
    			// Async programming for better glide effect
    			for (int i = 0; i < 20; i ++) {
    				if (hip2Enabled) {
    					hip2.keyReleased(e);
    				}
    				hip.keyReleased(e);
    			}    			
    		} catch (Exception ex) {
    			System.out.println(ex);
    		}
    		
    	}
    });
      // All asteroids are then added as objects to the instance variable
	  for (int i = 0; i < asteroidCount; i ++) {
		  this.asteroids.add(this.createAsteroid(true));
	  }
    
  }
  
  
  // Determines the collision with the border
  public Point border(Point inPoint) {
	  // Deals with the border conditions
	  double x = inPoint.x;
	  double y = inPoint.y;
	  
	  return new Point(this.collision(x, this.windowX), this.collision(y, this.windowY));
  }
  
  // Collision function to run collision logic in border function
  private double collision(double dimension, double windowDimension) {
	if (dimension < 0) {
		return dimension + 10;
	} else if (dimension > windowDimension) {
		return dimension - 10;
	}
  	return 0.0;
  }
  private double manageTime() {
	  double elapsedTime = System.currentTimeMillis() - this.timerTime;
	  double elapsedSeconds = elapsedTime / 1000;
	  double secondsDisplay = elapsedSeconds;
	  return secondsDisplay;
  }
  // Creates new asteroids quickly
  private Asteroid createAsteroid(boolean allowSplit) {
	    
	  	// Generates random vertices for more organic looking asteroids
    	int verticeCount = (int) (Math.random() * 30) + 3;
    	Point[] points = new Point[verticeCount];
    	int avgX = 0, avgY = 0;
    	int randomSpawn = (int) (Math.random() * 3) + 1;
    	int randomHeight = (int) (Math.random() * this.height + 20) + 1;
    	int randomWidth = (int) (Math.random() * this.width + 20) + 1;
    	int asteroidSize = 45;
    	
    	// Rarity uses a switch to determine where an asteroid will spawn on the screen
    	int rarity = (int) (Math.random() * 10) + 1;
    	
    	// Later in the game, asteroids before LARGER
    	if (this.score > 300 && rarity == 1) {
    		asteroidSize += (Math.random() * 80) + 1;
    	}
    	
    	// Determines where an asteroid will spawn on the screen
    	for (int vertice = 0; vertice < verticeCount; vertice++) {
    		int verticeX = (int) (Math.random() * asteroidSize) + 10;
    		int verticeY = (int) (Math.random() * asteroidSize) + 10;
    		switch(randomSpawn) {
    		case 1:
    			break;
    		case 2:
    			verticeY = verticeY + randomHeight;
    			break;
    		case 3:
    			verticeX = verticeX + randomWidth;
    			break;
    		}

    		avgX += verticeX;
    		avgY += verticeY;
    		points[vertice] = new Point(verticeX, verticeY);
    	}
    	// Calculates the center point of the asteroid
    	avgX = avgX / verticeCount;
    	avgY = avgY / verticeCount;
    	int[] size = {20, 20};
    	Asteroid newAsteroid = new Asteroid(points, new Point(avgX, avgY), 0.0, size, ((Math.random() * 0.9) + 0.05) + (this.score / 100) * 0.5);
        // Sets the rotation to be opposite of the player (so it goes toward them)
    	newAsteroid.setRotation(hip.getRotation() + 180);
    	// Parent asteroids have a "split" feature to break into smaller asteroids
        if (allowSplit) {
        	newAsteroid.allowSplit();        	
        }
    	return newAsteroid;
  }
  
  // Collision overloading (for different use cases)
  private void collision(double dimension, double windowDimension, int index, Bullet bullet) {
		if ((dimension < 0 && index >= 0) || (dimension > windowDimension && index >= 0)) {
			this.bullets.remove(index);
		}
  }
  
  @SuppressWarnings("unused")
private boolean collision(double dimension, double windowDimension, Ship ship) {
		if ((dimension < 0) || (dimension > windowDimension)) {
			return true;
		}
		return false;
  }
  
  private void collision(double dimension, double windowDimension, int index, Asteroid asteroid) {
	  if (index >= 0 && index < this.asteroids.size() - 1) {
		  if (asteroid.getPosition().x > this.width + 200 || asteroid.getPosition().x < -200
				  || asteroid.getPosition().y > this.height + 200 || asteroid.getPosition().y < -200) {
			  this.asteroids.remove(index);
		  }
		  if ((dimension < 0) || (dimension > windowDimension)) {
			  this.asteroids.get(index).setRotation(hip.getRotation());;
		  }		  
	  }
  }
  
  private boolean collision(Bullet bullet, int bulletIndex, Asteroid asteroid, int asteroidIndex) {
	  	double[] returnX = asteroid.returnX();
	  	double[] returnY = asteroid.returnY();
	  	 if (bulletIndex >= 0 && bulletIndex < this.bullets.size() - 1) {
			if (bullet.direction.x >= returnX[1] - (returnX[1] / 20) && bullet.direction.x <= returnX[returnX.length - 1] + (returnX[1] / 10) &&
				bullet.direction.y >= returnY[2] - (returnX[1] / 20) && bullet.direction.y <= returnY[returnY.length - 1] + (returnX[1] / 10)) {
			  	this.score += 10;
				this.bullets.remove(bulletIndex);
				Point[] shape = asteroid.getShape();
				Point[] smallerAsteroids = new Point[shape.length];
				
				for (int i = 0; i < smallerAsteroids.length; i++) {
					smallerAsteroids[i] = new Point(shape[i].x / 2, shape[i].y / 2);
				}
				
				int splitCount = (int) (Math.random() * 8) + 1;
				if (asteroid.getSplit()) {
					for(int i = 0; i < splitCount; i++) {
						Asteroid result = this.createAsteroid(false);
						double[] prevX = result.returnX();
						double[] prevY = result.returnY();
						for (int j = 0; j < prevX.length; j++) {
							prevX[j] /= 2;
							prevY[j] /= 2;
						}
						
						result.setX(prevX);
						result.setY(prevY);
						
						result.setPosition(asteroid.getPosition().x, asteroid.getPosition().y);
						this.asteroids.add(result);
					}
				}
				this.asteroids.remove(asteroidIndex);
				return true;
			}
	  	 }
		return false;
  }
  
  private boolean collision(Ship ship, Asteroid asteroid, int asteroidIndex) {
	  	double[] returnX = asteroid.returnX();
	  	double[] returnY = asteroid.returnY();
		if (ship.returnX()[1] >= returnX[1] - 20 && ship.returnX()[1] <= returnX[returnX.length - 1] + 20 &&
			ship.returnY()[1] >= returnY[2] - 20 && ship.returnY()[1] <= returnY[returnY.length - 1] + 20) {
			return true;
		}
		return false;
  }
  
  // Paints the gameplay on the canvas every 10th of a second
  public void paint(Graphics brush) {
	 
	// Render background
    brush.setColor(Color.black);
    brush.fillRect(0,0,width,height);
    
    // Render stars
    for (int i = 0; i < this.starPoints.length; i++) {
    	int[] starSize = {5, 5};
    	int[] starCoords = { (int) this.starPoints[i].x, (int) this.starPoints[i].y};
    	Star star = new Star(this.starPoints[i], starSize, starCoords, Color.white);
    	star.paint(brush);
    }
    
    // Renders new asteroids if the number is less than the count
    // Additionally increases the count later in the game
    try {
    	if (asteroids.size() < this.asteroidCount) {
    		if (this.score > 200) {
    			this.asteroids.add(this.createAsteroid(true));
    			this.asteroidCount += 1;
    		}
    		this.asteroids.add(this.createAsteroid(true));
    	}
    } catch (Exception e) {
    	System.out.println(e);
    }
    
    // If an asteroid hits the ship, game over
    boolean isGameOver = false;
    for (Asteroid asteroid : asteroids) {
    	if (collision(hip, asteroid, asteroids.indexOf(asteroid))) {
    		isGameOver = true;
    	}
    }
    // If the ship gets too close to the border, it's movement will be locked
    if (hip.getCenter().x > this.windowX - 5 || hip.getCenter().x < 5 || hip.getCenter().y > this.windowY - 5 || hip.getCenter().y < 5) {
    	hip.lockMovement();
    } else {
    	hip.unlockMovement();
    }
    
    // Gameplay features
    if (!isGameOver) {
    	
    	// Renders asteroids
    	for (Asteroid asteroid: asteroids) {
    		asteroid.paint(brush);
    		asteroid.move();
    		// If ship hits asteroid, clear board and render gameplay message
    		if (collision(hip, asteroid, asteroids.indexOf(asteroid))) {
    			brush.dispose();
    			brush.setColor(Color.red);
    			Font font = new Font("Arial", Font.BOLD, 20);
    			brush.setFont(font);
    			brush.drawString("GAME OVER", 400, 400);
    			isGameOver = true;
    		}
    		if (asteroids.indexOf(asteroid) >= 0) {
    			Point centerPoint = asteroid.getCenter();
    			collision(centerPoint.x, windowX + 100, asteroids.indexOf(asteroid), asteroid);
    			collision(centerPoint.y, windowY + 100, asteroids.indexOf(asteroid), asteroid);    		
    		}
    	}
    	
    	// Renders bullets
    	for (Bullet bullet : bullets) {
    		bullet.paint(brush);
    		bullet.move();
    		if (bullets.indexOf(bullet) >= 0) {
    			collision(bullet.direction.x, windowX, bullets.indexOf(bullet), bullet);
    			collision(bullet.direction.y, windowY, bullets.indexOf(bullet), bullet);    		
    		}
    	}
    	
    	// Renders bullet/asteroid collision
    	for (Bullet bullet : bullets) {
    		for (Asteroid asteroid : asteroids) {
    			collision(bullet, bullets.indexOf(bullet), asteroid, asteroids.indexOf(asteroid));
    		}
    	}
    	
    	// Renders ship
    	brush.setColor(Color.blue);
    	hip.paint(brush);
    	hip.accelerationVector(hip.getAcceleration(), true);
    	hip.move();
    	
    	// Renders easter egg (press "!" in game)
    	if (this.hip2Enabled) {
    		brush.setColor(Color.magenta);
    		hip2.paint(brush);
    		hip2.accelerationVector(hip2.getAcceleration(), true);
    		hip2.move();    		
    	}
    	// Renders score and timer fonts
    	Font scoreFont = new Font("Arial", Font.PLAIN, 20);
    	brush.setColor(Color.white);
    	brush.setFont(scoreFont);
    	brush.drawString("Score: " + this.score, 20, 20);
    	brush.drawString("Time: " + this.manageTime(), 140, 20);
    }
    
    // Enables game over functions and render
    if (isGameOver) {
    	try {
    		if (gameOverBlinker) {
    			brush.setColor(Color.red);
    			Font goFont = new Font("Arial", Font.BOLD, 50);
    			Font goScoreFont = new Font("Arial", Font.BOLD, 25);
    			brush.setFont(goFont);
    			brush.drawString("GAME OVER", 250, 250);
    			brush.setColor(Color.white);
    			brush.setFont(goScoreFont);
    			brush.drawString("Final Score: " + this.score, 270, 280);
    			this.gameOverBlinker = false;
    		} else {
    			this.gameOverBlinker = true;
    		}
    		Thread.sleep(1000);
    	} catch(Exception e) {
    		
    	}
    }
    
    // Repaints the canvas (VERY IMPORTANT)
    repaint();    	
  }

  
  public static void main (String[] args) {
    new Asteroids();
  }
}