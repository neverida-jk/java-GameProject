package mainGame;

import application.Main;
import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private SlowFallingObjects SFOT;
	private Scene scene;
	private Stage stage;
	private static boolean goLeft;
	private static boolean goRight;
	private ArrayList<FallingObject> objects;
	private ArrayList<HeartsSystem> hearts;
	private double backgroundY;
	private Image background = new Image( "images/BG_Mountain.jpg", 1100, 800, false, false );
	//private Image here = new Image("images/basket.jpg");
	private long startSpawn;
	private long startSpawnP;
	private long startSpawnA;
	private long startSpawnB;
	private long startSpawnDouble;
	private long startSpawnH;
	private long startSpawnSFO;
	private int spawnBananaCount;
	private int spawnPineappleCount;
	private int spawnAppleCount;
	private int spawnBombCount;
	private int spawnDoubleCount;
	private int spawnSFOCount;
	private int spawnHeartCount;
	private int newBg;
	public static int time;
	public static String pUpName;
	public static int times;
	public static String pUpNames;
	public static Random r = new Random();

	public final static int winningScore = 1000; //<= edit this for score winning condition
	public final static int MIN_OBJECT = 2;
	public final static int MAX_OBJECT = 5;
	public final static int OBJECT_TYPES = 3;
	public final static int WIDTH_PER_OBJECT_BANANA= 300;
	public final static int WIDTH_PER_OBJECT_PINEAPPLE = 500;
	public final static int OBJECT_INITIAL_YPOS = -60;
	public final static int BACKGROUND_SPEED = 0;
	public static double SPAWN_DELAY = 1;//initial delay bago lumabas yung unang banana pic
	public static double SPAWN_DELAY_HEART = 25; //delay bago lumabas yung unang heart pic
	public static double SPAWN_DELAY_SFO = 22; 
	public static double SPAWN_DELAY_DOUBLE = 29;//delay bago lumabas yung unang double score pic
	public static double SPAWN_DELAY_B = 3;//delay bago lumabas yung unang bomb pic
	public static double SPAWN_DELAY_P = 9;//delay bago lumabas yung unang pineapple pic
	public static double SPAWN_DELAY_A = 6;//delay bago lumabas yung unang apple pic
	public final static int SPAWN_NUM_HEART = 1;//spawn 1 time only every time
	public final static int SPAWN_NUM_BOMB = 1;
	public final static int SPAWN_NUM_SFO = 1;
	public final static int SPAWN_NUM_DOUBLE = 1;//spawn 1 time only every time
	public final static int SPAWN_NUM_BANANA = 1;//isang pic lang na banana every time na mag aappear talagang mabilis lang pagspawn nito - normies
	public final static int SPAWN_NUM_PINEAPPLE = 1;//isang pic lang ng pineapple every time and interval na 7 seconds bago yugng sunod na pic - 2nd magandang ability
	public final static int SPAWN_NUM_APPLE = 1;//isang pic lang ng apple every time and magaappear tapos may interval na 10 seconds - pinakamalakas na fruit

	GameTimer(Scene scene, GraphicsContext gc) {
		this.gc = gc;
		this.scene = scene;    
		this.basket= new Basket("Default");
		GameTimer.pUpName = "";
		GameTimer.pUpNames = "";
		this.objects = new ArrayList<FallingObject>();
		this.hearts = new ArrayList<HeartsSystem>();
		this.spawnBananaCount = this.spawnHeartCount = this.spawnAppleCount = this.spawnBombCount = this.spawnDoubleCount = this.spawnPineappleCount = this.spawnSFOCount = 0;
		this.startSpawn = this.startSpawnH = this.startSpawnDouble = this.startSpawnP = this.startSpawnA = this.startSpawnB = this.startSpawnSFO =  System.nanoTime();// time start
		this.prepareActionHandlers();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.generateHEARTS(1);
		this.redrawBackgroundImage();

		this.AutoSpawn(currentNanoTime);

		this.renderSprites();
		this.moveSprites();

		this.drawScore();
		if(GameTimer.time != 0) {
			this.drawTimer(time, pUpName);//double point
		}
		if(GameTimer.times != 0) {
			this.drawTimer1(times, pUpNames);//bomb
		}

		if(!this.basket.isAlive() || this.basket.getScore() >= this.winningScore) {
			this.drawGameOver();		// draw Game Over text
		}

	}


	void redrawBackgroundImage() {
		// clear the canvas
		this.gc.clearRect(0, 0, GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
		this.gc.drawImage( background, 0, this.backgroundY );

	}

	void AutoSpawn(Long currentNanoTime){
		//HEART SPAWN
		double spawnElapsedTimeH = (currentNanoTime-this.startSpawnH) / 1000000000.0;
		if(spawnElapsedTimeH > GameTimer.SPAWN_DELAY_HEART){

			this.startSpawnH = System.nanoTime();
		}
		if (this.spawnHeartCount == 0 && spawnElapsedTimeH == 1000000000.0){ // checks if initial number of banana has not been generated
			this.generateHEART(SPAWN_NUM_HEART);// initial number of slime = 1
			this.spawnHeartCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTimeH > GameTimer.SPAWN_DELAY_HEART) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateHEART(SPAWN_NUM_HEART); // spawn number of banana = 1
			this.startSpawnH = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
			this.spawnHeartCount++;
		}

		//SLOW FALLING OBJECTS SPAWN
		double spawnElapsedTimeSFO = (currentNanoTime-this.startSpawnSFO) / 1000000000.0;
		if(spawnElapsedTimeSFO > GameTimer.SPAWN_DELAY_SFO){

			this.startSpawnSFO = System.nanoTime();
		}
		if (this.spawnSFOCount == 0 && spawnElapsedTimeSFO == 1000000000.0){ // checks if initial number of bomb has not been generated
			this.generateSFO(SPAWN_NUM_SFO);// initial number of slime = 1
			this.spawnSFOCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTimeSFO > GameTimer.SPAWN_DELAY_SFO) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateSFO(SPAWN_NUM_SFO); // spawn number of banana = 1
			this.startSpawnSFO = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
			this.spawnSFOCount++;
		}

		//DOUBLE SCORE SPAWN
		double spawnElapsedTimeDouble = (currentNanoTime-this.startSpawnDouble) / 1000000000.0;
		if(spawnElapsedTimeDouble > GameTimer.SPAWN_DELAY_DOUBLE){

			this.startSpawnDouble = System.nanoTime();
		}
		if (this.spawnDoubleCount == 0 && spawnElapsedTimeDouble == 1000000000.0){ // checks if initial number of bomb has not been generated
			this.generateDOUBLE(SPAWN_NUM_DOUBLE);// initial number of slime = 1
			this.spawnDoubleCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTimeDouble > GameTimer.SPAWN_DELAY_DOUBLE) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateDOUBLE(SPAWN_NUM_DOUBLE); // spawn number of banana = 1
			this.startSpawnDouble = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
			this.spawnDoubleCount++;
		}

		//BOMB SPAWN 
		double spawnElapsedTimeB = (currentNanoTime-this.startSpawnB) / 1000000000.0;
		if(spawnElapsedTimeB > GameTimer.SPAWN_DELAY_B){

			this.startSpawnB = System.nanoTime();
		}
		if (this.spawnBombCount == 0 && spawnElapsedTimeDouble == 1000000000.0){ // checks if initial number of bomb has not been generated
			this.generateBOMB(SPAWN_NUM_BOMB);// initial number of slime = 1
			this.spawnBombCount++; // increments counter of spawned banana, false if condition
		} else if (spawnElapsedTimeB > GameTimer.SPAWN_DELAY_B) { // checks if spawnElapsedTime is greater than 1.5 seconds 
			this.generateBOMB(SPAWN_NUM_BOMB); // spawn number of banana = 1
			this.startSpawnB = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 1.5)
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

		//PINEAPPLE SPAWN
		double spawnElapsedTimeP = (currentNanoTime-this.startSpawnP) / 1000000000.0;
		if(spawnElapsedTimeP > GameTimer.SPAWN_DELAY_P){

			this.startSpawnP = System.nanoTime();
		}
		if (this.spawnPineappleCount == 0 && spawnElapsedTimeDouble == 1000000000.0){  // checks if initial number of pineapple has not been generated
			this.generatePINEAPPLE(SPAWN_NUM_PINEAPPLE);// initial number of pineapple = 1
			this.spawnPineappleCount++; // increments counter of spawned pineapple, false if condition
		} else if (spawnElapsedTimeP > GameTimer.SPAWN_DELAY_P) { // checks if spawnElapsedTimeP is greater than 7 seconds 
			this.generatePINEAPPLE(SPAWN_NUM_PINEAPPLE); // spawn number of pineapple = 1
			this.startSpawn = System.nanoTime(); // resets banana spawn timer to its nanoTime (0 to compare again until 7)
			this.spawnPineappleCount++;
		}

		//APPLE SPAWN
		double spawnElapsedTimeA = (currentNanoTime-this.startSpawnA) / 1000000000.0;
		if(spawnElapsedTimeA > GameTimer.SPAWN_DELAY_A){

			this.startSpawnA = System.nanoTime();
		}
		if (this.spawnAppleCount == 0 && spawnElapsedTimeDouble == 1000000000.0){ // checks if initial number of apple has not been generated
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

		for (HeartsSystem hearts : this.hearts)
			hearts.render( this.gc );

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
		this.gc.setFont(Font.font("Comic sans MS", FontWeight.BOLD, 50));
		this.gc.setFill(Color.RED);
		this.gc.fillText(Basket.BASKET_LIFE+"", 660, 60);
	}

	void drawTimer(int time, String name){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.BLUE);
		this.gc.fillText(name + time + "s left.", 20, 60);
	}

	void drawTimer1(int time, String name){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.BLUE);
		this.gc.fillText(name + time + "s left.", 20, 80);
	}

	//(copied sa everwing)
	private void drawGameOver(){
		if(basket.getScore() >= this.winningScore) {
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText("WINNER!", 20, GameView.WINDOW_HEIGHT/2);
			GameView.mediaPlayer.stop();

		}
		else {
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText("GAME OVER!", 20, GameView.WINDOW_HEIGHT/2);
			GameView.mediaPlayer.stop();
		}
		this.stop();

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


	private void generateHEARTS(int numberOfHEARTS){
		this.hearts.clear();
		//for (int i=0; i < numberOfHEARTS;i++) { // loops through each instance of banana
		int y = 0;//to start at the very top
		int x = 700;
		this.hearts.add(new HeartsSystem(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		//}

	}

	private void generateHEART(int spawnNumHeart){
		GameTimer.SPAWN_DELAY_HEART = r.nextDouble(30, 50);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < spawnNumHeart;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//random x location to start
			this.objects.add(new Heart_PU(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	private void generateSFO(int spawnNumSFO){
		GameTimer.SPAWN_DELAY_SFO = r.nextDouble(20, 40);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < spawnNumSFO;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//random x location to start
			this.objects.add(new SlowFallingObjects(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	private void generateDOUBLE(int spawnNumDouble){
		GameTimer.SPAWN_DELAY_DOUBLE = r.nextDouble(20, 40);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < spawnNumDouble;i++) { // loops through each instance of banana
			int y = -640;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//random x location to start
			this.objects.add(new DoubleScore(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 banana at a random x and y locations
	private void generateBOMB(int numberOfBOMB){
		GameTimer.SPAWN_DELAY_B = r.nextDouble(1,5);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfBOMB;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//random x location to start
			this.objects.add(new Bomb(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 banana at a random x and y locations
	private void generateBANANA(int numberOfBANANA){
		GameTimer.SPAWN_DELAY = r.nextDouble(0, 1.5); //<== randomize spawn delay everytime
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfBANANA;i++) { // loops through each instance of banana
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//random x location to start
			this.objects.add(new Banana(x,y)); // adds an instance of banana in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 pineapple at a random x and y locations
	private void generatePINEAPPLE(int numberOfPINEAPPLE){
		GameTimer.SPAWN_DELAY_P = r.nextDouble(5, 20);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfPINEAPPLE;i++) { // loops through each instance of pineapple
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//r.nextInt((WIDTH_PER_OBJECT_PINEAPPLE * GameView.WINDOW_HEIGHT)/500); random x location to start
			this.objects.add(new Pineapple(x,y)); // adds an instance of pineapple in the array list of falling objects, adds more to renderSprites() method
		}
	}

	// Instantiates 1 apple at a random x and y locations
	private void generateAPPLE(int numberOfAPPLE){
		GameTimer.SPAWN_DELAY_A = r.nextDouble(5, 10);
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfAPPLE;i++) { // loops through each instance of apple
			int y = -600;//to start at the very top
			int x = r.nextInt(GameView.WINDOW_WIDTH - 120);//r.nextInt((WIDTH_PER_OBJECT_BANANA * GameView.WINDOW_HEIGHT)/500); random x location to start
			this.objects.add(new Apple(x,y));// adds an instance of apple   in the array list of falling objects, adds more to renderSprites() method
		}
	}
}
