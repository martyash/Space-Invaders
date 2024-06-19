package invaders.Observer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;


// concrete Observer
public class TimerDisplay implements TimeObserver{
    // string will be adjust via longs
    private Label timerLabel = new Label("0:00");
    

    @Override
    public void update(long timePassedInNano,boolean gameFinished){
        if(!gameFinished){
        long timeInSeconds = timePassedInNano / 1000000000; // Convert nanoseconds to seconds
        long minutes = timeInSeconds / 60;
        long seconds = timeInSeconds % 60;
        timerLabel.setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
        timerLabel.setTextFill(Color.WHITE);
        // has to be converted here rather than gameWindow as 'long' cannot understand fractions
        // and keeps time stuck at 0:00 at conversion 
        // conversion from nano to sec    
    }
    }

    public Label getLabel() {
        return timerLabel;
    }

    
    
}
