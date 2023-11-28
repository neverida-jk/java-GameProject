package mainGame;

import java.util.*;
import javafx.scene.image.Image;

/*
 * 	What for:
 * 
 * Represents the falling fruits or objects.
 * Manages the position, type, and state of each falling object.
 */
abstract class FallingObject extends Sprite {
	
	double speed;
	private final static double COLL_SPEED = 8;

	FallingObject(double x, double y, Image image){
		super(x,y,image);
		this.speed = FallingObject.COLL_SPEED;
	}

	void move(){
		this.yPos += this.speed;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

	void checkCollision(Basket basket){
	}
}
