package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import mainGame.Game;

public class Main extends Application 
{
		
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
       Game game = new Game();
       game.setStage(stage);
    }
    
    
}
