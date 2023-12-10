package mainGame;


import javafx.scene.image.Image;
public class Pineapple extends FallingObject {
	
	static int PINEAPPLE_SPEED = 7;
	private final static Image PINEAPPLE_IMAGE = new Image("images/watermelonCatch.png",150,160,false,false);
	static int GAIN = 20;
	private final static int LOSE = 10;

	Pineapple(double xPos, double yPos) {
		super(xPos, yPos, PINEAPPLE_IMAGE);
		this.speed = Pineapple.PINEAPPLE_SPEED;
	}
	
	void move(){
		this.yPos += Pineapple.PINEAPPLE_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

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

	@Override
	boolean isPowerUp() {
		return false;
	}



}
