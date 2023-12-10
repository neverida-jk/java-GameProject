package mainGame;

import javafx.scene.image.Image;
public class Apple extends FallingObject {
	
	static int APPLE_SPEED = 5;
	private final static Image APPLE_IMAGE = new Image("images/appleCatch.png",190,130,false,false);
	static int GAIN = 10;
	private final static int LOSE = 5;
	
	Apple(double xPos, double yPos) {
		super(xPos, yPos, APPLE_IMAGE);
		this.speed = Apple.APPLE_SPEED;
	}
	
	void move(){
		this.yPos += Apple.APPLE_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}
	
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println(Apple.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Apple.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				if (basket.getScore() != 0) {
				System.out.println("You didn't catch the apple, -"+Apple.LOSE); 
				basket.loseScore(Apple.LOSE);
				}
				this.vanish();
			}
		}
	}

	@Override
	boolean isPowerUp() {
		return false;
		
	}



}
