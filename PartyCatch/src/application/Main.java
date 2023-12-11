/***********************************
 * 
 * This is a Java program that utilizes JavaFX. “Party Catch” is a casual arcade game where players control a dynamic basket to catch falling fruits. 
 * The game's pace intensifies over time, keeping players engaged and offering a balance between simplicity and excitement.
 * 
 * The game features the following Scenes:
 * Main Menu - displays Play button, About Game button, and Developers button
 * 
 * About Game - it contains a brief description of the game, how to play, scoring system, and the win/lose conditions. 
 * It also includes all the references used.
 * 
 * Developers - it contains the information about the developer members of the group.
 * 
 * Game Proper - the scene of the actual game
 * 
 * Winning Scene - this scene is displayed when the player wins the game
 * 
 * Game Over Scene - this scene is displayed when the player loses the game 
 * 
 * 
 * The program has 4 packages: the application, images, mainGame, and music packages.
 * The application package contains the Main class.
 * The images package contains all the images used in the game.
 * The mainGame package has 18 classes for the program to function properly.
 * The music package has contains the background music used in the game.
 * 
 * @authors	Shawn Clyde Diares, Jake Laurence Neverida, John Miles Ramos
 * @created__date	2023-11-21 09:28
 * 
 * CMSC 22 YZ1L Group 6
 ************************************/

package application;
	
import mainGame.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 *  What for: 
 *  
 *  Entry point for launching the application.
 *  Initializes the JavaFX application.
 *  Launches the main game components.
 */

public class Main extends Application {
	
	//Initializes the game view and sets the stage.
    public void start(Stage stage) {
        GameView newGame = new GameView(); // Create an instance of the GameView class, which manages the game scenes and components.
        newGame.setStage(stage);	// Set the primary stage for the game.
    }

	public static void main(String[] args) {
		launch(args);	// Launch the JavaFX application.
	}

}
