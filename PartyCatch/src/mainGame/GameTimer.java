package mainGame;

import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/* 
 * 	What for:
 * 
 * Manages the game loop and timing.
 * Extends AnimationTimer to create a continuous loop that updates the game state.
 * Calls methods to update the game logic, render objects, and handle events on each frame.
 * Ensures that the game runs at a consistent frame rate.
 * Ensures that the game runs at a consistent frame rate.
 * Manages timing-related functionalities like animations and countdowns.
 */

class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	private Basket basket;
	private Scene scene;
	private static boolean goLeft;
	private static boolean goRight;
	private ArrayList<FallingObject> objects;
	private double backgroundY;
	private Image background = new Image( "images/ben10bg.jpg" );
	private long startSpawn;
	
	public final static int MIN_OBJECT = 0;
	public final static int MAX_OBJECT = 5;
	public final static int OBJECT_TYPES = 1;
	public final static int WIDTH_PER_OBJECT = 80;
	public final static int OBJECT_INITIAL_YPOS = -60;
	public final static int BACKGROUND_SPEED = 2;
	public final static double SPAWN_DELAY = 1.5;
	
	
    GameTimer(Scene scene, GraphicsContext gc) {
    	this.gc = gc;
    	this.scene = scene;    	
    	this.basket= new Basket("Default");
    	this.objects = new ArrayList<FallingObject>();
    	this.startSpawn = System.nanoTime();
    	this.prepareActionHandlers();
    }
    
	@Override
	public void handle(long currentNanoTime) {
		this.redrawBackgroundImage();
                
        this.renderSprites();
        this.moveSprites();
        
        this.drawScore();
        
        if(!this.basket.isAlive()) {
        	this.stop();				// stops this AnimationTimer (handle will no longer be called) so all animations will stop
        	this.drawGameOver();		// draw Game Over text
        }
	}
	
	 void redrawBackgroundImage() {
			// clear the canvas
	        this.gc.clearRect(0, 0, GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);

	        // redraw background image (moving effect)
	        this.backgroundY += GameTimer.BACKGROUND_SPEED;

	        this.gc.drawImage( background, 0, this.backgroundY-this.background.getHeight() );
	        this.gc.drawImage( background, 0, this.backgroundY );
	        
	        if(this.backgroundY>=GameView.WINDOW_HEIGHT) 
	        	this.backgroundY = GameView.WINDOW_HEIGHT-this.background.getHeight();
	    }
	 
	 void renderSprites() {
	    	// draw guardian
	        this.basket.render(this.gc);
	        
	        // draw Sprites in ArrayLists
	        for (FallingObject objects : this.objects)
	        	objects.render( this.gc );
	      
	    }
	 
	   void moveSprites() {
	        this.moveBasket();
	        this.moveObjects();
	    }
	   
	   /*
	     * Catches the left and right key presses for the basket's movement
	     * */
		private void prepareActionHandlers() {
	    	this.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
	        {
	            public void handle(KeyEvent e)
	            {
	                String code = e.getCode().toString();
	                if(code.equals("LEFT")) {
	                	GameTimer.goLeft = true;
	                }else if(code.equals("RIGHT")) {
	                	GameTimer.goRight = true;
	                }
	                
	            }
	        });

	    	this.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
	        {
	            public void handle(KeyEvent e)
	            {
	                String code = e.getCode().toString();
	                if(code.equals("LEFT")) {
	                	GameTimer.goLeft = false;
	                }else if(code.equals("RIGHT")) {
	                	GameTimer.goRight = false;
	                }
	            }
	        });
	    }
		
		/*
	     * Gets called in handle() to move the basket based on the goLeft and goRight flags
	     * */
		private void moveBasket() {
			if (GameTimer.goLeft)
	            this.basket.setDX(-Basket.BASKET_SPEED);
			else if (GameTimer.goRight)
	        	this.basket.setDX(Basket.BASKET_SPEED);
	        else 
	        	this.basket.setDX(0);
	        
	        this.basket.move();
	        
		}
		
		private void drawScore(){
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.YELLOW);
			this.gc.fillText("Score:", 20, 30);
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText(basket.getScore()+"", 90, 30);
		}
		
		private void drawGameOver(){
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText("GAME OVER!", 20, GameView.WINDOW_HEIGHT/2);
		}

		/*
	     * Moves the objects and checks if they collide with the basket in checkCollision()
	     * If they are outside the screen, they get removed from the ArrayList
	     * */
		private void moveObjects() {
			for(int i = 0; i < this.objects.size(); i++){
				FallingObject m = this.objects.get(i);
				if(m.isVisible()){
					m.move();
					//m.checkCollision(this.objects);
				}
				else this.objects.remove(i);
			}
		}
}
