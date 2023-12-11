/***********************************
 * Represents the Banana object in the game.
 * This is a falling object caught by the player with the basket.
 ************************************/

package mainGame;


import javafx.scene.image.Image;

class Banana extends FallingObject {
	static int BANANA_SPEED = 3; // Speed at which the Banana falls.
	private final static Image BANANA_IMAGE = new Image("images/bananaCatch.png",150,100,false,false); // Image representing the Banana.
	static int GAIN = 5; // Points gained when catching a Banana.
	private final static int LOSE = 3; // Points deducted when a Banana reaches the bottom without being caught.

	// Constructor for the Banana class
	Banana(double xPos, double yPos) {
		super(xPos, yPos, BANANA_IMAGE);
		this.speed = Banana.BANANA_SPEED;
	}

	// Checks for collisions with the Basket and updates the score
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println(Banana.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Banana.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				// Checks if the player has any points, then deducts points if applicable
				if (basket.getScore() != 0) {
				System.out.println("You didn't catch the banana, -"+Banana.LOSE); 
				basket.loseScore(Banana.LOSE);
				}
				this.vanish();
			}
		}
	}

	// Check whether the Banana is a power-up (not a power-up)
	@Override
	boolean isPowerUp() {

		return false;
	}



}
