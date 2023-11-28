package mainGame;

//import game.GameStage;

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
		private Stage stage;
		private Group root;
		private Canvas canvas;
		
		// Constants for window dimensions and assets
		static final int WINDOW_HEIGHT = 700;
		static final int WINDOW_WIDTH = 800;
		private static final int INSTRUCTION_WINDOW_HEIGHT = 900;
		// private static final Image ABOUT_DEV = new Image("dev.png",800,600,false,false); <== DEV Pictures 
		
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
			
			//for menu initialization (about,instructions,start game)
			this.initSplash(stage);
			this.initAbout(stage);
			
			this.stage.setScene(this.splashScene);
			this.stage.setResizable(false);

			this.stage.show();
		}
		
		private void setAbout(Stage stage) {
	        stage.setScene(aboutScene);
		}
		
		// Methods to create various UI components
		private Canvas createCanvas(int width, int height) {
	    	Canvas canvas = new Canvas(width, height);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        Image bg = new Image("images/ben10bg.jpg"); //<-- need at least 700x800 for bg image
	        gc.drawImage(bg, 0, 0);
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
		
		
		
		// for UI, such as buttons too
		private VBox createVBox() {
	    	VBox vbox = new VBox();
	        vbox.setAlignment(Pos.CENTER);
	        vbox.setSpacing(8);
	        
	        // about Devs button graphics 
			Image aboutDevs = new Image("images/aboutDevs.png", 500, 100, false, false);
	        ImageView aboutView = new ImageView(aboutDevs);
	        
	        Button b1 = new Button(); //<= button for aboutDevs
	        
	        // set graphics for button
	        b1.setGraphic(aboutView);
	        
	        //button styling
	        b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
	        
	        //function on button click
	        b1.setOnMouseClicked(event -> setAbout(stage));
	        
	        vbox.getChildren().addAll(b1); //<== add on parameters all of the VBox elements
	        
	        return vbox;
	    }
		
		// for about developer UI
		private Pane createAboutPane() {
			Pane about = new Pane();
			//ImageView img1 = new ImageView(ABOUT_DEV); <== uncomment for about DEV graphic
			
	        
			about.getChildren().addAll(createCanvas(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT));
			return about;
		}
}
