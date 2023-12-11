/***********************************
 * "Bomb (Minus 1 Heart)" power-down.
 * Provides methods to apply the power-down effect to the game, deducting one heart when a bomb is hit.
 ************************************/

package mainGame;

import javafx.scene.image.Image;

public class Bomb extends FallingObject {
	// Speed at which the Bomb falls
	static int BOMB_SPEED = 8;
	// Image representing the Bomb
	final static Image BOMB_IMAGE = new Image("images/bombCatch.png",120,120,false,false);
	// Damage value inflicted by the Bomb (number of hearts deducted)
	private static final int BOMB_DMG = 1;

	// Constructor for the Bomb class
	Bomb(double xPos, double yPos) {
		super(xPos, yPos, BOMB_IMAGE);
		this.speed = Bomb.BOMB_SPEED;
	}
	
	// Moves the Bomb downward on the screen
	void move(){
		this.yPos += Bomb.BOMB_SPEED;
		// Check if the Bomb has passed through the bottom of the scene
		if(this.yPos >= GameView.WINDOW_HEIGHT){
			// If the Bomb passes through the bottom, set visible to false
			this.vanish();
		}
	}

	// Checks for collisions with the Basket and applies the power-down effect
	@Override
	void checkCollision(Basket basket) {
		if(this.collidesWith(basket)){
			System.out.println("YOU CAUGHT A BOMB, -1 Heart");
			// Deduct one heart from the Basket
			Basket.BASKET_LIFE -= BOMB_DMG;
			this.vanish();
			// Check if the Basket has run out of hearts
			if(Basket.BASKET_LIFE == 0){
				basket.die();
				System.out.println("GAME OVER - 3 BOMBS CAUGHT");
			}
		}
	}

	// Indicates whether the Bomb is a power-up (not a power-up)
	@Override
	boolean isPowerUp() {
		return false;
	}

	static void upgrade() {
		Bomb.BOMB_SPEED += 1;
	}

}
