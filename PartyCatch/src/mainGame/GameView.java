/**********************************************************
 * The GameView class has the following functions:
 *
 *  Handles the graphical representation of the game.
 *  Displays game objects on the screen (e.g., sprites, UI elements).
 *  Listens for user input events and forwards them to the GameController.
 *  Renders sprites, updates the display, and manages the graphical user interface (GUI).
 *  Handles the visualization of the game world, background, and other graphical elements.
 *  Manages transitions between different views (e.g., menus, in-game screens).
 ***********************************************************/

package mainGame;

import mainGame.GameTimer;

import java.nio.file.Paths;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView {
	// Class attributes
	private Scene initialGame;
	private Scene scene;
	private Scene splashScene;		// the splash scene
	private Scene aboutScene; 		// about developers scene
	private Scene instScene; 		// how 2 play scene
	private Stage stage;
	private Group root;
	private Canvas canvas;
	static MediaPlayer mediaPlayer; // playing background music

	// Constants for window dimensions and assets
	static final int WINDOW_HEIGHT = 700;
	static final int WINDOW_WIDTH = 800;
	private static final Image ICON = new Image("images/CLogo.png");
	//private static final Image ABOUT_DEV = new Image("images/underDev.png",800,700,false,false); // <== DEV Pictures 
	private static final Image INST_VIEW = new Image("images/aboutGame1.png",800,700,false,false); // <== DEV Pictures
	static final String BGM_MAINMENU = "src/music/BGM_MainMenu.mp3"; //<== path to music
	static final String BGM_STARTGAME = "src/music/BGM_IndoorPlayGround.mp3"; //<== path to music
	
	
	// Fonts and colors
	static final Font DESC_FONT = Font.font("Arial", FontWeight.BOLD,14);
	public static final Color FONT_COLOR = Color.WHITE;

	// Class constructor
	public GameView() {
		this.root = new Group(); // group of leaf nodes
		this.scene = new Scene(root, GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
		
	
	}

	// Methods to set up the game stage and scenes
	public void setStage(Stage stage) {

		this.stage = stage;
		// set stage elements here
		this.root.getChildren().addAll(this.createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT),canvas);
		this.stage.setTitle("Party Catch");
		
		// start playing the background music
		//this.mediaPlayer.play();

		//for menu initialization (about,instructions,start game)
		this.initSplash(stage);
		this.initAbout(stage);
		this.initInst(stage);
		this.initInitialG(stage);

		this.stage.setScene(this.splashScene);
		this.stage.setResizable(false);
		stage.getIcons().add(ICON);
		music(BGM_MAINMENU);
		
		this.stage.show();
	}
	
	// Method that plays background music; call this and replace music with String path, refer to variables on top
	static void music(String music) {
		if (mediaPlayer != null) mediaPlayer.stop();
		 //<= stop any music first before playing another
		Media h = new Media(Paths.get(music).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}

	//main game stage; edit this when GameTimer is done
	private void setGame(Stage stage) { 
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		GameTimer gameTimer = new GameTimer(scene, gc);
		gameTimer.start();
		stage.setScene(scene);
		music(BGM_STARTGAME);
	}
	
	private void setInitialG (Stage stage) {
		stage.setScene(initialGame);
	}
	
	//Sets the stage to the splash screen
	private void setMenu(Stage stage) {
        stage.setScene(splashScene);
	}

	//Sets the stage to the Developers screen
	private void setAbout(Stage stage) {
		stage.setScene(aboutScene);
	}
	
	//Sets the stage to the About Game screen
	private void setInst(Stage stage) {
		stage.setScene(instScene);
	}

	// Methods to create various UI components
	private Canvas createCanvas(int width, int height) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image bg = new Image("images/BG_BeachFrontSide.jpg", 1300, 800, false, false); //<-- need at least 700x800 for bg image
        double xOffset = -250;
        gc.drawImage(bg, xOffset, 0);
		return canvas;     
	}

	// Initializes the splash screen, about screen, and instructions screen
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT),this.createPane());
		this.splashScene = new Scene(root,GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
	}

	private void initAbout(Stage stage) {
		ScrollPane root = new ScrollPane();
		this.aboutScene = new Scene(root,GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
		root.setContent(this.createAboutPane());
	}
	
	private void initInst(Stage stage) {
		ScrollPane root = new ScrollPane();
		this.instScene = new Scene(root,GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
		root.setContent(this.createInstPane());
	}

	private void initInitialG(Stage stage) {
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT),this.createNewGame());
		this.initialGame = new Scene(root,GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
	}

	// for UI, such as buttons too
	private Pane createPane() {
		Pane pane = new Pane();
		
		//game logo
		Image logo = new Image("images/logoPartyCatch.png");
		ImageView partyCatch = new ImageView(logo);
		partyCatch.relocate(100, 50);
		animation(partyCatch, 0, -30, true);

		// about Devs button graphics 
		Image aboutDevs = new Image("images/aboutDevelopers.png", 250, 150, false, false);
		ImageView aboutView = new ImageView(aboutDevs);

		//new Game button graphics
		Image start = new Image("images/playButton.png", 450, 200, false, false);
		ImageView newGame = new ImageView(start);

		// instructions button graphics 
		Image instructions = new Image("images/aboutGame.png", 350, 150, false, false);
		ImageView instView = new ImageView(instructions);

		//create buttons
		Button b1 = new Button(); //<= button for aboutDevs
		Button b2 = new Button(); //<= button for newGame
		Button b3 = new Button(); //<= button for instruction
		
		// set graphics for button
		b1.setGraphic(aboutView);
		b2.setGraphic(newGame);
		b3.setGraphic(instView);
		
		//set animation; fade in and out
		b1.setSkin(new ButtonAnim(b1, 0.5));
		b2.setSkin(new ButtonAnim(b2, 0.5));
		b3.setSkin(new ButtonAnim(b3, 0.5));

		//button styling
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b2.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b3.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");

		//function on button click
		b1.setOnMouseClicked(event -> setAbout(stage));
		b2.setOnMouseClicked(event -> setInitialG(stage));
		b3.setOnMouseClicked(event -> setInst(stage));
		
		//button positioning
		b1.relocate(270, 500); //(x,y)
		b2.relocate(170, 250);
		b3.relocate(220, 400);
		
		pane.getChildren().addAll(partyCatch,b2,b3,b1); //<== add on parameters all of the Pane elements

		return pane;
	}
	
	private Pane createNewGame() {
		Pane inst = new Pane();
		Image initialG = new Image("images/BG_Mountain.jpg", 800, 700, false, false);
		ImageView aboutImg = new ImageView(initialG); //<== for DEV graphic
		
		Image image1 = new Image("images/exitButton.png", 100, 50, false, false);
        ImageView imageView1 = new ImageView(image1);
        
        Image image2 = new Image("images/startGame.png", 200, 200, false, false);
        ImageView imageView2 = new ImageView(image2);
        animation(imageView2, 0, -10, true);
		
		Button b1 = new Button();// button for going back
		b1.setGraphic(imageView1);
		b1.setSkin(new ButtonAnim(b1, 0.5));
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.setOnMouseClicked(event -> setMenu(stage));// setMenu function used to redirect to Main Menu
		b1.relocate(10, 10);
		
		Button b2 = new Button();// button for starting game
		b2.setGraphic(imageView2);
		b2.setSkin(new ButtonAnim(b2, 0.9));
		b2.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b2.setOnMouseClicked(event -> setGame(stage));// setMenu function used to redirect to Main Menu
		b2.relocate(300, 400);


		inst.getChildren().addAll(aboutImg,b1,b2); //<== add on parameters all elements needed
		return inst;
	}

	// for about developer page
	private Pane createAboutPane() {
		Pane about = new Pane();
		Image oneP = new Image("images/devz1.png",800,700,false,false);
		ImageView pOne = new ImageView(oneP); //<== for DEV graphic
		Image twoP = new Image("images/devz2.png",800,700,false,false);
		ImageView pTwo = new ImageView(twoP);
		pTwo.relocate(0, 700);
		
		Image devz = new Image("images/devs.png");
		ImageView dev = new ImageView(devz);
		dev.relocate(0,-150);
		animation(dev, 0, -10, true);
		
		Image image = new Image("images/exitButton.png", 100, 50, false, false);
        ImageView imageView = new ImageView(image);
		
		Button b1 = new Button();
		b1.setGraphic(imageView);
		b1.setSkin(new ButtonAnim(b1, 0.5));
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.setOnMouseClicked(event -> setMenu(stage));
		b1.relocate(10, 10);


		about.getChildren().addAll(createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT), pOne,b1, pTwo, dev); //<== add on parameters all elements needed
		return about;
	}
	
	// for instructions page
		private Pane createInstPane() {
			Pane inst = new Pane();
			Image oneP = new Image("images/about1.png",800,700,false,false);
			ImageView pOne = new ImageView(oneP); //<== for DEV graphic
			Image twoP = new Image("images/about2.png",800,700,false,false);
			ImageView pTwo = new ImageView(twoP);
			pTwo.relocate(0, 700);
			Image threeP = new Image("images/about3.png",800,700,false,false);
			ImageView pThree= new ImageView(threeP);
			pThree.relocate(0, 1400);
			Image fourP = new Image("images/about4.png",800,700,false,false);
			ImageView pFour= new ImageView(fourP);
			pFour.relocate(0, 2100);
			Image fiveP = new Image("images/about5.png",800,700,false,false);
			ImageView pFive= new ImageView(fiveP);
			pFive.relocate(0, 2800);
			
			Image image = new Image("images/exitButton.png", 100, 50, false, false);
	        ImageView imageView = new ImageView(image);
			
			Button b1 = new Button();// button for going back
			b1.setGraphic(imageView);
			b1.setSkin(new ButtonAnim(b1, 0.5));
			b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
			b1.setOnMouseClicked(event -> setMenu(stage));// setMenu function used to redirect to Main Menu
			b1.relocate(10, 10);


			inst.getChildren().addAll(createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT), pOne,pTwo,pThree,pFour,pFive,b1); //<== add on parameters all elements needed
			return inst;
		}
		
		//animation for logo
		void animation(ImageView image, int x, int y, boolean reverse) {
			TranslateTransition translate = new TranslateTransition();
			translate.setNode(image);
			translate.setDuration(Duration.millis(1000));
			translate.setCycleCount(TranslateTransition.INDEFINITE);
			translate.setAutoReverse(reverse);
			translate.setByX(x);
			translate.setByY(y);
			translate.play();
		}
}
