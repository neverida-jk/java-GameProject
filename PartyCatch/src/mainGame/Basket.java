/***********************************
 * Represents the basket controlled by the player.
 * Manages the position and state of the basket.
 ************************************/

package mainGame;

import javafx.scene.image.Image;

public class Basket extends Sprite {
	private String name;
	private boolean alive;
	private int score;

	// Initial position of the Basket
	private final static double INITIAL_POSX = 340;
	private final static double INITIAL_POSY = 600;
	// Speed at which the Basket moves
	static int BASKET_SPEED = 20;
	public static int BASKET_LIFE = 3; // Number of lives for the Basket

	// Constructor for the Basket class
	Basket(String name, Image image, int score){
		super(Basket.INITIAL_POSX, Basket.INITIAL_POSY, image);
		this.name = name;
		this.alive = true;
		this.score = score;
	}

	//Name of Basket Character
	String getName(){
		return this.name;
	}

	//Gets score
	int getScore(){
		return this.score;
	}

	//check the state of basket if alive
	boolean isAlive(){
		return this.alive;
	}

	//Sets the Basket Character as dead
	void die(){
		this.alive = false;
	}
	
	boolean isUpgraded() {
		return false;
	}
	
	//add score
    void gainScore(int increase){
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }
    
    // Decreases the Basket's score, ensuring it doesn't go below zero
    void loseScore (int decrease) {
    	this.score-=decrease;
    	if(this.score < 0) this.score = 0;
    	System.out.println("Score: "+score);
    }

    // Moves the Basket within the game window
	void move(){
		if(this.xPos+this.dx >= 0 && this.xPos+this.dx <= GameView.WINDOW_WIDTH-this.width){
			this.xPos += this.dx; 
		}
	}

}
