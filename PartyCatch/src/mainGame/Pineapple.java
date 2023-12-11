/***********************************
 * Represents the Watermelon object in the game.
 * This is a falling object caught by the player with the basket.
 * 
 * Pineapple to Watermelon is a last minute change since it fits better in the game.
 ************************************/

package mainGame;


import javafx.scene.image.Image;
public class Pineapple extends FallingObject {
	
	static int PINEAPPLE_SPEED = 7; //Static variable for the speed of the falling watermelon
	private final static Image PINEAPPLE_IMAGE = new Image("images/watermelonCatch.png",150,160,false,false); //Image object representing the watermelon graphic
	
	// Static points gained or lost for the watermelon
	static int GAIN = 20;
	private final static int LOSE = 10;

	// Constructor - Initializes the watermelon with a given position and the watermelon image
	Pineapple(double xPos, double yPos) {
		super(xPos, yPos, PINEAPPLE_IMAGE);
		this.speed = Pineapple.PINEAPPLE_SPEED;
	}
	
	//Moves the watermelon downward on the screen
	void move(){
		this.yPos += Pineapple.PINEAPPLE_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

	//Checks if the watermelon collides with the basket and updates the basket's score or applies a penalty accordingly
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println(Pineapple.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Pineapple.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				if (basket.getScore() != 0) {
				System.out.println("You didn't catch the watermelon, -"+Pineapple.LOSE); 
				basket.loseScore(Pineapple.LOSE);
				}
				this.vanish();
			}
		}
	}

	// Check whether the Watermelon is a power-up (not a power-up)
	@Override
	boolean isPowerUp() {
		return false;
	}



}
