package invaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;



import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Menu menu = new Menu(primaryStage);
        // size of the menu NOT the game itself
        Scene menuScene = new Scene(menu, 300, 200); 
        primaryStage.setTitle("Space Invaders - Select Difficulty");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
}
