package mainGame;


import javafx.scene.image.Image;

class Banana extends FallingObject {
	private final static int BANANA_SPEED = 5;
	private final static Image COIN_IMAGE = new Image("images/banana.jpg");
	private final static int GAIN = 5;

	Banana(double xPos, double yPos, Image image) {
		super(xPos, yPos, Banana.COIN_IMAGE);
		this.speed = Banana.BANANA_SPEED;
	}

	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println(this.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Banana.GAIN);
		}
	}

}
