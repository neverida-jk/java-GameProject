package mainGame;

import javafx.scene.image.Image;

/*
 * Manages the player's hearts system.
 * Keeps track of the number of hearts.
 * Handles actions related to hearts, such as losing a heart.
 * Provides methods for checking the current number of hearts.
 * May include logic for game over conditions when all hearts are lost.
 * Optionally, methods for gaining hearts or managing extra lives.
 */
public class HeartsSystem extends Sprite {
	private final static Image HEART_IMAGE = new Image("images/heart.png", 100, 100, false, false);
	
	HeartsSystem(double xPos , double yPos){
		super(xPos, yPos,HeartsSystem.HEART_IMAGE);
	}
		void die() {
		this.vanish();
	}
}
