package mainGame;

import javafx.scene.image.Image;

/*
 * What for:
 * 
 *  "Slow Falling Objects" power-up.
 *  Manages the duration of the power-up.
 *  Provides methods to apply the power-up effect to the game, temporarily slowing down the speed of falling objects.
 */
public class SlowFallingObjects extends FallingObject{
	
	
	//private DoubleScoreTimer timer;
	private SlowFallingObjectsTimer timer;
	private final static int SPEED = 7;
	final static Image IMAGE = new Image("images/poisonCatch.png",120,120,false,false);
	//private final static int GAIN = 10;
	//private static final int DMG = 1;
	private boolean isActivated;
	
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
	
	boolean isActive(){
		return this.isActivated;
	}
	
	boolean die(){
		return this.isActivated = true;
	}

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

	void deactivate(){
		this.isActivated = false;
		System.out.println("SLOW MO DE-ACTIVATED!");
		Bomb.BOMB_SPEED +=6;
	}



	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			activate();
			this.vanish();
		}
	}

	@Override
	boolean isPowerUp() {
		return true;
	}

}
