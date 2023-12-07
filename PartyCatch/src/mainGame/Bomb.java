package mainGame;

import javafx.scene.image.Image;

/*
 * What for:
 * 
 * "Bomb (Minus 1 Heart)" power-down.
 * Provides methods to apply the power-down effect to the game, deducting one heart when a bomb is hit.
 */
public class Bomb extends FallingObject {
	static int BOMB_SPEED = 8;
	private final static Image BOMB_IMAGE = new Image("images/bombCatch.png",120,120,false,false);
	//private final static int GAIN = 10;
	private static final int BOMB_DMG = 1;

	Bomb(double xPos, double yPos) {
		super(xPos, yPos, BOMB_IMAGE);
		this.speed = Bomb.BOMB_SPEED;
	}
	
	void move(){
		this.yPos += Bomb.BOMB_SPEED;
		if(this.yPos >= GameView.WINDOW_HEIGHT){	// if this monster passes through the bottom of the scene, set visible to false
			this.vanish();
		}
	}

	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println("YOU CAUGHT A BOMB, -1 Heart");
			Basket.BASKET_LIFE -= BOMB_DMG;
			this.vanish();
			if(Basket.BASKET_LIFE == 0){
				basket.die();
				System.out.println("GAME OVER - 3 BOMBS CAUGHT");
			}
		}
	}

}
