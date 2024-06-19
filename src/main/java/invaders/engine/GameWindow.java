package invaders.engine;

import java.util.List;
import java.util.ArrayList;



import invaders.ConfigReader;
import invaders.Observer.GameTimer;
import invaders.Observer.TimerDisplay;
import invaders.Observer.PointsDisplay;
import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import javafx.scene.control.Alert;
import javafx.util.Duration;


import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import invaders.gameobject.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import javafx.scene.control.Button;



public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;
    
    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    // private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model){
        this.model = model;
		this.width =  model.getGameWidth();
        this.height = model.getGameHeight();
        pane = new Pane();  
        // for animation timer
        

        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);
        // Initialize and position the TimerDisplay and Points Display
        TimerDisplay timerDisplay = new TimerDisplay();
        PointsDisplay pointsDisplay = new PointsDisplay();

        model.addObserverToGameTimer(timerDisplay); // Use method to add observer
        pane.getChildren().add(timerDisplay.getLabel()); // Add the timer label to the game pane

        model.addObservertoGamePoints(pointsDisplay);
        pane.getChildren().add(pointsDisplay.getLabel()); // add points label to the game pane

        timerDisplay.getLabel().setLayoutX(275); // Position timer label in the middle
        timerDisplay.getLabel().setLayoutY(10);

        pointsDisplay.getLabel().setLayoutX(30); // position points score towards the left
        pointsDisplay.getLabel().setLayoutY(10);



        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);
        //                      listener            event handler
        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);


    }

    
    

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }


    private void draw(){
        model.update();

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }


        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        // moves enemy projectiles
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        // creates enemy projectiles
        model.getRenderables().addAll(model.getPendingToAddRenderable());

        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }

}
