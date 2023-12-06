package mainGame;


import javafx.scene.image.Image;

class Banana extends FallingObject {
	static int BANANA_SPEED = 3;//pede palitan kung ano want niyo speed -- same dun sa pineapple at apple pede rin palitan yun
	private final static Image BANANA_IMAGE = new Image("images/banana_sprite.gif");
	static int GAIN = 5; // score point
	private final static int LOSE = 3; // score point

	Banana(double xPos, double yPos) {
		super(xPos, yPos, BANANA_IMAGE);
		this.speed = Banana.BANANA_SPEED;
	}
	/*
	void move(){
		this.yPos += Banana.BANANA_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}*/

	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println(this.GAIN + " been added to the score!");
			this.vanish();
			basket.gainScore(Banana.GAIN);
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				if (basket.getScore() != 0) {
				System.out.println("HINDI MO NAKUHA BANANA BUBU, -"+this.LOSE); 
				basket.loseScore(Banana.LOSE);
				}
				this.vanish();
			}
		}
	}

}
