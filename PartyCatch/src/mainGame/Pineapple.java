package mainGame;


import javafx.scene.image.Image;
public class Pineapple extends FallingObject {
	
	private final static int PINEAPPLE_SPEED = 5;
	private final static Image PINEAPPLE_IMAGE = new Image("images/pineapple_sprite.gif");
	private final static int GAIN = 20;
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
			System.out.println(this.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Pineapple.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				if (basket.getScore() != 0) {
				System.out.println("HINDI MO NAKUHA PINEAPPLE BUBU, -"+this.LOSE); 
				basket.loseScore(Pineapple.LOSE);
				}
				this.vanish();
			}
		}
	}

}
