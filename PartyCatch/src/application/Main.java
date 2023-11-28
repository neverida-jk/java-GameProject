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
	
    public void start(Stage stage) {
        GameView newGame = new GameView();
        newGame.setStage(stage);
    }

	public static void main(String[] args) {
		launch(args);
	}

}
