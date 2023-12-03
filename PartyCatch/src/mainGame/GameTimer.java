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
	private Image here = new Image("images/basket.jpg");
	private long startSpawn;
	private long startSpawnP;
	private long startSpawnA;
	private long startSpawnB;
	private int spawnBananaCount;
	private int spawnPineappleCount;
	private int spawnAppleCount;
	private int spawnBombCount;

	public final static int MIN_OBJECT = 2;
	public final static int MAX_OBJECT = 5;
	public final static int OBJECT_TYPES = 3;
	public final static int WIDTH_PER_OBJECT_BANANA= 300;
	public final static int WIDTH_PER_OBJECT_PINEAPPLE = 500;
	public final static int OBJECT_INITIAL_YPOS = -60;
	public final static int BACKGROUND_SPEED = 0;
	public final static double SPAWN_DELAY = 1.5;//delay bago lumabas yung banana pic
	public final static double SPAWN_DELAY_B = 1.5;//delay bago lumabas yung bomb pic
	public final static double SPAWN_DELAY_P = 7;//delay bago lumabas yung pineapple pic
	public final static double SPAWN_DELAY_A = 10;//delay bago lumabas yung apple pic
	public final static int SPAWN_NUM_BOMB = 1;
	public final static int SPAWN_NUM_BANANA = 1;//isang pic lang na banana every time na mag aappear talagang mabilis lang pagspawn nito - normies
	public final static int SPAWN_NUM_PINEAPPLE = 1;//isang pic lang ng pineapple every time and interval na 7 seconds bago yugng sunod na pic - 2nd magandang ability
	public final static int SPAWN_NUM_APPLE = 1;//isang pic lang ng apple every time and magaappear tapos may interval na 10 seconds - pinakamalakas na fruit


	GameTimer(Scene scene, GraphicsContext gc) {
		this.gc = gc;
		this.scene = scene;    	
		this.basket= new Basket("Default");
		this.objects = new ArrayList<FallingObject>();
		this.spawnBananaCount = 0;
		this.startSpawn = this.startSpawnP = this.startSpawnA = this.startSpawnB = System.nanoTime();// time start
		this.prepareActionHandlers();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.redrawBackgroundImage();
		this.FruitsSpawn(currentNanoTime);

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

	void FruitsSpawn(Long currentNanoTime){
		//BOMB SPAWN 
		double spawnElapsedTimeB = (currentNanoTime-this.startSpawn) / 1000000000.0;
		if(spawnElapsedTimeB > GameTimer.SPAWN_DELAY){

			this.startSpawn = System.nanoTime();
		}
		if (this.spawnBombCount == 0){ // checks if initial number of bomb has not been generated
			this.generateBOMB(SPAWN_NUM_BOMB);// initial number of slime = 1
			this.spawnBombCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTimeB > GameTimer.SPAWN_DELAY) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateBOMB(SPAWN_NUM_BOMB); // spawn number of banana = 1
			this.startSpawn = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
			this.spawnBombCount++;
		}
		
		//BANANA SPAWN
		double spawnElapsedTime = (currentNanoTime-this.startSpawn) / 1000000000.0;
		if(spawnElapsedTime > GameTimer.SPAWN_DELAY){

			this.startSpawn = System.nanoTime();
		}
		if (this.spawnBananaCount == 0){ // checks if initial number of banana has not been generated
			this.generateBANANA(SPAWN_NUM_BANANA);// initial number of slime = 1
			this.spawnBananaCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTime > GameTimer.SPAWN_DELAY) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateBANANA(SPAWN_NUM_BANANA); // spawn number of banana = 1
			this.startSpawn = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
			this.spawnBananaCount++;
		}

		double spawnElapsedTimeP = (currentNanoTime-this.startSpawnP) / 1000000000.0;
		if(spawnElapsedTimeP > GameTimer.SPAWN_DELAY_P){

			this.startSpawnP = System.nanoTime();
		}
		if (this.spawnPineappleCount == 0){  // checks if initial number of pineapple has not been generated
			this.generatePINEAPPLE(SPAWN_NUM_PINEAPPLE);// initial number of pineapple = 1
			this.spawnPineappleCount++; // increments counter of spawned pineapple, false if condition
		} else if (spawnElapsedTimeP > GameTimer.SPAWN_DELAY_P) { // checks if spawnElapsedTimeP is greater than 7 seconds 
			this.generatePINEAPPLE(SPAWN_NUM_PINEAPPLE); // spawn number of pineapple = 1
			this.startSpawn = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 7)
			this.spawnPineappleCount++;
		}

		double spawnElapsedTimeA = (currentNanoTime-this.startSpawnA) / 1000000000.0;
		if(spawnElapsedTimeA > GameTimer.SPAWN_DELAY_A){

			this.startSpawnA = System.nanoTime();
		}
		if (this.spawnAppleCount == 0){ // checks if initial number of apple has not been generated
			this.generateAPPLE(SPAWN_NUM_APPLE);// initial number of apple = 1
			this.spawnAppleCount++; // increments counter of spawned apple, false if condition
		} else if (spawnElapsedTimeA > GameTimer.SPAWN_DELAY_A) { // checks if spawnElapsedTimeA is greater than 10 seconds 
			this.generateAPPLE(SPAWN_NUM_APPLE); // spawn number of apple = 1
			this.startSpawnA = System.nanoTime(); // resets apple spawn timer to its nanoTime (0 to compare again until 10)
			this.spawnAppleCount++;
		}
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

	//score on upper left (copied sa everwing)
	private void drawScore(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("Score:", 20, 30);
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(basket.getScore()+"", 90, 30);
	}

	//(copied sa everwing)
	private void drawGameOver(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("GAME OVER!", 20, GameView.WINDOW_HEIGHT/2);
	}

	/*
	 * Moves the objects and checks if they collide with the basket in checkCollision()
	 * checkCollision 'di ko pa ma-gets, copied din sa everwing, tho hindi pa gumagana
	 * */
	private void moveObjects() {
		for(int i = 0; i < this.objects.size(); i++){
			FallingObject m = this.objects.get(i);
			if(m.isVisible()){
				m.move();
				m.checkCollision(basket);
			}
			else this.objects.remove(i);
		}
	}


	// Instantiates 1 banana at a random x and y locations
	private void generateBOMB(int numberOfBOMB){
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfBOMB;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 250);//random x location to start
			this.objects.add(new Bomb(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 banana at a random x and y locations
	private void generateBANANA(int numberOfBANANA){
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfBANANA;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 250);//random x location to start
			this.objects.add(new Banana(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 pineapple at a random x and y locations
	private void generatePINEAPPLE(int numberOfPINEAPPLE){
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfPINEAPPLE;i++) { // loops through each instance of pineapple
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 250);//r.nextInt((WIDTH_PER_OBJECT_PINEAPPLE * GameView.WINDOW_HEIGHT)/500); random x location to start
			this.objects.add(new Pineapple(x,y)); // adds an instance of pineapple in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 apple at a random x and y locations
	private void generateAPPLE(int numberOfAPPLE){
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfAPPLE;i++) { // loops through each instance of apple
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 250);//r.nextInt((WIDTH_PER_OBJECT_BANANA * GameView.WINDOW_HEIGHT)/500); random x location to start
			this.objects.add(new Apple(x,y));// adds an instance of apple   in the array list of falling objects, adds more to renderSprites() method
		}
	}



}
