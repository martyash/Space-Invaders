package invaders.state;
import invaders.gameobject.Bunker;
import javafx.scene.image.Image;
import java.io.File;

public class YellowState implements BunkerState {
    private Bunker bunker;

    public YellowState(Bunker bunker){
        this.bunker = bunker;
    }

    @Override
    public void takeDamage() {
        bunker.setImage(new Image(new File("src/main/resources/bunkerRed.png").toURI().toString()));
        bunker.setState(new RedState(bunker));
    }

    private static YellowState createYellowState(Bunker bunkerCopy){
        YellowState yellow = new YellowState(bunkerCopy);
        return yellow;
    }

   // @Override
   // public YellowState copy(){
    //    Bunker bunkerCopy = this.bunker.copy();  
    //    return YellowState.createYellowState(bunkerCopy);
    //}

    public BunkerState copy(){
        // Return a new GreenState associated with the same bunker.
        return new YellowState(bunker);
    }
}