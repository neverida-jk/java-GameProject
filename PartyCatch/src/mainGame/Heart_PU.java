package mainGame;

import javafx.scene.image.Image;
public class Heart_PU extends FallingObject{
	
	private final static int HEART_SPEED = 7;//pede palitan kung ano want niyo speed -- same dun sa pineapple at apple pede rin palitan yun
	private final static Image HEART_IMAGE = new Image("images/heart.png");
	private final static int GAIN = 5; // score point if basket has max heart
	//private final static int LOSE = 3; // score point wala naman malolose kahit hindi masalo ayos lang

	Heart_PU(double xPos, double yPos) {
		super(xPos, yPos, HEART_IMAGE);
		this.speed = Heart_PU.HEART_SPEED;
	}
	
	void move(){
		this.yPos += Heart_PU.HEART_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if heart image passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			if(Basket.BASKET_LIFE < 3) {//checks if basket life is less than 3
				Basket.BASKET_LIFE++;//add a heart to the basket life
				System.out.println(Basket.BASKET_LIFE + " ABA NASALO SWERTE ADD LIFE PA NGA");
				this.vanish();//vanish after collision
			}
			else {//if basket life equal 3 just gain points 
				System.out.println(Heart_PU.GAIN + " been added to the score!");
				this.vanish();
				basket.gainScore(Heart_PU.GAIN);//gain the point which is equal to 5
			}
		}else {
			if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this item passes through the bottom of the scene, set visible to false
				this.vanish();
			}
		}
	}

}
