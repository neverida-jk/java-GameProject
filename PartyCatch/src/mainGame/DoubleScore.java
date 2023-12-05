package mainGame;

import javafx.scene.image.Image;


class DoubleScore extends FallingObject{
	private DoubleScoreTimer timer;
	private final static int SPEED = 7;
	private final static Image IMAGE = new Image("images/doubleScore.png",100,100,false,false);
	//private final static int GAIN = 10;
	private static final int DMG = 1;
	private boolean isActivated;

	DoubleScore(double xPos, double yPos) {
		super(xPos, yPos, IMAGE);
		this.speed = DoubleScore.SPEED;
		this.isActivated = false;
	}

	/*
	void move(){
		this.yPos += DoubleScore.SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}*/

	void activate(){
		if(!this.isActivated){
			this.isActivated = true;
			this.timer = new DoubleScoreTimer(this);
			this.timer.start();
			System.out.println("DOUBLE SCORE ACTIVATED FOR 10 SECONDS!");
			Banana.GAIN += Banana.GAIN;
			Apple.GAIN += Apple.GAIN;
			Pineapple.GAIN += Pineapple.GAIN;
		}else this.timer.refresh();
	}

	void deactivate(){
		this.isActivated = false;
		System.out.println("DOUBLE SCORE DE-ACTIVATED!");
		Banana.GAIN /= 2;
		Apple.GAIN /= 2;
		Pineapple.GAIN /= 2;	
	}



	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			activate();
			this.vanish();
		}
	}

}
