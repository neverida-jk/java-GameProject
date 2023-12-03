package mainGame;

import javafx.scene.image.Image;
public class Apple extends FallingObject {
	
	private final static int APPLE_SPEED = 7;
	private final static Image APPLE_IMAGE = new Image("images/apple_sprite.gif");
	private final static int GAIN = 10;
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
			System.out.println(this.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Apple.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				System.out.println("HINDI MO NAKUHA APPLE BUBU, -"+this.LOSE); 
				basket.loseScore(Apple.LOSE);
				this.vanish();
			}
		}
	}

}
