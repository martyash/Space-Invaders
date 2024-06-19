package invaders;

import javafx.scene.layout.VBox;
import invaders.Singleton.LevelSingleton;
import invaders.Singleton.Levels;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/*
 * Serves a vertical menu for user when they start the game
 * 
 * 
*/

// vbox lays out options in vertical column
public class Menu extends VBox{

    public Menu(Stage primaryStage) {
        // create the 3 buttons for difficulty
        Button btnEasy = new Button("Easy");
        Button btnMedium = new Button("Medium");
        Button btnHard = new Button("Hard");
        // singleton instances applied here 

        btnEasy.setOnAction(event -> {
            LevelSingleton.getInstance().setDifficulty(Levels.EASY);
            startGame(primaryStage);
        });

        btnMedium.setOnAction(event -> {
            LevelSingleton.getInstance().setDifficulty(Levels.MEDIUM);
            startGame(primaryStage);
        });

        btnHard.setOnAction(event -> {
            LevelSingleton.getInstance().setDifficulty(Levels.HARD);
            startGame(primaryStage);
        });

        this.getChildren().addAll(btnEasy, btnMedium, btnHard);
    }

    private void startGame(Stage primaryStage) {
        // set game instance based on singleton
        String difficultyFile = LevelSingleton.getInstance().getCurrentFilePath();
        GameEngine model = new GameEngine(difficultyFile);
        System.out.println("Game is starting");
        GameWindow window = new GameWindow(model);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        // launches the game
        window.run();
    }
    
}
