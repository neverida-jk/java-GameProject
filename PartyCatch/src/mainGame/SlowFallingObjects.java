/**********************************************************
 * The SlowFallingObjects class has the following functions:
 *
 *  "Slow Falling Objects" power-up.
 *  Manages the duration of the power-up.
 *  Provides methods to apply the power-up effect to the game, temporarily slowing down the speed of the Bomb.
 *  Inherits from FallingObject class
 ***********************************************************/

package mainGame;

import javafx.scene.image.Image;

public class SlowFallingObjects extends FallingObject{
	
	
	//private DoubleScoreTimer timer;
	private SlowFallingObjectsTimer timer; //Instance variable of SlowFallingObjectsTimer to manage the duration of the power-up
	private final static int SPEED = 7; //Static variable for the initial speed of the falling slow falling objects power-up
	final static Image IMAGE = new Image("images/potionCatch.png",120,120,false,false); //Image object representing the slow falling objects power-up graphic
	//private final static int GAIN = 10;
	//private static final int DMG = 1;
	private boolean isActivated; //Boolean variable indicating whether the power-up is currently activated
	
	//Constructor - Initializes the slow falling objects power-up with a given position and the power-up image
	SlowFallingObjects(double xPos, double yPos){
		super(xPos, yPos, IMAGE);
		this.speed = SlowFallingObjects.SPEED;
		this.isActivated = false;
	}

	/*DoubleScore(double xPos, double yPos) {
		super(xPos, yPos, IMAGE);
		this.speed = DoubleScore.SPEED;
		this.isActivated = false;
	}*/

	/*
	void move(){
		this.yPos += DoubleScore.SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}*/
	
	//Returns true if the power-up is currently activated
	boolean isActive(){
		return this.isActivated;
	}
	
	//Returns true if the power-up's effect has ended
	boolean die(){
		return this.isActivated = true;
	}

	//Activates the power-up, slowing down the falling objects (Bomb) and starting the timer
	void activate(){
		if(!this.isActivated){
			die();
			this.isActivated = true;
			this.timer = new SlowFallingObjectsTimer(this);
			this.timer.start();
			System.out.println("SLOW MO ACTIVATED FOR 10 SECONDS!");
			Bomb.BOMB_SPEED -=6;
		}else this.timer.refresh();
	}

	//Deactivates the power-up, restoring the original Bomb's speed
	void deactivate(){
		this.isActivated = false;
		System.out.println("SLOW MO DE-ACTIVATED!");
		Bomb.BOMB_SPEED +=6;
	}


	//Checks if the power-up collides with the basket and activates the power-up effect
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			activate();
			this.vanish();
		}
	}

	//Returns true since it is a power-up falling object
	@Override
	boolean isPowerUp() {
		return true;
	}

}
