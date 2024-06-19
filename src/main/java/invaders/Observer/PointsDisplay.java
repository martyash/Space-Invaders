package invaders.Observer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

// observer class
public class PointsDisplay implements PointObserver {
    private Label pointLabel;

    public PointsDisplay(){
        pointLabel = new Label("POINTS 0");
        pointLabel.setTextFill(Color.WHITE);
    }
    

    @Override
    public void updateScore(int points){

        pointLabel.setText("POINTS: " + points);
        pointLabel.setTextFill(Color.WHITE);
        
    }

    public Label getLabel(){
        return pointLabel;
    }
    
}
