/***********************************
 * Represents the "Double Score" power-up in the game.
 * Activates double score for a limited duration when caught by the player.
 ************************************/

package mainGame;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


class DoubleScore extends FallingObject{
	// Timer for the double score activation
	private DoubleScoreTimer timer;
	// Speed at which the DoubleScore power-up falls
	private final static int SPEED = 7;
	// Image representing the DoubleScore power-up
	final static Image IMAGE = new Image("images/doubleScore.png",120,120,false,false);
	private final static ImageView image = new ImageView(IMAGE); // ImageView for the DoubleScore image
	// Track whether the DoubleScore is currently activated
	private boolean isActivated;

	// Constructor for the DoubleScore class
	DoubleScore(double xPos, double yPos) {
		super(xPos, yPos, IMAGE);
		this.speed = DoubleScore.SPEED;
		this.isActivated = false;
	}

	// Activates the DoubleScore power-up
	void activate(){
		if(!this.isActivated){
			this.isActivated = true;
			this.timer = new DoubleScoreTimer(this);
			this.timer.start();
			System.out.println("DOUBLE SCORE ACTIVATED FOR 10 SECONDS!");
			// Double the gain points for Banana, Apple, and Pineapple(Watermelon) during activation
			Banana.GAIN += Banana.GAIN;
			Apple.GAIN += Apple.GAIN;
			Pineapple.GAIN += Pineapple.GAIN;
		}else 
			this.timer.refresh(); // Refresh the activation timer if the DoubleScore is already activated
	}

	// Deactivates the DoubleScore power-up
	void deactivate(){
		this.isActivated = false;
		System.out.println("DOUBLE SCORE DE-ACTIVATED!");
		// Restore the gain points for Banana, Apple, and Pineapple(Watermelon) to their original values
		Banana.GAIN /= 2;
		Apple.GAIN /= 2;
		Pineapple.GAIN /= 2;	
	}
	
	// Checks for collisions with the Basket and activates the power-up
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			activate();
			this.vanish();
		}
	}

	// Indicates whether the DoubleScore is a power-up (It is a power-up)
	@Override
	boolean isPowerUp() {
		return true;
	}

}
