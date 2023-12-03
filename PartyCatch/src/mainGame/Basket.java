package mainGame;

/*
 * 	What for:
 * 
 * Represents the basket controlled by the player.
 * Manages the position and state of the basket.
 */

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Basket extends Sprite {
	private String name;
	private boolean alive;
	private int score;

	private final static Image BASKET_IMAGE = new Image("images/basket.jpg");
	private final static double INITIAL_POSX = 300;
	private final static double INITIAL_POSY = 600;
	final static int BASKET_SPEED = 20;
	public static int BASKET_LIFE = 100;//public para madali makita nung bomb HAHAHAHA

	Basket(String name){
		super(Basket.INITIAL_POSX, Basket.INITIAL_POSY,Basket.BASKET_IMAGE);
		this.name = name;
		this.alive = true;
		Basket.BASKET_LIFE = 100;//sinet ko alng na life nung mismong basket
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
	
	//add score
    void gainScore(int increase){
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }
    
    void loseScore (int decrease) {
    	this.score-=decrease;
    	System.out.println("Score: "+score);
    }
    
	//functions for minus points, score multiplier,
	//slow fallling object, time boost 
	// in short(powerups and downs)

	void move(){
		if(this.xPos+this.dx >= 0 && this.xPos+this.dx <= GameView.WINDOW_WIDTH-this.width){
			this.xPos += this.dx; 
			//Unsure sa if statement yung pangalawa
			//GameView. or Game. maalin lang sa dalawa
			//base yun dun sa application name e di ko sure alin
			//pero yung nakita ko GameView.
		}
	}

}
