package mainGame;

import javafx.scene.image.Image;

public class DoubleScore extends FallingObject {
		private final static int SPEED = 7;
		private final static Image IMAGE = new Image("images/doubleScore.png",100,100,false,false);
		//private final static int GAIN = 10;
		private static final int DMG = 1;

		DoubleScore(double xPos, double yPos) {
			super(xPos, yPos, IMAGE);
			this.speed = DoubleScore.SPEED;
		}
		
		void move(){
			this.yPos += DoubleScore.SPEED;
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
				this.vanish();
			}
		}

		@Override
		void checkCollision(Basket basket) {
			if(this.collidesWith(basket)){
				this.vanish();
			}
		}

	}
