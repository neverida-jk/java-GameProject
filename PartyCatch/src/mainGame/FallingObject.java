/***********************************
 * Represents the falling fruits or objects.
 * Manages the position, type, and state of each falling object.
 ************************************/

package mainGame;

import java.util.*;
import javafx.scene.image.Image;

abstract class FallingObject extends Sprite {
	
	// Speed at which the falling object moves
	 double speed;
	//final static double COLL_SPEED = 10;
	
	// Constructor for the FallingObject class
	FallingObject(double x, double y, Image image){
		super(x,y,image);
		//speed = FallingObject.COLL_SPEED;
	}

	// Moves the falling object downward on the screen
	void move(){
		this.yPos += this.speed;
		// Check if the object has passed through the bottom of the scene
		if(this.yPos >= GameView.WINDOW_HEIGHT){
			this.vanish(); // If the object passes through the bottom, set visible to false
		}
	}
	
	abstract void checkCollision(Basket basket); // Abstract method to be implemented in subclasses
	abstract boolean isPowerUp(); // Abstract method to determine whether the object is a power-up
}
