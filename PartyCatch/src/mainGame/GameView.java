package mainGame;

/*
 *  What for:
 *  
 *  Handles the graphical representation of the game.
 *  Displays game objects on the screen (e.g., sprites, UI elements).
 *  Listens for user input events and forwards them to the GameController.
 *  Renders sprites, updates the display, and manages the graphical user interface (GUI).
 *  Handles the visualization of the game world, background, and other graphical elements.
 *  Manages transitions between different views (e.g., menus, in-game screens).
 */

import mainGame.GameTimer;

import java.nio.file.Paths;

import javafx.geometry.Pos;
import javafx.scene.Group;

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

public class GameView {
	// Class attributes
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
	private static final int INSTRUCTION_WINDOW_HEIGHT = 900;
	private static final Image ABOUT_DEV = new Image("images/aboutPage.png",900,700,false,false); // <== DEV Pictures 
	private static final Image INST_VIEW = new Image("images/instPage.png",900,700,false,false); // <== DEV Pictures
	static final String BGM_MAINMENU = "src/music/BGM_MainMenu.mp3"; //<== path to music
	static final String BGM_STARTGAME = "src/music/BGM_Mountain.mp3"; //<== path to music
	
	
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

		this.stage.setScene(this.splashScene);
		this.stage.setResizable(false);
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

	private void setAbout(Stage stage) {
		stage.setScene(aboutScene);
	}
	
	private void setInst(Stage stage) {
		stage.setScene(instScene);
	}

	// Methods to create various UI components
	private Canvas createCanvas(int width, int height) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image bg = new Image("images/BG_MainMenu.jpg"); //<-- need at least 700x800 for bg image
        double xOffset = -250;
        gc.drawImage(bg, xOffset, 0);
		return canvas;     
	}

	// Initializes the splash screen, about screen, and instructions screen
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT),this.createVBox());
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


	// for UI, such as buttons too
	private VBox createVBox() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(8);

		// about Devs button graphics 
		Image aboutDevs = new Image("images/aboutDevs.png", 400, 100, false, false);
		ImageView aboutView = new ImageView(aboutDevs);

		//new Game button graphics
		Image start = new Image("images/newGame.png", 500, 100, false, false);
		ImageView newGame = new ImageView(start);

		// instructions button graphics 
		Image instructions = new Image("images/instructions.png", 400, 100, false, false);
		ImageView instView = new ImageView(instructions);

		//create buttons
		Button b1 = new Button(); //<= button for aboutDevs
		Button b2 = new Button(); //<= button for newGame
		Button b3 = new Button(); //<= button for instructions

		// set graphics for button
		b1.setGraphic(aboutView);
		b2.setGraphic(newGame);
		b3.setGraphic(instView);

		//button styling
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b2.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b3.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");

		//function on button click
		b1.setOnMouseClicked(event -> setAbout(stage));
		b2.setOnMouseClicked(event -> setGame(stage));
		b3.setOnMouseClicked(event -> setInst(stage));

		vbox.getChildren().addAll(b2,b3,b1); //<== add on parameters all of the VBox elements

		return vbox;
	}

	// for about developer page
	private Pane createAboutPane() {
		Pane about = new Pane();
		ImageView aboutImg = new ImageView(ABOUT_DEV); //<== for DEV graphic


		about.getChildren().addAll(createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT), aboutImg); //<== add on parameters all elements needed
		return about;
	}
	
	// for instructions page
		private Pane createInstPane() {
			Pane inst = new Pane();
			ImageView aboutImg = new ImageView(INST_VIEW); //<== for DEV graphic


			inst.getChildren().addAll(createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT), aboutImg); //<== add on parameters all elements needed
			return inst;
		}
}
