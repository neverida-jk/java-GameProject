/***********************************
 * Represents the Apple object in the game.
 * This is a falling object caught by the player with the basket.
 ************************************/

package mainGame;

import javafx.scene.image.Image;
public class Apple extends FallingObject {
	
	// Constants for Apple properties
	static int APPLE_SPEED = 5;
	private final static Image APPLE_IMAGE = new Image("images/appleCatch.png",190,130,false,false);
	static int GAIN = 10;
	private final static int LOSE = 5;
	
	// Constructor for the Apple class
	Apple(double xPos, double yPos) {
		super(xPos, yPos, APPLE_IMAGE);
		this.speed = Apple.APPLE_SPEED;
	}
	
	// Move the Apple object downward on the screen
	void move(){
		this.yPos += Apple.APPLE_SPEED;
		// Check if the Apple has passed through the bottom of the scene
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// If the Apple passes through the bottom, set visible to false
			this.vanish();
		}
	}
	
	// Check for collisions with the Basket
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			// If the Apple collides with the Basket, add points to the score
			System.out.println(Apple.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Apple.GAIN);
		}else {
			// If the Apple passes through the bottom without being caught
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				// Check if the player has any points, then deduct points if applicable
				if (basket.getScore() != 0) {
				System.out.println("You didn't catch the apple, -"+Apple.LOSE); 
				basket.loseScore(Apple.LOSE);
				}
				this.vanish();
			}
		}
	}

	// Check whether the Apple is a power-up (not a power-up)
	@Override
	boolean isPowerUp() {
		return false;
		
	}



}
