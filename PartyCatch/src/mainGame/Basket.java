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
	private final static double INITIAL_POSX = 0;
	private final static double INITIAL_POSY = 600;
	final static int BASKET_SPEED = 20;

	Basket(String name){
		super(Basket.INITIAL_POSX, Basket.INITIAL_POSY,Basket.BASKET_IMAGE);
		this.name = name;
		this.alive = true;
		//heart system implement here wala pa function e
		//need sa is alive
		//yung sa bullets di ko muna nilagyan kasi diba
		//di naman nagashoot yung basket HAHAHAHA
		// unsure sa speed kasi diba nababago yun

		//parang kulang pa to di ko alam yung ipapalit sa
		//array na bullet(based kay tandang at everwing)
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
