/***********************************
 * The Heart_PU class represents the heart power-up falling object in the game
 * It also inherits from the FallingObject class
 ************************************/

package mainGame;

import javafx.scene.image.Image;
public class Heart_PU extends FallingObject{
	
	private final static int HEART_SPEED = 7;//Constant for the speed of the heart power-up
	private final static Image HEART_IMAGE = new Image("images/heart.png"); //Image object representing the heart graphic
	private final static int GAIN = 10; // Score point if basket has max hearts
	// No score penalty if heart is not caught

	//Constructor
	Heart_PU(double xPos, double yPos) {
		super(xPos, yPos, HEART_IMAGE);
		this.speed = Heart_PU.HEART_SPEED;
	}
	
	//Moves the heart downward on the screen
	void move(){
		this.yPos += Heart_PU.HEART_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if heart image passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

	//Checks if the heart collides with the basket and updates the basket's life or score accordingly
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			if(Basket.BASKET_LIFE < 3) {//checks if basket life is less than 3
				Basket.BASKET_LIFE++;//add a heart to the basket life
				System.out.println(Basket.BASKET_LIFE + " You caught the Heart! +1 Heart");
				this.vanish();//vanish after collision
			}
			else {//if basket life equal 3 just gain points 
				System.out.println(Heart_PU.GAIN + " been added to the score!");
				this.vanish();
				basket.gainScore(Heart_PU.GAIN);//gain the point which is equal to 10
			}
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				this.vanish();
			}
		}
	}

	//Returns true since it is a power-up falling object
	@Override
	boolean isPowerUp() {
		return true;
	}



}
