package mainGame;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


class DoubleScore extends FallingObject{
	private DoubleScoreTimer timer;
	private final static int SPEED = 7;
	final static Image IMAGE = new Image("images/doubleScore.png",120,120,false,false);
	private final static ImageView image = new ImageView(IMAGE);
	private static final int DMG = 1;
	private boolean isActivated;

	DoubleScore(double xPos, double yPos) {
		super(xPos, yPos, IMAGE);
		this.speed = DoubleScore.SPEED;
		this.isActivated = false;
	}

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


	@Override
	boolean isPowerUp() {
		return true;
	}

}
