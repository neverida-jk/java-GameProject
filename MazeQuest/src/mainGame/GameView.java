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
		private Stage stage;
		private Group root;
		private Canvas canvas;
		
		// Constants for window dimensions and assets
		static final int WINDOW_HEIGHT = 800;
		static final int WINDOW_WIDTH = 600;
		private static final int INSTRUCTION_WINDOW_HEIGHT = 900;
		
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
			
			this.stage.setResizable(false);

			this.stage.show();
		}
		
		// Methods to create various UI components
		private Canvas createCanvas(int width, int height) {
	    	Canvas canvas = new Canvas(width, height);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        Image bg = new Image("images/background800x600.png"); //<-- need png
	        gc.drawImage(bg, 0, 0);
	        return canvas;     
	    }
}
